package org.ethan.demo.zookeeper.d03;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ZkClientWatcherTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZkClientWatcherTest.class);
    public static void main(String[] args) throws InterruptedException {
        testClientWatcherNode();

        testClientWatcherData();

        Thread.sleep(10000);
    }

    private static void testClientWatcherNode() throws InterruptedException {
        ZkClient client = new ZkClient(new ZkConnection("192.168.1.210:2181"), 1000);

        // 对父节点添加监听子节点变化
        client.subscribeChildChanges("/super-watcher", new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                LOGGER.info("path={}, childs={}", parentPath, currentChilds);
            }
        });

        Thread.sleep(2000);
        if (!client.exists("/super-watcher")) {
            client.createPersistent("/super-watcher");
        }
        Thread.sleep(1000);
        if (!client.exists("/super-watcher/c1")) {
            client.createPersistent("/super-watcher/c1", "c1内容");
        }
        Thread.sleep(1000);
        if (!client.exists("/super-watcher/c2")) {
            client.createPersistent("/super-watcher/c2", "c2内容");
        }
        Thread.sleep(1000);
//        client.delete("/super-watcher");
        Thread.sleep(1000);
        client.deleteRecursive("/super-watcher");
    }

    private static void testClientWatcherData() throws InterruptedException {
        ZkClient client = new ZkClient(new ZkConnection("192.168.1.210:2181"), 3000);
        if (!client.exists("/super-watcher2")) {
            client.createEphemeral("/super-watcher2", "test watcher2");
        }
        client.subscribeDataChanges("/super-watcher2", new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                LOGGER.info("变更新节点:parentPath={}, currentChild={}", dataPath, data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                LOGGER.info("删除节点Path={}", dataPath);
            }
        });

        Thread.sleep(1000);
        client.writeData("/super-watcher2", "test data".getBytes(), -1);
        Thread.sleep(1000);
        client.delete("/super-watcher2");
        Thread.sleep(1000);
    }
}
