package org.ethan.demo.zookeeper.d02;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CuratorEventTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CuratorEventTest.class);

    public static void main(String[] args) throws Exception {
        CuratorEventClient client = new CuratorEventClient("192.168.1.210:2181");
        client.addListenerOne("/test", "test data".getBytes());

        client.addListenerTwoOne("/test1", "test1".getBytes());
        client.addListenerTwoTwo("/test2", "test2".getBytes());
        client.addListenerTwoThree("/test3", "test3".getBytes());
    }

}

class CuratorEventClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CuratorEventClient.class);

    private static final RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 3);

    private CuratorFramework curatorFramework;

    public CuratorEventClient(String addr) {
        curatorFramework = CuratorFrameworkFactory.newClient(addr, retryPolicy);
        curatorFramework.start();
    }

    /**
     * 第一种监听器的添加方式:对指定的节点进行添加操作
     * 仅仅能监控的本节点的数据修改,删除操作并且只能监听一次
     * @param path 路径
     * @param data 数据
     * @throws Exception
     */
    public void addListenerOne(String path, byte[] data) throws Exception {
        curatorFramework.create().orSetData().withMode(CreateMode.PERSISTENT).forPath(path, data);
        byte[] resultData = curatorFramework.getData()
                .usingWatcher((Watcher) event -> LOGGER.info("获取数据事件:{}", event)).forPath(path);
        curatorFramework.create().orSetData().withMode(CreateMode.PERSISTENT).forPath(path, data);
        Thread.sleep(1000);
        curatorFramework.create().orSetData().withMode(CreateMode.PERSISTENT).forPath(path, data);
        Thread.sleep(1000);
        LOGGER.info("节点数据:{}", new String(resultData));
    }

    /**
     * 第二种监听的添加方式: Cache的三种实现
     * Path Cache: 监视一个路径下的孩子节点的创建,删除及修改;产生的事件会传给注册的PathChildrenCacheListener
     * Node Cache: 监视一个结点的创建,更新,并将节点缓存在本地
     * Tree Cache: PatchCache和NodeCache的合体,监视路径下的创建,更新,删除事件,并缓存路径下所有孩子结点的数据.
     *
     * 本方法是实现Path Cache模式
     * 能监听所有的字节点且是无限监听的模式,但是指定目录下节点的子节点不再监听
     * @param path 路径
     * @param data 数据
     * @throws Exception
     */
    public void addListenerTwoOne(String path, byte[] data) throws Exception {
        PathChildrenCache childrenCache = new PathChildrenCache(curatorFramework, path, true);
        PathChildrenCacheListener childrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                LOGGER.info("开始进行事务分析");
                ChildData data = event.getData();
                LOGGER.info("节点类型={},path={},data={}", event.getType(), data.getPath(), data.getData());
            }
        };
        childrenCache.getListenable().addListener(childrenCacheListener);
        LOGGER.info("流油zk监听器成功");
        childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);

        // 创建一个节点
        curatorFramework.create().orSetData().withMode(CreateMode.PERSISTENT).forPath(path, data);

        // 创建子节点
        curatorFramework.create().orSetData().withMode(CreateMode.PERSISTENT).forPath(path + "/node1", "node1".getBytes());
        Thread.sleep(1000);
        curatorFramework.create().orSetData().withMode(CreateMode.PERSISTENT).forPath(path + "/node2", "node2".getBytes());
        Thread.sleep(1000);
        curatorFramework.create().orSetData().withMode(CreateMode.PERSISTENT).forPath(path + "/node2", "node3".getBytes());
        Thread.sleep(1000);
        curatorFramework.delete().forPath(path + "/node2");
        Thread.sleep(1000);
    }

    /**
     * Node Cache: 监控本节点的变化情况,连接目录是否压缩
     * 监听本节点变化 节点可以进行修改 删除节点后会再次创建(空节点)
     * @param path 路径
     * @param data 数据
     * @throws Exception
     */
    public void addListenerTwoTwo(String path, byte[] data) throws Exception {
        final NodeCache nodeCache = new NodeCache(curatorFramework, path, false);
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                ChildData currentData = nodeCache.getCurrentData();
                LOGGER.info("节点类型={},path={},data={}, stat={}", currentData.getPath(), currentData.getData(), currentData.getStat());
            }
        });
        nodeCache.start();

        curatorFramework.create().orSetData().withMode(CreateMode.PERSISTENT).forPath(path, data);
        Thread.sleep(1000);
        curatorFramework.create().orSetData().withMode(CreateMode.PERSISTENT).forPath(path, "enjoy".getBytes());
        Thread.sleep(1000);
    }

    /**
     * Three Cache
     * 监控指定节点和节点下的所有的节点变化,无限监听, 可以进行本节点的删除(不在创建)
     * @param path
     * @param data
     * @throws Exception
     */
    public void addListenerTwoThree(String path, byte[] data) throws Exception {
        TreeCache treeCache = new TreeCache(curatorFramework, path);
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                if (event != null) {
                    ChildData data = event.getData();
                    LOGGER.info("节点类型={},path={},data={}", event.getType(), data.getPath(), data.getData());
                }
            }
        });
        treeCache.start();

        curatorFramework.create().orSetData().withMode(CreateMode.PERSISTENT).forPath(path, data);
        Thread.sleep(1000);
        curatorFramework.create().orSetData().withMode(CreateMode.PERSISTENT).forPath(path + "/node001", "node001".getBytes());
        Thread.sleep(1000);
        curatorFramework.create().orSetData().withMode(CreateMode.PERSISTENT).forPath(path + "/node001", "node01".getBytes());
        Thread.sleep(1000);
        curatorFramework.create().orSetData().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path + "/node002/node002_1", "node002_1".getBytes());
        Thread.sleep(1000);
    }
}
