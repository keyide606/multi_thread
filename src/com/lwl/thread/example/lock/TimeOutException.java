package com.lwl.thread.example.lock;

/**
 * @author liwenlong
 * @date 2021-04-30 17:08
 */
public class TimeOutException extends Exception {


    public TimeOutException(String message){
        super(message);
    }
}
