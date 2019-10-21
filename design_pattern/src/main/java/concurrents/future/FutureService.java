package concurrents.future;

import concurrents.future.impl.AsynFuture;

import java.util.function.Consumer;

/**
 *
 */
public class FutureService {

    public <T> IFuture<T> submit(final IFutureTask<T> task) {
        AsynFuture<T> asynFuture = new AsynFuture<>();
        new Thread(() -> {
            T result = task.call();
            asynFuture.done(result);
        }).start();
        return asynFuture;
    }

    public <T> IFuture<T> submit(final IFutureTask<T> task, final Consumer<T> consumer) {
        AsynFuture<T> asynFuture = new AsynFuture<>();
        new Thread(() -> {
            T result = task.call();
            asynFuture.done(result);
            consumer.accept(result);
        }).start();
        return asynFuture;
    }
}