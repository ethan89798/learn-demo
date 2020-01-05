package org.ethan.demo.juc.ssy.d02;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class MyCustomQueue {

    private Lock lock = new ReentrantLock();
    private Condition full = lock.newCondition();
    private Condition empty = lock.newCondition();

    /**
     * 数组保存的索引
     */
    private int preIndex = 0;
    private int lastIndex = 0;
    private int count;
    /**
     * 保存数据的数组
     */
    private Object[] array = new Object[10];

    public void put(Object obj) {
        try {
            lock.lock();
            while (count == array.length) {
                full.wait();
            }
            array[preIndex] = obj;
            if (++preIndex >= array.length) preIndex = 0;
            ++count;
            System.out.println("put list=" + Arrays.toString(array));
            empty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Object take() {
        Object old = null;
        try {
            lock.lock();

            while (count == 0) {
                empty.wait();
            }

            old = array[lastIndex];
            array[lastIndex] = null;
            if (++lastIndex >= array.length) lastIndex = 0;
            --count;

            System.out.println("tack list=" + Arrays.toString(array));

            full.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return old;
    }

    public static void main(String[] args) {
        final MyCustomQueue queue = new MyCustomQueue();

        IntStream.range(0, 10).forEach(i -> new Thread(()->{
            queue.put("hello");
        }).start());

        IntStream.range(0, 10).forEach(i -> new Thread(()->{
            queue.take();
        }).start());
    }
}
