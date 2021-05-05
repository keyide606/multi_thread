package com.lwl.tool.countdownlatch;


/**
 * @author liwenlong
 * @date 2021-05-04 21:48
 */
public class MyCountDownLatch {
    //todo 明天起床实现
    private volatile int count;
    private Object lock = new Object();


    public MyCountDownLatch(int count) {
        this.count = count;
    }


    public void countDown() {
        if (count < 0) {
            return;
        }
        synchronized (lock) {
            count--;
            lock.notifyAll();
        }
    }

    public void await() {
        synchronized (lock) {
            while (count > 0) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
