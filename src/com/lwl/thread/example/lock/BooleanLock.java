package com.lwl.thread.example.lock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeoutException;

/**
 * @author liwenlong
 * @date 2021-04-30 17:14
 */
public class BooleanLock implements Lock {

    // false代表锁为被使用,true带边锁已经被使用
    private boolean initValue;
    private Thread currentThread;
    private Collection<Thread> threads;

    public BooleanLock() {
        this.initValue = false;
        threads = new ArrayList<>();
    }

    @Override
    public synchronized void lock() throws InterruptedException {
        // 如果已经被使用那么进入等待队列
        while (initValue) {
            threads.add(Thread.currentThread());
            this.wait();
        }
        // 即将获取锁
        initValue = true;
        currentThread = Thread.currentThread();
        threads.remove(currentThread);
    }

    @Override
    public synchronized void unLock() {
        // 如果是当前线程上的锁,那么它自己才能释放锁
        if (Thread.currentThread() == currentThread) {
            this.initValue = false;
            System.out.println(Thread.currentThread().getName() + "释放了锁");
            this.notifyAll();
        }
    }

    // 代码多少秒获取不到锁之后就不玩了,直接放弃
    @Override
    public synchronized void  lock(long mills) throws InterruptedException, TimeOutException {
        if (mills <= 0) {
            lock();
            return;
        }
        long remainingTime = mills;
        long endTime = System.currentTimeMillis() + mills;
        // 如果已经被使用那么进入等待队列
        while (initValue) {
            if (remainingTime <= 0) {
                System.out.println(Thread.currentThread().getName() + "我不玩了,你们玩!!");
                throw new TimeOutException("time out");
            }
            threads.add(Thread.currentThread());
            this.wait();
            remainingTime = endTime - System.currentTimeMillis();
        }
        // 即将获取锁
        initValue = true;
        currentThread = Thread.currentThread();
        threads.remove(currentThread);
    }


    @Override
    public Collection<Thread> getBlockedThread() {
        return Collections.unmodifiableCollection(threads);
    }

    @Override
    public int getBlockedThreadSize() {
        return threads.size();
    }
}
