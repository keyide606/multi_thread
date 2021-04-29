package com.lwl.thread.priority;

/**
 * @author liwenlong
 * @date 2021-04-29 22:00
 */
public class Main {
    public static void main(String[] args) {
        /*
         打印一下线程的id，线程的优先级以及测试一下线程优先级是否会影响线程的执行顺序
         根据结果来看,并不会严格按照优先级的顺序执行
         */
        printInfo();
        testPriority();
    }

    static void printInfo() {
        Thread t = new Thread(() -> {
            System.out.println("hello");
        });
        t.start();
        System.out.println(t.getId());
        System.out.println(t.getPriority());
    }

    static void testPriority() {
        Thread one = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                System.out.println("one");
            }
        });
        one.setPriority(Thread.NORM_PRIORITY);
        Thread two = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                System.out.println("two");
            }
        });
        two.setPriority(Thread.MAX_PRIORITY);
        Thread three = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                System.out.println("three");
            }
        });

        three.setPriority(Thread.MIN_PRIORITY);
        three.start();
        one.start();
        two.start();
    }
}
