package com.lwl.tool.countdownlatch;


import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author liwenlong jdk实现为aqs
 * @date 2021-05-04 11:53
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        testMyCountDownLatch();
    }

    static void testCountDownLatch() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(10);
        Random runtime = new Random(System.currentTimeMillis());
        IntStream.rangeClosed(1, 10).forEach(i -> new Thread(String.valueOf(i)) {
            @Override
            public void run() {
                try {
                    // 模拟做一些事情
                    Thread.sleep(runtime.nextInt(100));
                    System.out.println(Thread.currentThread().getName() + "我执行完了");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start());
        latch.await();
        System.out.println("全部执行完毕,我要开始做我的事情了");
    }

    static void testCountDownLatchApi() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        new Thread(() -> {
            try {
                Thread.sleep(100_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        // 超过时间不等了
        countDownLatch.await(1, TimeUnit.SECONDS);
        System.out.println("=====");
    }

    // 测试下我自己的CountDownLatch
    static void testMyCountDownLatch() {
        MyCountDownLatch latch = new MyCountDownLatch(10);
        Random runtime = new Random(System.currentTimeMillis());
        IntStream.rangeClosed(1, 10).forEach(i -> new Thread(() -> {
            try {
                Thread.sleep(runtime.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"执行完毕");
            latch.countDown();
        }).start());
        latch.await();
        System.out.println("准备录取成绩");
    }
}
