package concurrents.future.impl;

import concurrents.future.IFuture;

/***
 *
 * @param <T>
 */
public class AsynFuture<T> implements IFuture<T> {

    private volatile boolean done = false;

    private T result;

    public void done(T result) {
        synchronized (this) {
            this.result = result;
            this.done = true;
            this.notifyAll();
        }
    }

    @Override
    public T get() throws InterruptedException {
        synchronized (this) {
            while (!done) {
                this.wait();
            }
        }
        return result;
    }
}
