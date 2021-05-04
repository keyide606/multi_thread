package com.lwl.classloader;

/**
 * @author liwenlong
 * @date 2021-05-02 13:56
 */
public class User {
    static {
        System.out.println("test my classLoader ...");
    }


    private void hello() {
        System.out.println("hello,world!");
    }
}
