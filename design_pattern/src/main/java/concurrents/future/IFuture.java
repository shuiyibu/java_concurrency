package concurrents.future;

/**
 *
 */
public interface IFuture<T> {

    T get() throws InterruptedException;

}