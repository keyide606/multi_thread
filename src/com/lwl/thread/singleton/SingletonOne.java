package com.lwl.thread.singleton;

/**
 * @author liwenlong 饿汉式的模式
 * @date 2021-05-01 13:39
 */
public class SingletonOne {

    // 饿汉式模式,在类初始化阶段就已经加载
    private static final SingletonOne singletonOne = new SingletonOne();

    private SingletonOne() {

    }

    public static SingletonOne getInstance() {
        return singletonOne;
    }
}
