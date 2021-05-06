package com.lwl.thread.example.future;

/**
 * @author liwenlong
 * @date 2021-05-06 10:59
 */
@FunctionalInterface
public interface FutureTask<T> {
    T call();
}
