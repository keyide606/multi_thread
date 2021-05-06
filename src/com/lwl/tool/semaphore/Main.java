package com.lwl.tool.semaphore;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author liwenlong
 * @date 2021-05-05 15:52
 */
public class Main {
    private static int count = 20;

    /*
    Semaphore 信号量，用来控制同一时间，资源可被访问的线程数量，一般可用于流量的控制。
     */
    public static void main(String[] args) {
        testSemaphore();
    }

    static void testSemaphore() {
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < count; i++) {
            final int no = i;
            executorService.execute(() -> {
                try {
                    //获得许可
                    semaphore.acquire();
                    System.out.println(no + ":号车可通行");
                    //模拟车辆通行耗时
                    Thread.sleep(2000);
                    //释放许可
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }
}
