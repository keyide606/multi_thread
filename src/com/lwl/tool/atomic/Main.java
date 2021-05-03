package com.lwl.tool.atomic;

import java.util.concurrent.atomic.*;
import java.util.stream.IntStream;

/**
 * @author liwenlong 原子类测试
 * @date 2021-05-02 16:28
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        testAtomicIntegerArray();
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
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        System.out.println(atomicBoolean.get());
        System.out.println(atomicBoolean.compareAndSet(false, true));
    }

    // 测试AtomicReference
    static void testAtomicReference() throws InterruptedException {

        // 多线程去对会员卡中少于20元的账户进行充值活动
        /*
        1.线程1 察觉到账户A中的余额为10元，准备充值,突然cpu切换到线程B
        2.线程2 察觉到账户A中的余额为10元，对其充值,然后为30元
        3.线程3 察觉到账户A中的钱大于20元,然后消费20元，变为10元
        4.对于线程A来说,金钱从10-30-10貌似没变，然后准备充值,但是实际不应该充值，ABA问题的困扰
        使用AtomicStampedReference

         */
        AtomicReference<Integer> money = new AtomicReference<>(10);

        new Thread(() -> {
            Integer m = money.get();
            if (m < 20) {
                try {
                    Thread.sleep(5_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (money.compareAndSet(m, m + 20)) {
                    System.out.println(Thread.currentThread().getName() + "充值了20元");
                }
            }
        }, "A").start();

        // 一个线程不断消耗会员卡中的钱
        new Thread(() -> {
            while (true) {
                Integer m = money.get();
                if (m > 20) {
                    if (money.compareAndSet(m, m - 20)) {
                        System.out.println(Thread.currentThread().getName() + "消费了20元");
                    }
                }
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 一个线程不断消耗会员卡中的钱
        new Thread(() -> {
            Integer m = money.get();
            if (m < 20) {
                if (money.compareAndSet(m, m + 20)) {
                    System.out.println(Thread.currentThread().getName() + "充值了20元");
                }
            }
        }, "B").start();
    }


    static void testAtomicStampedReference() {
        AtomicStampedReference<Integer> money = new AtomicStampedReference<>(10, 0);
        new Thread(() -> {
            int[] stamped = new int[1];
            Integer m = money.get(stamped);
            if (m < 20) {
                try {
                    Thread.sleep(5_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (money.compareAndSet(m, m + 20, stamped[0], stamped[0] + 1)) {
                    System.out.println(Thread.currentThread().getName() + "充值了20元");
                } else {
                    System.out.println(Thread.currentThread().getName() + "失败了,m=" + m);
                }
            }
        }, "A").start();

        // 一个线程不断消耗会员卡中的钱
        new Thread("consumer") {
            @Override
            public void run() {
                while (true) {
                    int[] stamped = new int[1];
                    Integer m = money.get(stamped);
                    if (m > 20) {
                        if (money.compareAndSet(m, m - 20, stamped[0], stamped[0] + 1)) {
                            System.out.println(Thread.currentThread().getName() + "消费了20元");
                        }
                    }
                    try {
                        Thread.sleep(1_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


        new Thread(() -> {
            int[] stamped = new int[1];
            Integer m = money.get(stamped);
            if (m < 20) {
                if (money.compareAndSet(m, m + 20, stamped[0], stamped[0] + 1)) {
                    System.out.println(Thread.currentThread().getName() + "充值了20元");
                }
            }
        }, "B").start();

    }

    // 其实是对数组中的元素的操作是原子性的,AtomicLongArray和AtomicReferenceArray类似
    static void testAtomicIntegerArray() throws InterruptedException {

        AtomicIntegerArray array = new AtomicIntegerArray(new int[10]);

        System.out.println(array.get(5));

        IntStream.rangeClosed(1, 10).forEach(i -> new Thread(() -> {
            for (int j = 0; j < 20; j++) {
                System.out.println(array.getAndIncrement(5));
            }
        }).start());

        Thread.sleep(2_000);
        System.out.println(array.get(5));
    }

}
