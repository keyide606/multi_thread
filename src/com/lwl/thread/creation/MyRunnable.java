package com.lwl.thread.creation;

/**
 * @author liwenlong
 * @date 2021-03-30 9:29
 */
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            System.out.println("实现Runnable:" + i);
        }
    }
}
