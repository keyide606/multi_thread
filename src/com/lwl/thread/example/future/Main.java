package com.lwl.thread.example.future;

/**
 * @author liwenlong
 * @date 2021-05-06 10:50
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        FutureService service = new FutureService();
        Future<String> future = service.submit(() -> {
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "已经完成了";
        });

        // do something
        // 实际上就是：我们提交一个任务给service,他给我们一个future,
        // 后台起一个线程帮忙完成任务,完成之后将结果给future,我们通过future可以将完成任务的结果获取到
        System.out.println("=====");
        System.out.println("我吃饭,睡觉,跑步,写代码...");
        Thread.sleep(10000);
        System.out.println(future.get());
    }
}
