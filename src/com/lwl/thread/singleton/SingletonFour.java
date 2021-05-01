package com.lwl.thread.singleton;

import java.util.stream.IntStream;

/**
 * @author liwenlong
 * @date 2021-05-01 14:55
 */
public class SingletonFour {

    // holder方式,懒加载,线程安全
    private SingletonFour() {

    }

    private static class Instance {
        private static final SingletonFour singleton = new SingletonFour();
    }

    public static SingletonFour getInstance() {
        return Instance.singleton;
    }

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 100).forEach(i -> new Thread(String.valueOf(i)){
            @Override
            public void run() {
                System.out.println(getInstance());
            }
        }.start());
    }

}
