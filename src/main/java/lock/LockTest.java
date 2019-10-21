package lock;

import java.util.Optional;
import java.util.stream.Stream;

import static java.lang.System.out;

/***
 *
 */
public class LockTest {
    public static void main(String[] args) throws InterruptedException {

        final BooleanLock booleanLock = new BooleanLock();
        Stream.of("T1", "T2", "T3", "T4")
                .forEach(name ->
                        new Thread(() -> {
                            try {
                                booleanLock.lock(300_000L);
                                Optional.of(Thread.currentThread().getName() + " have the lock Monitor")
                                        .ifPresent(out::println);
                                work();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (Lock.TimeOutException e) {
                                Optional.of(Thread.currentThread().getName() + " time out")
                                        .ifPresent(out::println);
                            } finally {
                                booleanLock.unlock();
                            }
                        }, name).start()
                );
        out.println('d');
    }

    private static void work() throws InterruptedException {
        Optional.of(Thread.currentThread().getName() + " is Working...")
                .ifPresent(out::println);
        Thread.sleep(1_000);
    }
}
