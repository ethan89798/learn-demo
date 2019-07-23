package org.ethan.demo.zookeeper.lock;

public class LockTestMain {
    public static void main(String[] args) throws InterruptedException {
        ZkDistrbuteLock2 lock = new ZkDistrbuteLock2();
//        ZkDistrbuteLock lock = new ZkDistrbuteLock();
        final NumberGenerator generator = new NumberGenerator();
        for (int i = 0; i < 500; i++) {
            new Thread(()->{
                lock.lock();
                try {
                    generator.autoIncrement();
                    System.out.println(".........." + NumberGenerator.number);
                } finally {
                    lock.unLock();
                }
            }).start();
        }
        Thread.sleep(2000);
    }
}

class NumberGenerator {
    static Integer number = new Integer(0);

    public void autoIncrement() {
        ++number;
    }
}
