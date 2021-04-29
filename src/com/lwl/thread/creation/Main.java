package com.lwl.thread.creation;

import java.util.Arrays;

/**
 * @author liwenlong
 * 继承Thread类,重写run()方法
 * 实现Runnable,实现run()方法
 * @date 2021-04-29 16:49
 */
public class Main {
    public static void main(String[] args) {
        testCreateThreadGroup();
    }

    static void simpleCreate() {
        // 线程如果不起名字,默认名称为Thread-i i从0开始
        Thread thread = new MyThread();

        Thread runnableThread = new Thread(new MyRunnable());

        thread.start();
        runnableThread.start();
    }

    /*
    线程组相关知识
     */
    static void testCreateThreadGroup() {

        /*
        创建线程时候如果不指定线程组,那么创建的线程默认和其父线程在同一个组内
         */
        Thread t = new Thread(() -> {
            System.out.println("准备睡眠");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getThreadGroup().getName());
        System.out.println(t.getThreadGroup().getName());

        // 打印当前线程组中线程
        ThreadGroup threadGroup = t.getThreadGroup();
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        Arrays.asList(threads).stream().forEach(System.out::println);
    }
}
