package com.lwl.tool.forkjoin;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author liwenlong
 * @date 2021-05-06 12:18
 */
public class Main {
    // 分而治之的思想,将大线程分成小线程
    // 类似于算法中的分治算法,一个是有返回值一个无返回值
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        testForkJoinRecursiveAction();
    }

    static void testForkJoinRecursiveTask() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask future = forkJoinPool.submit(new CalculateRecursiveTask(0, 10));
        System.out.println(future.get());
    }

    static void testForkJoinRecursiveAction() throws InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        AtomicInteger sum = new AtomicInteger();
        forkJoinPool.submit(new CalculateRecursiveAction(0, 10, sum));
        Thread.sleep(10_000);
        System.out.println(sum.get());
    }

    private static class CalculateRecursiveTask extends RecursiveTask<Integer> {
        private final int start;
        private final int end;

        public CalculateRecursiveTask(int start, int end) {
            this.start = start;
            this.end = end;
        }


        @Override
        protected Integer compute() {
            if (end - start <= 3) {
                return IntStream.rangeClosed(start, end).sum();
            } else {
                int middle = (start + end) / 2;
                CalculateRecursiveTask left = new CalculateRecursiveTask(start, middle);
                CalculateRecursiveTask right = new CalculateRecursiveTask(middle + 1, end);
                left.fork();
                right.fork();
                return left.join() + right.join();
            }
        }
    }

    private static class CalculateRecursiveAction extends RecursiveAction {

        private final int start;
        private final int end;
        private AtomicInteger sum;

        public CalculateRecursiveAction(int start, int end, AtomicInteger sum) {
            this.start = start;
            this.end = end;
            this.sum = sum;
        }

        @Override
        protected void compute() {
            if (end - start <= 3) {
                sum.addAndGet(IntStream.rangeClosed(start, end).sum());
            } else {
                int middle = (start + end) / 2;
                CalculateRecursiveAction left = new CalculateRecursiveAction(start, middle, sum);
                CalculateRecursiveAction right = new CalculateRecursiveAction(middle + 1, end, sum);
                left.fork();
                right.fork();
                left.join();
                right.join();
            }
        }
    }

}
