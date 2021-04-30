package com.lwl.thread.example.stop;

/**
 * @author liwenlong
 * @date 2021-04-30 10:24
 */
public class ThreadService {


    private Thread executeThread;

    private boolean finished = false;

    public void execute(Runnable runnable) {
        executeThread = new Thread(() -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finished = true;
        });
        executeThread.start();
    }

    public void shutdown(long mills)   {
        long currentTime = System.currentTimeMillis();
        while (!finished) {
            if ((System.currentTimeMillis() - currentTime) > mills) {
                System.out.println("任务超时,需要关闭它");
                System.out.println(System.currentTimeMillis() - currentTime);
                executeThread.interrupt();
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(System.currentTimeMillis() - currentTime);
    }
}
