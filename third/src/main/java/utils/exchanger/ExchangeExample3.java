package utils.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.System.out;

public class ExchangeExample3 {
    public static void main(String[] args) {
        final Exchanger<Integer> exchanger = new Exchanger<>();
        new Thread(() -> {
            AtomicReference<Integer> a = new AtomicReference<>(1);
            try {
                while (true) {
                    a.set(exchanger.exchange(a.get()));
                    out.println("A has value " + a.get());
                    TimeUnit.SECONDS.sleep(3);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            AtomicReference<Integer> a = new AtomicReference<>(2);
            try {
                while (true) {
                    a.set(exchanger.exchange(a.get()));
                    out.println("B has value " + a.get());
                    TimeUnit.SECONDS.sleep(2);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
