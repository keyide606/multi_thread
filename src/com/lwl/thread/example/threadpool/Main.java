package com.lwl.thread.example.threadpool;

import java.util.stream.IntStream;

/**
 * @author liwenlong
 * @date 2021-04-30 22:12
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        testSimpleThreadPool();
    }


    static void testSimpleThreadPool() throws InterruptedException {
        SimpleThreadPool simpleThreadPool = new SimpleThreadPool();
        simpleThreadPool.start();
        IntStream.range(0, 50).forEach(i -> {
            simpleThreadPool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + "执行第" + i + "个任务");
                try {
                    Thread.sleep(2_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
        Thread.sleep(10_00000);
        System.out.println("任务全部执行完毕");
        simpleThreadPool.shutdown();
        simpleThreadPool.submit(() -> System.out.println("提交任务"));
    }
}
