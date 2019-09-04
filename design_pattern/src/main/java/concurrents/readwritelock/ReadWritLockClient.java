package concurrents.readwritelock;

/**
 * @description:
 * @auther:
 * @date: 2019-09-05 00:09
 */

/**
 * ReadWriteLock design pattern
 * Reader-Writer design pattern
 */
public class ReadWritLockClient {
    public static void main(String[] args) {
        final SharedData sharedData = new SharedData(10);
        new ReaderWorker(sharedData).start();
        new ReaderWorker(sharedData).start();
        new ReaderWorker(sharedData).start();
        new ReaderWorker(sharedData).start();
        new ReaderWorker(sharedData).start();
        new WriterWorker(sharedData, "qwertyuiopasdfg").start();
        new WriterWorker(sharedData, "QWERTYUIOPASDFG").start();
    }
}