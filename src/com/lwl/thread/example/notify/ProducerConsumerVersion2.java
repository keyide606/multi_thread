package com.lwl.thread.example.notify;

/**
 * @author liwenlong
 * @date 2021-04-30 15:19
 */
public class ProducerConsumerVersion2 {
    private final Object object = new Object();

    private int i = 0;

    private boolean produced = false;

    public void produce()  {
        synchronized (object) {
            // 使用while防止其本身正在等待时,获取obejct对象的锁之后,重复生成
            while (produced) {
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i++;
            System.out.println("生成:" + i);
            produced = true;
            object.notifyAll();
        }
    }

    public void consume() {
        synchronized (object) {
            // 使用while防止其本身正在等待时,获取obejct对象的锁之后,重复消费
            while (!produced) {
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("消费:" + i);
            produced = false;
            object.notifyAll();
        }
    }
}
