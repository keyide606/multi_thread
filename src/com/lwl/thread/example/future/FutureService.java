package com.lwl.thread.example.future;

/**
 * @author liwenlong
 * @date 2021-05-06 11:01
 */
public class FutureService {

    public <T> Future<T> submit(final FutureTask<T> task) {
        AsynFuture<T> future = new AsynFuture<>();
        new Thread(() -> {
            T result = task.call();
            future.finish(result);
        }).start();
        return future;
    }
}
