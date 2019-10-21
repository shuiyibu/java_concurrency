package concurrents.immutable;

/**
 *
 */
public class ImmutablePerformance {
    /**
     * 使用两个线程测试主要是防止jdk作优化
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        //36470  10067
        //35857 immutable 9803
        long startTimestamp = System.currentTimeMillis();
        SyncObj synObj = new SyncObj();
        synObj.setName("Alex");
        ImmutableObj immutableObj = new ImmutableObj("Bob");

//        Thread t1 = new Thread() {
//            @Override
//            public void run() {
//                for (long l = 0L; l < 1_000_000; l++) {
//                    System.out.println(Thread.currentThread().getName() + "=" + immutableObj.toString());
//                }
//            }
//        };
//        Thread t2 = new Thread() {
//            @Override
//            public void run() {
//                for (long l = 0L; l < 1_000_000; l++) {
//                    System.out.println(Thread.currentThread().getName() + "=" + immutableObj.toString());
//                }
//            }
//        };
        Thread t1 = new Thread(new TestRunnable(immutableObj));
        t1.start();

        Thread t2 = new Thread(new TestRunnable(immutableObj));
        t2.start();
        t1.join();
        t2.join();


        long endTimestamp = System.currentTimeMillis();
        System.out.println("Elapsed time " + (endTimestamp - startTimestamp));
    }
}

class TestRunnable implements Runnable {
    private Object object;

    public TestRunnable(Object object) {
        this.object = object;
    }

    @Override
    public void run() {
        for (long l = 0L; l < 1_000_000; l++) {
            System.out.println(Thread.currentThread().getName() + "=" + object.toString());
        }
    }
}

final class ImmutableObj {
    private final String name;

    ImmutableObj(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "[" + name + "]";
    }
}

class SyncObj {

    private String name;

    public synchronized void setName(String name) {
        this.name = name;
    }

    @Override
    public synchronized String toString() {
        return "[" + name + "]";
    }
}