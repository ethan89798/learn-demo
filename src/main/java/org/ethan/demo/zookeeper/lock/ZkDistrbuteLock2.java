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

    /*
    当前请求的节点前一个节点
     */
    private String lastNodePath;
    /*
    当前请求的节点
     */
    private String currentNodePath;

    public ZkDistrbuteLock2() {
        if (!client.exists(LOCK_PATH)) {
            client.createPersistent(LOCK_PATH);
        }
    }

    @Override
    public boolean tryLock() {
        // 如果当前节点为空则为第一次尝试加锁, 第一次加锁赋值currentNodePath
        if (StringUtils.isEmpty(currentNodePath)) {
            // 创建一个临时有序的节点
            currentNodePath = client.createEphemeralSequential(LOCK_PATH + "/", "lock");
        }

        // 获取所有的临时节点并排序,临时节点名称为自增长的字符串
        List<String> children = client.getChildren(LOCK_PATH);
        Collections.sort(children);
        // 如果当前节点在所有节点中排名第一,则获锁成功
        if (currentNodePath.endsWith(LOCK_PATH + "/" + children.get(0))) {
            return true;
        } else {
            // 如果当前节点在所有节点中排名不是排名第一,则获取前面的节点名称,并赋值给lastNodePath
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
        // 给排在前面的节点增加监听,本质是启动另一个线程去监听前置节点
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
