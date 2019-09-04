package concurrents.observepattern.multithread;

import concurrents.observepattern.multithread.impl.ThreadLifeCycleObserver;

import java.util.Arrays;

/**
 * @description:
 * @auther:
 * @date: 2019-09-04 21:11
 */
public class ThreadLifeCycleClient {
    public static void main(String[] args) {
        new ThreadLifeCycleObserver().concurrentQuery(Arrays.asList("1", "2"));
    }
}