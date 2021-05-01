package com.lwl.thread.example.threadpool;

/**
 * @author liwenlong
 * @date 2021-05-01 11:26
 */
public class DiscardTaskException extends RuntimeException {
    public DiscardTaskException(String message) {
        super(message);
    }
}
