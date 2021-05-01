package com.lwl.thread.singleton;

/**
 * @author liwenlong
 * @date 2021-05-01 14:38
 */
public class SingletonTwo {

    private static volatile SingletonTwo singletonTwo;

    private SingletonTwo() {
    }

    // 饱汉模式,使用到的时候在加载
    public static SingletonTwo getInstance() {
        if (singletonTwo == null) {
            synchronized (SingletonTwo.class) {
                if (singletonTwo == null){
                    singletonTwo = new SingletonTwo();
                }
            }
        }
        return singletonTwo;
    }
}
