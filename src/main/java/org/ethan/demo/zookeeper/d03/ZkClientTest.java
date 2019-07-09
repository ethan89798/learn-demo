package org.ethan.demo.zookeeper.d03;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ZkClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZkClientTest.class);

    public static void main(String[] args) throws InterruptedException {
        ZkClient client = new ZkClient("192.168.1.210:2181", 10000);

        // create and delete方法
        client.createEphemeral("/zkclient");
        client.createPersistent("/zkclient/c1", true);
        Thread.sleep(1000);
        client.delete("/temp");
        client.deleteRecursive("/super/tmp");

        // 设置path和data 并且读取子节点和每个节点的内容
        client.createPersistent("/super", "1234");
        client.createPersistent("/super/c1", "c1内容");
        client.createPersistent("/super/c2", "c2内容");
        List<String> children = client.getChildren("/super");
        for (String child : children) {
            String path = "/super/" + child;
            LOGGER.info("path={}, data={}", path, client.readData(path));
        }

        // 更新和判断节点是否存在
        client.writeData("/super", "新内容");
        LOGGER.info(client.readData("/super/c2"));
        LOGGER.info("是否存在={}", client.exists("/super/c1"));

        // 递归删除/super内容
        client.deleteRecursive("/super");
    }
}
