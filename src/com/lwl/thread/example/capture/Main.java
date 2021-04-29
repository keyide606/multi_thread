package com.lwl.thread.example.capture;

/**
 * @author liwenlong
 * @date 2021-04-29 22:29
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        Thread one = new Thread(new CaptureRunnable("一号机器", 10_000));
        Thread two = new Thread(new CaptureRunnable("二号机器", 20_000));
        Thread three = new Thread(new CaptureRunnable("三号机器", 15_000));
        one.start();
        two.start();
        three.start();
        one.join();
        two.join();
        three.join();
        long endTime = System.currentTimeMillis();
        System.out.println("采集完毕,时间为:" + (endTime - startTime));

    }
}
