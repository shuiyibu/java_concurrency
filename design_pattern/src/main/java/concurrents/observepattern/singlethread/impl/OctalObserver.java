package concurrents.observepattern.singlethread.impl;

import concurrents.observepattern.singlethread.Observer;
import concurrents.observepattern.singlethread.Subject;

/**
 * @description:
 * @auther:
 * @date: 2019-09-04 20:54
 */
public class OctalObserver extends Observer {

    public OctalObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        System.out.println("Octal String:" + Integer.toOctalString(subject.getState()));
    }
}