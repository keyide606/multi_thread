package com.lwl.thread.example.deadlock;

/**
 * @author liwenlong
 * @date 2021-04-30 13:32
 */
public class OneService {


    private final Object object = new Object();

    private TwoService twoService;

    public void setTwoService(TwoService twoService) {
        this.twoService = twoService;
    }

    public void print() {
        synchronized (object) {
            twoService.show();
            System.out.println("two---print");

        }
    }

    public void show() {
        synchronized (object) {
            System.out.println("two---show");
        }
    }

}
