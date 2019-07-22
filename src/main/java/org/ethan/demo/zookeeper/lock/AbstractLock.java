package org.ethan.demo.zookeeper.lock;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractLock implements Lock {

    private static final Logger logger = LoggerFactory.getLogger(AbstractLock.class);

    protected ZkClient client = new ZkClient("192.168.1.210:2181");
    protected final String LOCK_PATH = "/lock";

    @Override
    public void lock() {
        if (tryLock()) {
            logger.info("{}-{}-thread get lock", Thread.currentThread().getId(), Thread.currentThread().getName());
        } else {
            /*
             * 等待锁资源
             */
            waitLock();
            /*
             * 重新获得锁
             */
            lock();
        }

    }

    public abstract boolean tryLock();

    public abstract void waitLock();

    @Override
    public void unLock() {
        if (client != null) {
            client.delete(LOCK_PATH);
//            client.close();
            logger.info("{}-{}-Thread lock release", Thread.currentThread().getId(), Thread.currentThread().getName());
        }
    }
}
