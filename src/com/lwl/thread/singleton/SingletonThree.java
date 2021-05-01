package com.lwl.thread.singleton;


import java.util.stream.IntStream;

/**
 * @author liwenlong
 * @date 2021-05-01 14:42
 */
public class SingletonThree {

    private SingletonThree() {

    }

    private enum Singleton {
        INSTANCE;

        private final SingletonThree instance;

        Singleton() {
            instance = new SingletonThree();
        }


        public SingletonThree getInstance() {
            return instance;
        }
    }

    public static SingletonThree getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 100).forEach(i -> new Thread(String.valueOf(i)) {
            @Override
            public void run() {
                System.out.println(getInstance());
            }
        }.start());
    }
}
