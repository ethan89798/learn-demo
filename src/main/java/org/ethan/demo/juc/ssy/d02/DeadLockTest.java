package org.ethan.demo.juc.ssy.d02;

/**
 * 检测死锁可以使用jstack pid来查看应用内发生的死死
 * 当然我们也可以使用jvisualvm或jmc来查看检测，
 * 但是linux环境一般都是没有图形用户界面，所以使用jstack pid的方式比较多
 */
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
