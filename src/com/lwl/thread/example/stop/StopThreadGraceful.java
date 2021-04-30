package com.lwl.thread.example.stop;

/**
 * @author liwenlong
 * @date 2021-04-30 9:46
 */
public class StopThreadGraceful {
    public static void main(String[] args) {
        stopByInterrupt();
    }


    static void testSetFlag() {
        Worker worker = new Worker();
        new Thread(worker).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        worker.shutDown();
    }

    static void stopByInterrupt()   {
        Thread t = new Thread(() -> {
            while (true) {
                System.out.println("我正在工作");
                if (Thread.interrupted()) {
                    System.out.println("我放假了");
                    return;
                }
            }
        });
        t.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
    }
}
