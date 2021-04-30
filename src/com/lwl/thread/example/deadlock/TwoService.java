package com.lwl.thread.example.deadlock;

/**
 * @author liwenlong
 * @date 2021-04-30 13:39
 */
public class TwoService {

    private final Object object = new Object();

    private OneService oneService;

    public void setOneService(OneService oneService) {
        this.oneService = oneService;
    }

    public void print() {
        synchronized (object) {
            oneService.show();
            System.out.println("one---print");
        }
    }

    public void show() {
        synchronized (object) {
            System.out.println("one---show");
        }
    }

}
