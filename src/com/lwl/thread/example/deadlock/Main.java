package com.lwl.thread.example.deadlock;

/**
 * @author liwenlong
 * @date 2021-04-30 13:31
 */
public class Main {
    /*
    配合jps jstack命令使用
    jps：查看当前的进程
    jstack: 打印进程的栈
     */
    public static void main(String[] args) {
        OneService one = new OneService();

        TwoService two = new TwoService();
        one.setTwoService(two);
        two.setOneService(one);
        Thread t = new Thread(() -> {
            while (true) {
                one.print();
            }
        });

        Thread thread = new Thread(() -> {
            while (true) {
                two.print();
            }
        });

        t.start();
        thread.start();
    }
}
