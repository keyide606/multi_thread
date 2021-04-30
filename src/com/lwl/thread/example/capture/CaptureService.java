package com.lwl.thread.example.capture;

import java.util.*;

/**
 * @author liwenlong
 * @date 2021-04-30 16:30
 */
public class CaptureService {

    private static final LinkedList<Control> controls = new LinkedList<>();

    public static void main(String[] args) {
        List<Thread> workers = new ArrayList<>();

        Arrays.asList("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9", "M10")
                .stream()
                .map(CaptureService::createThread)
                .forEach(t -> {
                    t.start();
                    workers.add(t);
                });


        workers.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Optional.of("all capture finished").ifPresent(System.out::println);
    }


    private static Thread createThread(String name) {
        return new Thread(() -> {
            System.out.println(name + "准备开始做一些事情");
            synchronized (controls) {
                while (controls.size() >= 5) {
                    try {
                        controls.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                controls.addLast(new Control());
            }

            System.out.println(name + "正在处理");
            // 模拟做一些事情
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 事情做完之后
            synchronized (controls) {
                controls.removeFirst();
                controls.notifyAll();
                System.out.println(name + "我结束了");
            }
        }, name);
    }


    private static class Control {

    }
}


