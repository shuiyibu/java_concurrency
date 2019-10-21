package concurrents.observepattern.multithread;

/**
 * @description: Observer
 * @auther:
 * @date: 2019-09-04 21:04
 */
public interface ILifeCycleListener {

    void onEvent(ObservableRunnable.RunnableEvent event);
}
