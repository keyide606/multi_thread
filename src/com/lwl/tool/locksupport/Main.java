package com.lwl.tool.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * @author liwenlong
 * @date 2021-05-06 15:47
 */
public class Main {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 10; i++) {

                System.out.println(i);
                if (i == 5) {
                    LockSupport.park();
                }
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        try {
            Thread.sleep(8_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 提前调用LockSupport.unpark(t),在线程t第一次调用LockSupport.park(),不会发生阻塞;
        LockSupport.unpark(t);
    }
}
