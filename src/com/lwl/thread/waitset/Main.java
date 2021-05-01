package com.lwl.thread.waitset;

import com.lwl.thread.example.lock.Lock;

import java.util.stream.IntStream;

/**
 * @author liwenlong
 * @date 2021-05-01 18:41
 */
public class Main {

    /*
     关于调用wait的一些测试
     1.看wait方法可以看到,调用某个对象的wait方法,那么这个调用线程会进入对象的waitSet中
     2.waitSet中的线程不是先进先出的,至少常用的hotspot不是这样的
     3.调用对象的notify或者notifyAll方法,此对象的waitSet中的线程不会立刻执行,执行之前必须要获取锁
     4.wait是会释放对象的锁,但是再次获取锁之后,因为会有线程上下文的切换
     下面针对上面说的,简单测试一下
     */
    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 40).forEach(i -> new Thread(String.valueOf(i)) {
            @Override
            public void run() {
                synchronized (LOCK) {
                    System.out.println(i + "阻塞了");
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName()+ "执行完成");
            }
        }.start());


        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        IntStream.rangeClosed(1, 40).forEach(i -> {
            synchronized (LOCK) {
                LOCK.notify();
            }
        });
    }


}
