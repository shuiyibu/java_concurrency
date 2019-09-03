package _3createthread;

import java.util.Arrays;

/**
 *
 */
public class CreateThreadWithThreadGroup {
    /**
     * t.getThreadGroup() = java.lang.ThreadGroup[name=main,maxpri=10]
     * Thread.currentThread().getName() = main
     * threadGroup.getName() = main
     * threadGroup.activeCount() = 3
     * Thread[main,5,main]
     * Thread[Monitor Ctrl-Break,5,main]
     * Thread[Thread-0,5,]
     *
     * @param args
     */
    public static void main(String[] args) {
        Thread t = new Thread() {
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        System.out.println("t.getThreadGroup() = " + t.getThreadGroup());
        System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        System.out.println("threadGroup.getName() = " + threadGroup.getName());

        System.out.println("threadGroup.activeCount() = " + threadGroup.activeCount());

        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);

        Arrays.asList(threads).forEach(System.out::println);
    }
}
