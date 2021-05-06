package com.lwl.tool.phaser;


import java.util.concurrent.Phaser;

/**
 * @author liwenlong 比赛的案子:按照初赛,预赛,决赛不断减少
 * @date 2021-05-06 16:00
 */
public class Runner implements Runnable {
    private String name;
    private Phaser phaser;

    public Runner(Phaser phaser, String name) {
        this.phaser = phaser;
        this.name = name;
    }

    @Override
    public void run() {
        /**
         * 参加预赛
         */
        System.out.println("选手-" + name + ":参加预赛");
        if ("A".equals(name)) {
            phaser.arriveAndAwaitAdvance();
            phaser.arriveAndDeregister();
            return;
        }
        phaser.arriveAndAwaitAdvance();
        /**
         * 参加初赛
         */
        System.out.println("选手-" + name + ":参加初赛");
        /**
         * 初赛阶段-----执行这个方法的话会等所有的选手都完成了之后再继续下面的方法
         */
        if ("B".equals(name)) {
            phaser.arriveAndAwaitAdvance();
            phaser.arriveAndDeregister();
            return;
        }
        phaser.arriveAndAwaitAdvance();
        /**
         * 参加决赛
         */
        System.out.println("选手-" + name + ":参加决赛");
        /**
         * 决赛阶段-----执行这个方法的话会等所有的选手都完成了之后再继续下面的方法
         */
        phaser.arriveAndAwaitAdvance();
    }

}
