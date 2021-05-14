package com.lwl.tool.completablefuture;


import java.util.concurrent.*;

/**
 * @author liwenlong
 * @date 2021-05-14 15:02
 */
public class TestFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        testFutureCancel();
    }


    static void testFutureGet() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<Integer> future = executorService.submit(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });
        // 调用这个方法会陷入阻塞,等到线程执行完然后返回结果
        Integer result = future.get();
        System.out.println(result);
        executorService.shutdown();
    }

    static void testFutureGetAndInterrupt() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Future<Integer> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });
        Thread currentThread = Thread.currentThread();
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentThread.interrupt();
            }
        };
        t.start();
        // 调用这个方法,阻塞一定时间,操作时间还没有返回结果抛出异常TimeoutException
        try {
            Integer result = future.get();
            System.out.println(result);
        } finally {
            executorService.shutdown();
        }
    }

    static void testFutureGetWaitTime() throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<Integer> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });
        // 调用这个方法,阻塞一定时间,操作时间还没有返回结果抛出异常TimeoutException
        try {
            Integer result = future.get(1, TimeUnit.SECONDS);
            System.out.println(result);
        } finally {
            executorService.shutdown();
        }
    }

    static void testFutureIsDone() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Future<Integer> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });
        System.out.println("任务做完了吗:" + future.isDone());
        executorService.shutdown();
    }

    static void testFutureCancel() throws InterruptedException {
        // 尝试取消这个任务的执行
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Future<Integer> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println("即将执行完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });
        TimeUnit.SECONDS.sleep(2);
        // 取消之后仍然会执行
        future.cancel(true);
        executorService.shutdown();
    }
}
