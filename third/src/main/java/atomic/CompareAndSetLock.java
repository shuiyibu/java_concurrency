package atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: langdylan
 * @Date: 2019-09-19 15:29
 * @Description:
 */
public class CompareAndSetLock {
    private final AtomicInteger value = new AtomicInteger(0);
    private Thread lockThread;

    public void tryLock() throws GetLockException {
        boolean success = value.compareAndSet(0, 1);
        if (!success) throw new GetLockException("Get Lock failed");
        else lockThread = Thread.currentThread();
    }

    /**
     * //一个锁可能被多个线程拿到
     */
    public void unLockFailed() {
        if (0 == value.get()) return;
        value.compareAndSet(1, 0);
    }

    public void unLock() {
        if (0 == value.get()) return;
        if (lockThread == Thread.currentThread())
            value.compareAndSet(1, 0);
    }
}
