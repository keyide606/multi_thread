package com.lwl.tool.completablefuture;

import java.sql.Time;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @author liwenlong
 * @date 2021-05-14 16:00
 */
public class TestCompletableFuture {


    public static void main(String[] args) {
        complexUseCompletableFutureV2();
    }


    static void simpleUseCompletableFuture() {
        // 相当于传入给一个future一个consumer,执行后根据返回结果去执行这个consumer
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("====");
        }).whenComplete((v, t) -> System.out.println("result is " + t));

        System.out.println("主线程结束");
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void complexUseCompletableFuture() {
        IntStream.rangeClosed(0, 10).forEach(i ->
                CompletableFuture.supplyAsync(TestCompletableFuture::getValue)
                        .whenComplete((v, t) -> System.out.println("执行后的结果为:" + v))
        );

        System.out.println("主线程没有阻塞哦");
        Thread t = Thread.currentThread();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void complexUseCompletableFutureV2() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        IntStream.rangeClosed(0, 10).forEach(i ->
                CompletableFuture.supplyAsync(TestCompletableFuture::getValue, executorService)
                        .whenComplete((v, t) -> System.out.println("执行后的结果为:" + v))
        );

        System.out.println("会用自己的线程池,不用join,主线程没有阻塞哦");
        // 自己定义记得关闭线程池哦
        executorService.shutdown();
    }

    private static int getValue() {
        int value = ThreadLocalRandom.current().nextInt(20);
        try {
            System.out.println(Thread.currentThread().getName() + " will be sleep");
            TimeUnit.SECONDS.sleep(value);
            System.out.println(Thread.currentThread().getName() + " finish task ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return value;
    }
}
