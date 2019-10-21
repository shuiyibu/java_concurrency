package concurrents.observepattern.singlethread.impl;

import concurrents.observepattern.singlethread.Observer;
import concurrents.observepattern.singlethread.Subject;

/**
 * @description:
 * @date: 2019-09-04 20:42
 */
public class BinaryObserver extends Observer {

    public BinaryObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        System.out.println("Binary String:" + Integer.toBinaryString(subject.getState()));
    }
}