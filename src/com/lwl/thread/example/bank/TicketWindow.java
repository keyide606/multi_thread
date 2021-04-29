package com.lwl.thread.example.bank;

/**
 * @author liwenlong
 * @date 2021-04-29 19:59
 */
public class TicketWindow implements Runnable {
    private int index = 1;
    private int MAX = 50;


    @Override
    public void run() {
        while (index <= MAX) {
            System.out.println(Thread.currentThread().getName() + "叫号:" + index++);
        }
    }
}
