package lock;

import java.util.Collection;

/***
 *
 */
public interface Lock {

    void lock() throws InterruptedException;

    void lock(long mills) throws InterruptedException, TimeOutException;

    void unlock();

    Collection<Thread> getBlockedThread();

    int getBlockedSize();

    class TimeOutException extends Exception {

        public TimeOutException(String message) {
            super(message);
        }
    }

}