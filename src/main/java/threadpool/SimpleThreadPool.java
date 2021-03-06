package threadpool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 防止线程重复创建，浪费资源和时间
 * 1. 任务队列
 * 2. 拒绝策略（抛出异常，直接丢弃，阻塞，临时队列）
 * 3. 初始化值init
 * 4. active
 * 5. max
 * <p>
 * 实例 quartz control-M
 */
public class SimpleThreadPool extends Thread {

    public final static DiscardPolicy DEFAULT_DISCARD_POLICY = () -> {
        throw new DiscardException("Discard This Task.");
    };
    /**
     * 默认任务队列大小
     */
    private final static int DEFAULT_TASK_QUEUE_SIZE = 2000;

    /**
     * 线程前缀
     */
    private final static String THREAD_PREFIX = "SIMPLE_THREAD_POOL-";

    /**
     * 线程组
     */
    private final static ThreadGroup GROUP = new ThreadGroup("Pool_Group");

    /**
     * 任务队列（缓冲）
     */
    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();

    /**
     * 线程队列
     */
    private final static List<WorkerTask> THREAD_QUEUE = new ArrayList<>();

    private static volatile int seq = 0;

    /**
     * 提交队列大小
     */
    private final int queueSize;

    private final DiscardPolicy discardPolicy;
    /**
     * 线程池大小
     */
    private int size;

    /**
     * 线程池状态
     */
    private volatile boolean destroy = false;

    /**
     * 最小线程数
     */
    private int min;

    /**
     * 最大线程数
     */
    private int max;

    /**
     * 激活线程数
     */
    private int active;

    public SimpleThreadPool() {
        this(4, 8, 12, DEFAULT_TASK_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
    }

    public SimpleThreadPool(int min, int active, int max, int queueSize, DiscardPolicy discardPolicy) {
        this.min = min;
        this.active = active;
        this.max = max;
        this.queueSize = queueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleThreadPool threadPool = new SimpleThreadPool();
        for (int i = 0; i < 40; i++) {
            threadPool.submit(() -> {
                System.out.println("The runnable  be serviced by " + Thread.currentThread() + " start.");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("The runnable be serviced by " + Thread.currentThread() + " finished.");
            });
        }

        Thread.sleep(10000);
        threadPool.shutdown();

       /* Thread.sleep(10000);
        threadPool.shutdown();
        threadPool.submit(() -> System.out.println("======="));*/
    }

    /**
     *
     */
    private void init() {
        for (int i = 0; i < this.min; i++) {
            createWorkTask();
        }
        this.size = min;
        this.start();
    }

    /**
     * @param runnable
     */
    public void submit(Runnable runnable) {
        if (destroy)
            throw new IllegalStateException("The thread pool already destroy and not allow submit task.");

        synchronized (TASK_QUEUE) {
            if (TASK_QUEUE.size() > queueSize)
                discardPolicy.discard();
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    @Override
    public void run() {
        while (!destroy) {
            System.out.printf("Pool#Min:%d,Active:%d,Max:%d,Current:%d,QueueSize:%d\n",
                    this.min, this.active, this.max, this.size, TASK_QUEUE.size());
            try {
                Thread.sleep(5_000L);
                if (TASK_QUEUE.size() > active && size < active) {
                    for (int i = size; i < active; i++) {
                        createWorkTask();
                    }
                    System.out.println("The pool incremented to active.");
                    size = active;
                } else if (TASK_QUEUE.size() > max && size < max) {
                    for (int i = size; i < max; i++) {
                        createWorkTask();
                    }
                    System.out.println("The pool incremented to max.");
                    size = max;
                }

                synchronized (THREAD_QUEUE) {
                    if (TASK_QUEUE.isEmpty() && size > active) {
                        System.out.println("=========Reduce========");
                        int releaseSize = size - active;
                        for (Iterator<WorkerTask> it = THREAD_QUEUE.iterator(); it.hasNext(); ) {
                            if (releaseSize <= 0)
                                break;

                            WorkerTask task = it.next();
                            task.close();
                            task.interrupt();
                            it.remove();
                            releaseSize--;
                        }
                        size = active;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 构建线程
     */
    private void createWorkTask() {
        WorkerTask task = new WorkerTask(GROUP, THREAD_PREFIX + (seq++));
        task.start();
        THREAD_QUEUE.add(task);
    }

    public void shutdown() throws InterruptedException {
        while (!TASK_QUEUE.isEmpty()) {
            Thread.sleep(50);
        }

        synchronized (THREAD_QUEUE) {
            int initVal = THREAD_QUEUE.size();
            while (initVal > 0) {
                for (WorkerTask task : THREAD_QUEUE) {
                    if (task.getTaskState() == TaskState.BLOCKED) {
                        task.interrupt();
                        task.close();
                        initVal--;
                    } else {
                        Thread.sleep(10);
                    }
                }
            }
        }

        System.out.println(GROUP.activeCount());

        this.destroy = true;
        System.out.println("The thread pool disposed.");
    }

    public int getQueueSize() {
        return queueSize;
    }

    public int getSize() {
        return size;
    }

    public boolean isDestroy() {
        return this.destroy;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getActive() {
        return active;
    }

    /**
     * 线程状态
     */
    private enum TaskState {
        FREE, RUNNING, BLOCKED, DEAD
    }

    /**
     * 拒绝策略
     */
    public interface DiscardPolicy {

        void discard() throws DiscardException;
    }

    public static class DiscardException extends RuntimeException {

        public DiscardException(String message) {
            super(message);
        }
    }

    /**
     *
     */
    private static class WorkerTask extends Thread {

        private volatile TaskState taskState = TaskState.FREE;

        public WorkerTask(ThreadGroup group, String name) {
            super(group, name);
        }

        public TaskState getTaskState() {
            return this.taskState;
        }

        @Override
        public void run() {
            OUTER:
//标记？？TODO
            while (this.taskState != TaskState.DEAD) {
                Runnable runnable;
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            taskState = TaskState.BLOCKED;
                            TASK_QUEUE.wait();//worktask
                        } catch (InterruptedException e) {
                            System.out.println("Closed.");
                            break OUTER;
                        }
                    }
                    runnable = TASK_QUEUE.removeFirst();
                }

                if (runnable != null) {
                    taskState = TaskState.RUNNING;
                    runnable.run();
                    taskState = TaskState.FREE;
                }
            }
        }

        public void close() {
            this.taskState = TaskState.DEAD;
        }
    }
}
