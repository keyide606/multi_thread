package com.lwl.thread.creation;

/**
 * @author liwenlong
 *  继承Thread类,重写run()方法
 *  实现Runnable,实现run()方法
 * @date 2021-04-29 16:49
 */
public class Main {
    public static void main(String[] args) {
        Thread thread = new MyThread();

        Thread runnableThread = new Thread(new MyRunnable());

        thread.start();
        runnableThread.start();
    }
}
