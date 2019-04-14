package org.ethan.demo.jvm.ssy.d05;

import java.util.Random;

/**
 * 死锁程序, 使用jconsole或jvisualvm进行死锁检测.
 *
 */
public class CustomLockTest {
    public static void main(String[] args) {
        new Thread(new ThreadA(), "thread-a").start();
        new Thread(new ThreadB(), "thread-b").start();
    }
}

class ThreadA implements Runnable {
    @Override
    public void run() {
        Random random = new Random();
        synchronized (ThreadA.class) {
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (ThreadB.class) {
                System.out.println("ThreadA");
            }
        }
    }
}

class ThreadB implements Runnable {

    @Override
    public void run() {
        Random random = new Random();
        synchronized (ThreadB.class) {
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (ThreadA.class) {
                System.out.println("ThreadB");
            }
        }
    }
}
