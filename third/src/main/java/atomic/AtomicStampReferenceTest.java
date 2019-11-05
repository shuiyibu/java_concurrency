package atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

import static java.lang.System.out;

/**
 * @Auther: langdylan
 * @Date: 2019-09-23 09:44
 * @Description: 可以解决CAS的ABA
 */
public class AtomicStampReferenceTest {

    private static AtomicStampedReference<Integer> atomicStampedRef = new AtomicStampedReference<>(100, 0);

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    boolean result = atomicStampedRef.compareAndSet(100, 101, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
                    out.println(result);

                    result = atomicStampedRef.compareAndSet(101, 100, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
                    out.println(result);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int stamp = atomicStampedRef.getStamp();
                    out.println("Before sleep stamp: " + stamp);
                    TimeUnit.SECONDS.sleep(2);

                    boolean result = atomicStampedRef.compareAndSet(100, 101, stamp, stamp + 1);
                    out.println(result);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        thread.start();
        thread2.start();
        thread.join();
        thread2.join();

    }
}
