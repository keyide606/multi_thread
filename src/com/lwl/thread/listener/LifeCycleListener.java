package com.lwl.thread.listener;

/**
 * @author liwenlong
 * @date 2021-05-01 18:10
 */
public interface LifeCycleListener {
    void onEvent(ObservableRunnable.RunnableEvent event);
}
