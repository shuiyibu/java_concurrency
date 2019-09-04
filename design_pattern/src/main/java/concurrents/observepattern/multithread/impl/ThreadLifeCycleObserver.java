package concurrents.observepattern.multithread.impl;

import concurrents.observepattern.multithread.ILifeCycleListener;
import concurrents.observepattern.multithread.ObservableRunnable;

import java.util.List;

/**
 * @description:
 * @auther:
 * @date: 2019-09-04 21:06
 */
public class ThreadLifeCycleObserver implements ILifeCycleListener {

    private final Object LOCK = new Object();

    /**
     * @description: 回调
     * @param:
     * @return:
     * @auther:
     * @date: 2019-09-04 21:07
     */
    public void concurrentQuery(List<String> ids) {
        if (ids == null || ids.isEmpty())
            return;

        ids.stream().forEach(id -> new Thread(new ObservableRunnable(this) {
            @Override
            public void run() {
                try {
                    notifyChange(new RunnableEvent(RunnableState.RUNNING, Thread.currentThread(), null));
                    System.out.println("query for the id " + id);
                    Thread.sleep(1000L);
                    notifyChange(new RunnableEvent(RunnableState.DONE, Thread.currentThread(), null));
                } catch (Exception e) {
                    notifyChange(new RunnableEvent(RunnableState.ERROR, Thread.currentThread(), e));
                }
            }
        }, id).start());
    }

    @Override
    public void onEvent(ObservableRunnable.RunnableEvent event) {
        synchronized (LOCK) {
            System.out.println("The runnable [" + event.getThread().getName() + "] data changed and state is [" + event.getState() + "]");
            if (event.getCause() != null) {
                System.out.println("The runnable [" + event.getThread().getName() + "] process failed.");
                event.getCause().printStackTrace();
            }
        }
    }
}