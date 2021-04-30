package com.lwl.thread.threadgroup;

import java.util.Arrays;

/**
 * @author liwenlong
 * @date 2021-04-30 20:15
 */
public class Main {
    /*
    尝试使用一下ThreadGroup的api
     */
    public static void main(String[] args) {
        ThreadGroup g = Thread.currentThread().getThreadGroup();
        // 输出线程组名称
        System.out.println(g.getName());
        // 输出其父线程组的名称
        System.out.println(g.getParent().getName());
        // 查看线程组活跃的线程数
        System.out.println(g.activeCount());
        // 打印线程组中活跃线程数
        Thread[] threads = new Thread[g.activeCount()];

        g.enumerate(threads);

        Arrays.asList(g).stream().forEach(System.out::println);
    }
}
