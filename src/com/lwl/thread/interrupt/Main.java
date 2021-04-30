package com.lwl.thread.interrupt;

/**
 * @author liwenlong
 * @date 2021-04-29 22:43
 */
public class Main {
    /*
     t.interrupt()会给线程t一个中断信号
     Thread中的静态方法interrupted()和实例方法interrupted()都是一样的，都是获取当前执行线程是否有被打断的信号
     如果线程t调用了sleep(),wait(),join()方法那么会捕获异常InterruptedException
     下面是例子
     */
    public static void main(String[] args) {
        testJoin();
    }

    static void getInterruptFlag() {
        Thread t = new Thread(() -> {
            System.out.println("我正在执行,将要睡眠了");
            if (Thread.interrupted()) {
                System.out.println("获取到异常信号了");
            }
        });
        t.start();
        System.out.println(t.isInterrupted());
        t.interrupt();
        System.out.println(t.isInterrupted());
    }

    static void testSleep() {
        Thread t = new Thread(() -> {
            System.out.println("我正在执行,将要睡眠了");
            try {
                Thread.sleep(10_00);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
        System.out.println(t.isInterrupted());
        t.interrupt();
        System.out.println(t.isInterrupted());
    }

    static void testWait() {
        Object monitor = new Object();
        Thread t = new Thread(() -> {
            System.out.println("我正在执行,将要等待了");
            synchronized (monitor) {
                try {
                    monitor.wait(10_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        System.out.println(t.isInterrupted());
        t.interrupt();
        System.out.println(t.isInterrupted());
    }

    static void testJoin() {
        Thread main = Thread.currentThread();
        Thread t = new Thread(() -> {
            System.out.println("正在执行啊");
            System.out.println("准备打断main线程");
            main.interrupt();
        });

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            System.out.println("main线程被打断");
            e.printStackTrace();
        }

    }
}
