package com.lwl.thread.example.future;

/**
 * @author liwenlong
 * @date 2021-05-06 11:03
 */
public class AsynFuture<T> implements Future<T> {
    private volatile boolean finished = false;
    private T result;

    @Override
    public T get() throws InterruptedException {
        synchronized (this) {
            while (!finished) {
                this.wait();
            }
        }
        return result;
    }

    public void finish(T result) {
        synchronized (this) {
            this.result = result;
            this.finished = true;
            this.notify();
        }
    }
}
