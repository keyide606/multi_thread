package com.lwl.thread.example.singlepass;

/**
 * @author liwenlong
 * @date 2021-05-01 23:07
 */
public class Test {
    public static void main(String[] args) {
        Gate gate = new Gate();

        User one = new User("bj", "beijing", gate);
        User two = new User("sh", "shanghai", gate);
        User three = new User("gz", "guangzhou", gate);
        one.start();
        two.start();
        three.start();
    }
}
