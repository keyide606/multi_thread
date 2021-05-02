package com.lwl.tool.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liwenlong
 * @date 2021-05-02 18:58
 */
public class CompareAndSetLock {
    // 0:代表无锁 1.代表有锁
    private AtomicInteger integer = new AtomicInteger(0);
    private Thread currentThread;

    public void tryLock() throws GetLockException {
        // 如果为0然后获取锁
        if (integer.getAndSet(1) != 0) {
            throw new GetLockException(Thread.currentThread().getName() + "获取锁失败");
        }
        currentThread = Thread.currentThread();
        System.out.println(currentThread.getName() + "获取了锁");
    }

    public void unlock() {
        if (0 == integer.get()) {
            return;
        }
        if (integer.getAndSet(0) == 1 && currentThread == Thread.currentThread()) {
            System.out.println(currentThread + "释放了锁");
        }
    }
}
