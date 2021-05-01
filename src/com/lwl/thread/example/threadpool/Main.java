package com.lwl.thread.example.threadpool;

import java.util.stream.IntStream;

/**
 * @author liwenlong
 * @date 2021-04-30 22:12
 */
public class Main {
    public static void main(String[] args) {
        testSimpleThreadPool();
    }


    static void testSimpleThreadPool() {
        SimpleThreadPool simpleThreadPool = new SimpleThreadPool();

        IntStream.range(0, 40).forEach(i -> {
            simpleThreadPool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + "执行第" + i + "个任务");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}
