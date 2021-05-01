package com.lwl.thread.example.threadpool;

/**
 * @author liwenlong
 * @date 2021-05-01 11:26
 */
public interface DiscardPolicy {
    void discardTask() throws DiscardTaskException;
}
