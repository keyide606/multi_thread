package com.lwl.thread.example.threadpool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author liwenlong
 * @date 2021-04-30 21:45
 */
public class SimpleThreadPool extends Thread {
    private static final int DEFAULT_MIN_SIZE = 5;
    private static final int DEFAULT_ACTIVE_SIZE = 10;
    private static final int DEFAULT_MAX_SIZE = 15;
    private static final int DEFAULT_QUEUE_SIZE = 60;
    private static final String THREAD_PREFIX = "SIMPLE-THREAD-POOL-";
    private static final String THREAD_GROUP_NAME = "SIMPLE_THREAD_POOL";
    private static final DiscardPolicy DEFAULT_DISCARD_POLICY = () -> {
        throw new DiscardTaskException("task queue is full,can't submit new task.");
    };
    private int min;
    private int active;
    private int max;
    private int taskQueueSize;
    private LinkedList<Runnable> taskQueue;
    private List<WorkerTask> workerTasks;
    private ThreadGroup threadGroup;
    private DiscardPolicy discardPolicy;
    private volatile boolean destroyed;

    public SimpleThreadPool(int min, int active, int max, int taskQueueSize, DiscardPolicy discardPolicy) {
        this.min = min;
        this.active = active;
        this.max = max;
        this.taskQueueSize = taskQueueSize;
        this.discardPolicy = discardPolicy;
        taskQueue = new LinkedList<>();
        threadGroup = new ThreadGroup(THREAD_GROUP_NAME);
        workerTasks = new ArrayList<>();
        this.destroyed = false;
        init();
    }

    public SimpleThreadPool(int min, int active, int max, int taskQueueSize) {
        this(min, active, max, taskQueueSize, DEFAULT_DISCARD_POLICY);
    }

    public SimpleThreadPool() {
        this(DEFAULT_MIN_SIZE, DEFAULT_ACTIVE_SIZE, DEFAULT_MAX_SIZE, DEFAULT_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
    }

    private void init() {
        for (int i = 0; i < min; i++) {
            createWorkTask(THREAD_PREFIX + i);
        }
    }

    private void createWorkTask(String name) {
        WorkerTask workerTask = new WorkerTask(threadGroup, name);
        workerTask.start();
        workerTasks.add(workerTask);
    }

    public void submit(Runnable runnable) {
        if (destroyed) {
            System.out.println("thread pool is shutdown,can't submit task");
            return;
        }
        synchronized (taskQueue) {
            if (taskQueue.size() >= taskQueueSize) {
                discardPolicy.discardTask();
            }
            taskQueue.offer(runnable);
            taskQueue.notifyAll();
        }

    }

    public void shutdown() throws InterruptedException {
        while (!taskQueue.isEmpty()) {
            Thread.sleep(1_000);
        }
        int taskSize = workerTasks.size();
        while (taskSize > 0) {
            for (WorkerTask worker : workerTasks) {
                if (ThreadState.BLOCKED.equals(worker.threadState)) {
                    worker.interrupt();
                    worker.close();
                    taskSize--;
                } else {
                    Thread.sleep(10);
                }
            }
        }
        this.destroyed = true;
        System.out.println("thread pool shutdown.. ");
    }

    @Override
    public void run() {
        while (!destroyed) {
            System.out.println("当前线程大小:" + workerTasks.size());
            int currentSize = workerTasks.size();
            if (currentSize < active && taskQueue.size() > active) {
                for (int i = currentSize; i < active; i++) {
                    createWorkTask(THREAD_PREFIX + i);
                }
                System.out.println("线程扩容,扩容后currentSize=" + workerTasks.size());
            } else if (currentSize < max && taskQueue.size() > max) {
                for (int i = currentSize; i < max; i++) {
                    createWorkTask(THREAD_PREFIX + i);
                }
                System.out.println("线程扩容,currentSize=" + workerTasks.size());
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
            out:
            while (!threadState.equals(ThreadState.DEAD)) {
                Runnable runnable;
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
                if (runnable != null) {
                    this.threadState = ThreadState.RUNNING;
                    runnable.run();
                    this.threadState = ThreadState.FREE;
                }
            }
        }

        public void close() {
            this.threadState = ThreadState.DEAD;
        }
    }

}
