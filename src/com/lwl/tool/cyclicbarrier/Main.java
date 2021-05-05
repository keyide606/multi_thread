package com.lwl.tool.cyclicbarrier;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author liwenlong
 * @date 2021-05-04 22:21
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        // 多个线程互相之间等待,所有都准备好之后,在运行
        testCyclicBarrierApi();
    }


    static void testCyclicBarrier() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        new Thread("A") {
            @Override
            public void run() {
                try {
                    Thread.sleep(1_000);
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "启航");
            }
        }.start();
        new Thread("B") {
            @Override
            public void run() {
                try {
                    Thread.sleep(5_000);
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "启航");
            }
        }.start();
    }


    static void testCyclicBarrierApi() throws InterruptedException {
        // CyclicBarrier可以循环使用,初始时=reset=finished时等待的线程数

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            System.out.println("各就位...");
            try {
                Thread.sleep(3_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            };
            System.out.println("跑...");
        });

        new Thread(new Runner("张三",cyclicBarrier)).start();
        new Thread(new Runner("李四",cyclicBarrier)).start();
        Thread.sleep(5_000);
        new Thread(new Runner("王五",cyclicBarrier)).start();
        new Thread(new Runner("赵六",cyclicBarrier)).start();
    }


    static class Runner implements Runnable {
        CyclicBarrier barrier;
        String name;

        public Runner(String name, CyclicBarrier barrier) {
            this.name = name;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            System.out.println(name + "就绪");
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(name + "跑");
        }
    }
}
