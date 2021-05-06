package com.lwl.tool.exchanger;

import java.util.concurrent.Exchanger;

/**
 * @author liwenlong
 * @date 2021-05-05 22:21
 */
public class Main {
    public static void main(String[] args) {
        testExchanger();
    }

    // 只使用于两个线程之间交换数据,线程一定要成对出现,一个线程交换数据后,会阻塞住,直到另一个线程交换数据,它才继续运行
    static void testExchanger() {
        Exchanger<String> exchanger = new Exchanger<>();
        new Thread(("A")) {
            @Override
            public void run() {
                try {
                    String result = exchanger.exchange(":I am from A");
                    System.out.println(Thread.currentThread().getName() + result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread(("B")) {
            @Override
            public void run() {
                try {
                    String result = exchanger.exchange(":I am from B");
                    System.out.println(Thread.currentThread().getName()  + result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
