package org.ethan.demo.zookeeper.lock;

public interface Lock {
    /**
     * 开启锁
     */
    void lock();

    /**
     * 释放锁
     */
    void unLock();
}
