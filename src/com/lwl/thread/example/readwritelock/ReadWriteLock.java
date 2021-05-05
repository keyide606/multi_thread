package com.lwl.thread.example.readwritelock;


/**
 * @author liwenlong
 * @date 2021-05-02 10:01
 */
public class ReadWriteLock {

    // 正在读的线程数
    private volatile int readingReaders;
    // 正在等待读的线程数
    private volatile int waitingReaders;
    // 正在写的线程数
    private volatile int writingWriters;
    // 正在等待写的线程数
    private volatile int waitingWriters;
    // 此为开关,用来控制是否更倾向于写
    private boolean preferWriting = true;

    public synchronized void readLock() throws InterruptedException {
        waitingReaders++;
        // 如果有正在写的人,那么获取读锁失败
        try {
            while (writingWriters > 0 || (preferWriting && waitingWriters > 0)) {
                this.wait();
            }
            // 当前线程获取读锁,正在读的人+1
            readingReaders++;
        } finally {
            waitingReaders--;
        }
    }

    public synchronized void unReadLock() {
        readingReaders--;
        this.notify();
    }

    public synchronized void writeLock() throws InterruptedException {
        waitingWriters++;
        try {
            // 如果有人正在读或者正在写,那么当前线程阻塞
            while (readingReaders > 0 || writingWriters > 0) {
                this.wait();
            }
            writingWriters++;
        } finally {
            waitingWriters--;
        }
    }

    public synchronized void unWriteLock() {
        writingWriters--;
        this.notifyAll();
    }
}
