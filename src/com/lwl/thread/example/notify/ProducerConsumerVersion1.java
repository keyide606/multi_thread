package com.lwl.thread.example.notify;

/**
 * @author liwenlong
 * @date 2021-04-30 14:40
 */
public class ProducerConsumerVersion1 {

    private Object object = new Object();

    private int i = 0;

    private boolean produced = false;

    public void produce() {
        synchronized (object) {
            if (produced) {
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                i++;
                System.out.println("生成:" + i);
                produced = true;
                object.notify();
            }
        }
    }

    public void consume() {
        synchronized (object) {
            if (produced) {
                System.out.println("消费:" + i);
                produced = false;
                object.notify();
            } else {
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
