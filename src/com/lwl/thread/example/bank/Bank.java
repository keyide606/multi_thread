package com.lwl.thread.example.bank;

/**
 * @author liwenlong
 * @date 2021-04-29 19:58
 */
public class Bank {
    public static void main(String[] args) {
        TicketWindow ticketWindow = new TicketWindow();

        Thread one = new Thread(ticketWindow, "一号窗口");
        Thread two = new Thread(ticketWindow, "二号窗口");
        Thread three = new Thread(ticketWindow, "三号窗口");
        one.start();
        two.start();
        three.start();
    }
}
