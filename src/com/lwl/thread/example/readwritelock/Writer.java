package com.lwl.thread.example.readwritelock;

import java.util.Random;

/**
 * @author liwenlong
 * @date 2021-05-02 10:02
 */
public class Writer implements Runnable {
    private static final Random random = new Random(System.currentTimeMillis());
    private final SharedData sharedData;
    private final String filter;
    private int index;

    public Writer(SharedData sharedData, String filter) {
        this.sharedData = sharedData;
        this.filter = filter;
    }

    @Override
    public void run() {
        try {
            while (true) {
                char c = nextChar();
                sharedData.write(c);
                Thread.sleep(random.nextInt(10));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private char nextChar() {
        char c = filter.charAt(index);
        index++;
        if (index >= filter.length()) {
            index = 0;
        }
        return c;
    }
}
