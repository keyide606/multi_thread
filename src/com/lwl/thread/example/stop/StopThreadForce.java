package com.lwl.thread.example.stop;

/**
 * @author liwenlong
 * @date 2021-04-30 10:25
 */
public class StopThreadForce {
    public static void main(String[] args) {
        ThreadService threadService = new ThreadService();

        threadService.execute(() -> {
            // 模拟加载一个很大的资源,超时
//            while (true){
//
//            }
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadService.shutdown(10_000);
    }
}
