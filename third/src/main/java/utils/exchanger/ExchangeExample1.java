package utils.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.lang.System.out;

/**
 * @description:
 * @auther:
 * @date: 2019-10-01 15:22
 */
public class ExchangeExample1 {
    public static void main(String[] args) {
        final Exchanger<String> exchanger = new Exchanger<>();

        new Thread(() -> {
            out.println(Thread.currentThread().getName() + " start...");
            try {
                String result = exchanger.exchange("I am from T-A", 10, TimeUnit.SECONDS);
                out.println(Thread.currentThread().getName() + " get value[" + result + "]");
                out.println(Thread.currentThread().getName() + " end");

            } catch (InterruptedException | TimeoutException e) {
                e.printStackTrace();
                out.println("time out");
            }
        }, "==A==").start();

        new Thread(() -> {
            out.println(Thread.currentThread().getName() + " start...");
            try {
                String result = exchanger.exchange("I am from T-A1", 10, TimeUnit.SECONDS);
                out.println(Thread.currentThread().getName() + " get value[" + result + "]");
                out.println(Thread.currentThread().getName() + " end");

            } catch (InterruptedException | TimeoutException e) {
                e.printStackTrace();
                out.println("time out");
            }
        }, "==A1==").start();

        new Thread(() -> {
            out.println(Thread.currentThread().getName() + " start...");
            try {
                TimeUnit.SECONDS.sleep(20);
                String result = exchanger.exchange("I am from T-B");
                out.println(Thread.currentThread().getName() + " get value[" + result + "]");
                out.println(Thread.currentThread().getName() + " end");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "==B==").start();
        new Thread(() -> {
            out.println(Thread.currentThread().getName() + " start...");
            try {
                TimeUnit.SECONDS.sleep(20);
                String result = exchanger.exchange("I am from T-B1");
                out.println(Thread.currentThread().getName() + " get value[" + result + "]");
                out.println(Thread.currentThread().getName() + " end");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "==B1==").start();
    }
}
