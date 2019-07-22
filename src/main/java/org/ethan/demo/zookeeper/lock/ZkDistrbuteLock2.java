package org.ethan.demo.zookeeper.lock;

import edu.emory.mathcs.backport.java.util.Collections;
import org.I0Itec.zkclient.IZkDataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZkDistrbuteLock2 extends AbstractLock {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZkDistrbuteLock2.class);
    private CountDownLatch latch;

    private String lastNodePath;
    private String currentNodePath;

    public ZkDistrbuteLock2() {
        if (!client.exists(LOCK_PATH)) {
            client.createPersistent(LOCK_PATH);
        }
    }

    @Override
    public boolean tryLock() {
        if (StringUtils.isEmpty(currentNodePath)) {
            currentNodePath = client.createEphemeralSequential(LOCK_PATH + "/", "lock");
        }

        List<String> children = client.getChildren(LOCK_PATH);
        Collections.sort(children);
        if (currentNodePath.endsWith(LOCK_PATH + "/" + children.get(0))) {
            return true;
        } else {
            int wz = Collections.binarySearch(children, currentNodePath.substring(7));
            lastNodePath = LOCK_PATH + '/' + children.get(wz - 1);
        }
        return false;
    }

    @Override
    public void waitLock() {
        IZkDataListener listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {

            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                if (latch != null) {
                    latch.countDown();
                }
            }
        };
        client.subscribeDataChanges(lastNodePath, listener);
        if (client.exists(lastNodePath)) {
            latch = new CountDownLatch(1);
            try {
                latch.await();
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        client.unsubscribeDataChanges(lastNodePath, listener);
    }

    @Override
    public void unLock() {
        if (client != null) {
            client.delete(currentNodePath);
        }
    }
}
