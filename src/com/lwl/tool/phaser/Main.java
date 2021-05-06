package com.lwl.tool.phaser;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;

/**
 * @author liwenlong
 * @date 2021-05-06 15:13
 */
public class Main {
    public static void main(String[] args) {
        int runnerNum = 4;
        String str = "ABCD";
        GamePhaser gamePhaser = new GamePhaser(4);
        for (int i = 0; i < runnerNum; i++) {
            new Thread(new Runner(gamePhaser, "" + str.charAt(i))).start();
        }

    }

    public static class GamePhaser extends Phaser {

        public GamePhaser(int parties) {
            super(parties);
        }
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase) {
                case 0:
                    System.out.println("预赛完成");
                    return false;
                case 1:
                    System.out.println("初赛完成");
                    return false;
                case 2:
                    System.out.println("决赛完成");
                    return false;
                default:
                    return true;
            }
        }
    }
}
