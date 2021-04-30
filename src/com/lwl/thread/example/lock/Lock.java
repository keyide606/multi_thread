package com.lwl.thread.example.lock;

import java.util.Collection;

/**
 * @author liwenlong
 * @date 2021-04-30 17:08
 */
public interface Lock {

    // 等待获取锁
    void lock() throws InterruptedException;
    // 释放锁
    void unLock();
    // 等待获取锁,一定时间获取不到就不玩了
    void lock(long mills) throws InterruptedException, TimeOutException;


    Collection<Thread> getBlockedThread();

    int getBlockedThreadSize();

}
