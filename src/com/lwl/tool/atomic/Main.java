package com.lwl.tool.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author liwenlong 原子类测试
 * @date 2021-05-02 16:28
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        testCompareAndSetLock();
    }


    static void testAtomicInteger() throws InterruptedException {
        // 应用cas原子类操作
        AtomicInteger integer = new AtomicInteger();
        Thread one = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(integer.getAndIncrement());
            }
        });

        Thread two = new Thread(() -> {
            for (int k = 0; k < 100; k++) {
                System.out.println(integer.getAndIncrement());
            }
        });
        one.start();
        two.start();
        one.join();
        two.join();
        System.out.println("======结果=======");
        System.out.println(integer.get());

        // 调用一下api,获取值,然后增加一下
        System.out.println(integer.getAndAdd(5));
        // 调用一下api,获取值,然后增加一下
        System.out.println(integer.getAndSet(150));
        // 比较一下,如果是,那么设置为新值
        System.out.println(integer.compareAndSet(150, 200));
        System.out.println(integer.get() == 200);
    }

    static void testCompareAndSetLock() {
        // 利用atomic实现一个简答的锁
        CompareAndSetLock lock = new CompareAndSetLock();
        IntStream.rangeClosed(0, 5).forEach(i -> new Thread(() -> {
            try {
                lock.tryLock();
                // do something
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (GetLockException e) {
                e.printStackTrace();
            }
        }, String.valueOf(i)).start());
    }


    static void testAtomicBoolean() {

    }

    static void testAtomicDouble() {

    }

    static void testAtomicFloat() {

    }

    static void testAtomicCharacter() {

    }
}
