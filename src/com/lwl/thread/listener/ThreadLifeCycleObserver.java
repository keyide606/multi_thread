package com.lwl.thread.listener;

import java.util.List;

/**
 * @author liwenlong
 * @date 2021-05-01 18:16
 */
public class ThreadLifeCycleObserver implements LifeCycleListener {

    private final Object object = new Object();


    public void concurrentQuery(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        ids.stream().forEach(id -> new Thread(new ObservableRunnable(this) {
            @Override
            public void run() {
                notifyChange(new RunnableEvent(RunnableState.RUNNING, Thread.currentThread(), null));
                System.out.println("query for the id " + id);
                try {
                    Thread.sleep(100);
                    int j = 1 / 0;
                    notifyChange(new RunnableEvent(RunnableState.DONE, Thread.currentThread(), null));
                } catch (Exception e) {
                    notifyChange(new RunnableEvent(RunnableState.ERROR, Thread.currentThread(), e));
                }
            }
        }).start());
    }

    @Override
    public void onEvent(ObservableRunnable.RunnableEvent event) {
        synchronized (object) {
            System.out.println("event thead is" + event.getThread().getName() + ",and state is " + event.getRunnableState());
            if (event.getThrowable() != null) {
                System.out.println("错误如下");
                event.getThrowable().printStackTrace();
            }
        }
    }


}
