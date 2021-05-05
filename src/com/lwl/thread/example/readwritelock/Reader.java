package com.lwl.thread.example.readwritelock;

import java.util.Random;

/**
 * @author liwenlong
 * @date 2021-05-02 10:02
 */
public class Reader implements Runnable {
    private final SharedData sharedData;
    private final String name;

    public Reader(SharedData sharedData, String name) {
        this.sharedData = sharedData;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            char[] chars = sharedData.read();
            System.out.println(name + ":" + new String(chars));
        }
    }
}
