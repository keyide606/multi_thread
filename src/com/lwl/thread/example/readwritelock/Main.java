package com.lwl.thread.example.readwritelock;

/**
 * @author liwenlong
 * @date 2021-05-05 12:50
 */
public class Main {
    public static void main(String[] args) {
        char[] chars = new char[10];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = '*';
        }
        ReadWriteLock readWriteLock = new ReadWriteLock();
        SharedData sharedData = new SharedData(chars, readWriteLock);

        new Thread(new Reader(sharedData, "A")).start();
        new Thread(new Reader(sharedData, "B")).start();
        new Thread(new Reader(sharedData, "C")).start();
        new Thread(new Reader(sharedData, "D")).start();
        new Thread(new Reader(sharedData, "E")).start();
        new Thread(new Reader(sharedData, "F")).start();
        new Thread(new Reader(sharedData, "G")).start();
        new Thread(new Reader(sharedData, "H")).start();
        new Thread(new Writer(sharedData, "javaWorldNice")).start();
    }
}
