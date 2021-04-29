package com.lwl.thread.join;

/**
 * @author liwenlong
 * @date 2021-04-29 22:22
 */
public class Main {
    /*
     t.join()方法作用:当前线程等待线程t执行完毕后才执行
     t.join(100):当前线程等待线程t执行100ms后执行
     */
    public static void main(String[] args) throws InterruptedException {
//        testJoin();
        testJoinTime();
    }

    static void testJoin() throws InterruptedException {
        Thread t = new Thread(() -> {
            System.out.println("开始执行");
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("执行完毕");
        });
        t.start();
        t.join();

        System.out.println("main执行完毕");
    }

    static void testJoinTime() throws InterruptedException {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        });
        t.start();
        t.join(100);

        System.out.println("main执行完毕");
    }
}
