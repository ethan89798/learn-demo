package org.ethan.demo.juc.ssy.do2;

public class DeadLockTest {

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public void lockMethod1() {
        synchronized (lock1) {
            synchronized (lock2) {
                System.out.println("lock method1 invoked");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public void lockMethod2() {
        synchronized (lock2) {
            synchronized (lock1) {
                System.out.println("lock method2 invoked");
                try {
                    Thread.sleep(220);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public static void main(String[] args) {
        DeadLockTest deadLock = new DeadLockTest();
        Thread thread1 = new Thread(() -> {
            while (true) {
                deadLock.lockMethod1();
            }
        }, "DeadLock1");
        Thread thread2 = new Thread(() -> {
            while (true) {
                deadLock.lockMethod2();
            }
        }, "DeadLock1");
        thread1.start();
        thread2.start();
    }

}
