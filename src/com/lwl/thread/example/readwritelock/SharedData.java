package com.lwl.thread.example.readwritelock;

/**
 * @author liwenlong
 * @date 2021-05-05 12:52
 */
public class SharedData {
    private char[] data;
    private ReadWriteLock readWriteLock;

    public SharedData(char[] data, ReadWriteLock readWriteLock) {
        this.data = data;
        this.readWriteLock = readWriteLock;
    }


    public char[] read() {
        try {
            readWriteLock.readLock();
            return doRead();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.unReadLock();
        }
        return null;
    }

    private char[] doRead() {
        char[] result = new char[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = data[i];
        }
        return result;
    }

    public void write(char c) {
        try {
            readWriteLock.writeLock();
            doWrite(c);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.unWriteLock();
        }
    }

    private void doWrite(char c) {
        for (int i = 0; i < data.length; i++) {
            data[i] = c;
        }
    }
}
