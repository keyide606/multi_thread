package com.lwl.thread.listener;

import java.util.Arrays;

/**
 * @author liwenlong
 * @date 2021-05-01 18:29
 */
public class Main {
    public static void main(String[] args) {
        ThreadLifeCycleObserver observer = new ThreadLifeCycleObserver();
        observer.concurrentQuery(Arrays.asList("t1", "t2", "t3"));
    }
}
