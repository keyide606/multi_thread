package com.lwl.thread.example.notify;

import java.util.stream.Stream;

/**
 * @author liwenlong
 * @date 2021-04-30 14:12
 */
public class Main {
    public static void main(String[] args) {
        testVersion2();
    }

    /**
     * 单个生产者和单个消费者的问题,多个生产者和消费者会出现所有都会等待的情况,
     * 具体见version2代码
     */
    static void testVersion1() {
        ProducerConsumerVersion1 factory = new ProducerConsumerVersion1();

        new Thread(() -> {
            while (true) {
                factory.produce();
            }
        }).start();

        new Thread(() -> {
            while (true) {
                factory.consume();
            }
        }).start();
    }

    static void testVersion2() {
        ProducerConsumerVersion2 factory = new ProducerConsumerVersion2();

        Stream.of("p1", "p2").forEach(n -> new Thread(() -> {
            while (true) {
                factory.produce();
            }
        }).start());


        Stream.of("c1", "c2").forEach(n -> new Thread(() -> {
            while (true) {
                factory.consume();
            }
        }).start());
    }
}
