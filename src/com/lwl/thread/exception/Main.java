package com.lwl.thread.exception;

/**
 * @author liwenlong
 * @date 2021-04-30 19:45
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        int a = 10;
        int b = 0;

        Thread t = new Thread(() -> {
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int i = a / b;
            System.out.println(i);
        });
        // 能够获取到线程中的异常
        t.setUncaughtExceptionHandler((thread, e) -> {
            System.out.println(thread.getName());
            System.out.println(e);
        });

        t.start();
    }
}
