package com.lwl.thread.example.stop;

/**
 * @author liwenlong
 * @date 2021-04-30 10:03
 */
public class Worker implements Runnable {

    private volatile boolean flag = true;

    @Override
    public void run() {
        while (flag) {
            System.out.println("我正在工作");
        }
    }

    public void shutDown() {
        System.out.println("你放假了");
        flag = false;
    }
}
