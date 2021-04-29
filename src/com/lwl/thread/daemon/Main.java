package com.lwl.thread.daemon;

/**
 * @author liwenlong
 * @date 2021-04-29 21:35
 */
public class Main {
    public static void main(String[] args) {
        /*
         * 当用户线程结束后,不管守护线程有没有执行完,守护线程都会结束
         */
        Thread t = new Thread(() -> {
            while (true) {
                System.out.println("health check");
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.setDaemon(true);
        t.start();
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main 线程结束");
    }
}
