package concurrents.observepattern.singlethread;

import concurrents.observepattern.singlethread.impl.BinaryObserver;
import concurrents.observepattern.singlethread.impl.OctalObserver;

/**
 * @description:
 * @auther:
 * @date: 2019-09-04 20:57
 */
public class ObserverClient {
    public static void main(String[] args) {

        final Subject subject = new Subject();
        new BinaryObserver(subject);
        new OctalObserver(subject);
        System.out.println("==================");
        subject.setState(10);
        System.out.println("==================");
        subject.setState(10);

        System.out.println("==================");
        subject.setState(15);
    }
}
