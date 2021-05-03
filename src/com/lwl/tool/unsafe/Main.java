package com.lwl.tool.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.stream.IntStream;

/**
 * @author liwenlong
 * @date 2021-05-03 13:28
 */
public class Main {
    /*
    了解一下一半天使,一般魔鬼的unsafe
        使用unsafe自己写一个cas
     */
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        CASCounter casCounter;
        Unsafe unsafe = getUnsafe();
        casCounter = new CASCounter(0, unsafe);
        IntStream.rangeClosed(1, 10).forEach(i -> new Thread(() -> {
            for (int j = 0; j < 20; j++) {
                casCounter.increment();
            }
        }).start());
        Thread.sleep(10_000);
        System.out.println(casCounter.counter);
    }


    private static class CASCounter {
        private volatile long counter;

        private Unsafe unsafe;

        private long offset;

        public CASCounter(long counter, Unsafe unsafe) throws NoSuchFieldException {
            this.counter = counter;
            this.unsafe = unsafe;
            this.offset = unsafe.objectFieldOffset(CASCounter.class.getDeclaredField("counter"));
        }

        public void increment() {
            for (; ; ) {
                long prev = counter;
                long next = prev + 1;
                if (unsafe.compareAndSwapLong(this, offset, prev, next)) {
                    break;
                }
            }
        }


    }
    
    private static Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return (Unsafe) f.get(null);
    }
}
