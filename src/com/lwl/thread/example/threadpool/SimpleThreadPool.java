package com.lwl.thread.example.threadpool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author liwenlong
 * @date 2021-04-30 21:45
 */
public class SimpleThreadPool {
    private static final int DEFAULT_SIZE = 10;
    private static final int DEFAULT_QUEUE_SIZE = 30;
    private static final String THREAD_PREFIX = "SIMPLE-THREAD-POOL-";
    private static final String THREAD_GROUP_NAME = "SIMPLE_THREAD_POOL";
    private static final DiscardPolicy DEFAULT_DISCARD_POLICY = () -> {
        throw new DiscardTaskException("task queue is full,can't submit new task.");
    };
    private int size;
    private int taskQueueSize;
    private LinkedList<Runnable> taskQueue;
    private List<WorkerTask> workerTasks;
    private ThreadGroup threadGroup;
    private DiscardPolicy discardPolicy;

    public SimpleThreadPool(int size, int taskQueueSize, DiscardPolicy discardPolicy) {
        this.size = size;
        this.taskQueueSize = taskQueueSize;
        this.discardPolicy = discardPolicy;
        taskQueue = new LinkedList<>();
        threadGroup = new ThreadGroup(THREAD_GROUP_NAME);
        workerTasks = new ArrayList<>();
        init();
    }

    public SimpleThreadPool(int size, int taskQueueSize) {
        this(size, taskQueueSize, DEFAULT_DISCARD_POLICY);
    }

    public SimpleThreadPool() {
        this(DEFAULT_SIZE, DEFAULT_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
    }

    private void init() {
        for (int i = 0; i < size; i++) {
            createWorkTask(THREAD_PREFIX + i);
        }
    }

    private void createWorkTask(String name) {
        WorkerTask workerTask = new WorkerTask(threadGroup, name);
        workerTask.start();
        workerTasks.add(workerTask);
    }

    public void submit(Runnable runnable) {
        if (runnable == null) {
            System.out.println("不能提交空的任务");
        } else {
            if (taskQueue.size() >= taskQueueSize) {
                discardPolicy.discardTask();
            }else {
                synchronized (taskQueue) {
                    taskQueue.offer(runnable);
                    taskQueue.notifyAll();
                }
            }
        }
    }

    private enum ThreadState {
        FREE, BLOCKED, RUNNING, DEAD;
    }

    private class WorkerTask extends Thread {
        private volatile ThreadState threadState;

        public WorkerTask(ThreadGroup threadGroup, String name) {
            super(threadGroup, name);
            threadState = ThreadState.FREE;
        }

        @Override
        public void run() {
            while (!threadState.equals(ThreadState.DEAD)) {
                Runnable runnable = null;
                out:
                synchronized (taskQueue) {
                    while (taskQueue.isEmpty()) {
                        try {
                            this.threadState = ThreadState.BLOCKED;
                            taskQueue.wait();
                        } catch (InterruptedException e) {
                            break out;
                        }
                    }
                    runnable = taskQueue.poll();
                }
                this.threadState = ThreadState.RUNNING;
                runnable.run();
                this.threadState = ThreadState.FREE;
            }
        }
    }

}
