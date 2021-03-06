package com.lwl.thread.example.future;

/**
 * @author liwenlong
 * @date 2021-05-06 10:58
 */
public interface Future<T> {

    T get() throws InterruptedException;
}
