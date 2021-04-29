package com.lwl.thread.example.capture;

/**
 * @author liwenlong
 * @date 2021-04-29 22:29
 */
public class CaptureRunnable implements Runnable {


    private String machine;
    private long spendTime;

    public CaptureRunnable(String machine, long spendTime) {
        this.machine = machine;
        this.spendTime = spendTime;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(spendTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(machine + "采集数据成功");
    }
}
