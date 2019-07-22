package org.ethan.demo.zookeeper.lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class ZkDistrbuteLock extends AbstractLock {

    private static final Logger logger = LoggerFactory.getLogger(ZkDistrbuteLock.class);
    private CountDownLatch latch;

    @Override
    public boolean tryLock() {
        if (!client.exists(LOCK_PATH)) {
            try {
                client.createEphemeral(LOCK_PATH);
                return true;
            } catch (Exception e) {
//                logger.error(e.getMessage(), e);
            }
        }
        return false;
    }

    @Override
    public void waitLock() {
        // 编写监听器,用于监听节点变化信息,方便后续进行获取锁
        IZkDataListener listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
            }
            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                if (latch != null) {
                    // 将等待的唤醒
                    latch.countDown();
                }
            }
        };
        client.subscribeDataChanges(LOCK_PATH, listener);
        // 如果节点存在,就等待节点被删除的通知
        if (client.exists(LOCK_PATH)) {
            latch = new CountDownLatch(1);
            try {
                latch.await();
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
        client.unsubscribeDataChanges(LOCK_PATH, listener);
    }
}
