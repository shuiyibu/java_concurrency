package utils.exchanger;

import java.util.concurrent.Exchanger;

import static java.lang.System.out;

public class ExchangeExample2 {
    /**
     * Actor?
     * <p>
     * A will send the object java.lang.Object@49f32bed
     * B will send the object java.lang.Object@62b79014
     * B received the object java.lang.Object@49f32bed
     * A received the object java.lang.Object@62b79014
     */
    public static void main(String[] args) {
        final Exchanger<Object> exchanger = new Exchanger<>();
        new Thread(() -> {
            Object oa = new Object();
            out.println("A will send the object " + oa);
            try {
                Object ob = exchanger.exchange(oa);
                out.println("A received the object " + ob);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            Object ob = new Object();
            out.println("B will send the object " + ob);
            try {
                Object oa = exchanger.exchange(ob);
                out.println("B received the object " + oa);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
