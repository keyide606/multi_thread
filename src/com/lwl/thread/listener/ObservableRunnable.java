package com.lwl.thread.listener;


/**
 * @author liwenlong
 * @date 2021-05-01 18:10
 */
public abstract class ObservableRunnable implements Runnable {
    protected final LifeCycleListener listener;

    public ObservableRunnable(final LifeCycleListener listener) {
        this.listener = listener;
    }

    protected void notifyChange(final RunnableEvent runnableEvent) {
        listener.onEvent(runnableEvent);
    }

    protected enum RunnableState {
        RUNNING, DONE, ERROR;
    }

    public static class RunnableEvent {
        private final RunnableState runnableState;
        private final Thread thread;
        private final Throwable throwable;

        public RunnableEvent(RunnableState runnableState, Thread thread, Throwable throwable) {
            this.runnableState = runnableState;
            this.thread = thread;
            this.throwable = throwable;
        }

        public RunnableState getRunnableState() {
            return runnableState;
        }

        public Thread getThread() {
            return thread;
        }

        public Throwable getThrowable() {
            return throwable;
        }
    }
}
