package com.lwl.thread.example.lock;

import java.sql.Time;
import java.util.Arrays;

/**
 * @author liwenlong 自已定义一个lock,可以有时间限制
 * @date 2021-04-30 17:08
 */
public class Main {
    public static void main(String[] args) {

        final BooleanLock booleanLock = new BooleanLock();
        Arrays.asList("T1", "T2", "T3", "T4")
                .stream()
                .forEach(name ->
                        new Thread(() -> {
                            try {
                                booleanLock.lock(100);
                                System.out.println(Thread.currentThread().getName() + "获取锁");
                                work();
                            } catch (TimeOutException e) {
                                System.out.println(Thread.currentThread().getName()+"time out");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                booleanLock.unLock();
                            }
                        }, name).start()
                );

    }


    private static void work() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "is working...");
        Thread.sleep(1000);
    }
}
