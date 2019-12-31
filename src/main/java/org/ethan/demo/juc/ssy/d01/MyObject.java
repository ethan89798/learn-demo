package org.ethan.demo.juc.ssy.d01;

public class MyObject {
    private int counter;

    public synchronized void increase() {
        /*
        使用if很容易产生虚假唤醒，就会产生数据错误，建议使用while
        如果使用nofity()唤醒，就会出现唤醒的那一个线程刚好是本来操作的线程，就会进入无限等待。
         */
//        if (counter != 0) {
        while (counter != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        counter++;
        System.out.print(counter);
        notifyAll();
    }

    public synchronized void decrease() {
//        if (counter == 0) {
        while (counter == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        counter--;
        System.out.print(counter);
        notifyAll();
    }
}
