package org.ethan.demo.zookeeper.d02;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * apache curator框架基本使用
 */
public class CuratorClientTest {

    public static void main(String[] args) throws Exception {
        CuratorClient client = new CuratorClient();
        client.init("192.168.1.210:2181", 3000);

        client.testCreate();
        client.testExists();
        client.testUpdateDataAsync();
        client.testUpdateDataAsyncCallback();
        client.testDelete();
        client.testTransaction();
    }
}

class CuratorClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CuratorClient.class);
    /**
     * 重试策略
     *    ExponentialBackoffRetry  -->   重试一定次数, 通过增加重试之间的休眠时间达到指定定重试次数
     *    BoundedExponentialBackoffRetry  --> 重试一定次数, 通过增加重试之间的休眠时间直到最大上限重试次数
     *    RetryUntilElapsed  -->  一直重试, 直到超过指定时间
     *    RetryNTimes   --> 重试N次
     *    RetryOneTime  --> 只重度一次
     *
     * baseSleepTimeMs --> 初始的重试等待时间
     * maxRetries --> 最多重试次数
     *
     *
     */
    private static final RetryPolicy RETRY_POLICY = new ExponentialBackoffRetry(1000, 3);

    private CuratorFramework curatorClient;

    /**
     * 创建连接对象
     * @param addr 连接地址
     * @param timeout 超时时间
     */
    public void init(String addr, int timeout) {
        curatorClient = CuratorFrameworkFactory.newClient(addr, timeout, timeout, RETRY_POLICY);
        curatorClient.start();
    }

    /**
     * 使用curator创建各种节点类型
     * @throws Exception
     */
    public void testCreate() throws Exception {
        //创建永久节点
        String s = curatorClient.create().forPath("/curator", "curator_persistent".getBytes());
        String s4 = curatorClient.create().forPath("/curator3", "curator_persistent3".getBytes());
        //创建永久有序节点
        String s1 = curatorClient.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                .forPath("/curator_sequential", "curator_sequential".getBytes());
        //创建临时节点
        String s2 = curatorClient.create().withMode(CreateMode.EPHEMERAL)
                .forPath("/curator_ephemeral", "curator_ephemeral".getBytes());
        //创建临时有序节点
        String s3 = curatorClient.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath("/curator_ephemeral_sequential", "curator_ephemeral_sequential".getBytes());
        LOGGER.info("s={},s1={},s2={},s3={}", s, s1, s2, s3);
    }

    /**
     * 使用curator判断节点是否存在
     * @throws Exception
     */
    public void testExists() throws Exception {
        Stat stat = curatorClient.checkExists().forPath("/curator");
        Stat stat1 = curatorClient.checkExists().forPath("/curator2");
        LOGGER.info("/curator是否存在={}", stat == null ? false : true);
        LOGGER.info("/curator2是否存在={}", stat1 == null ? false : true);
    }

    /**
     * 更新节点数据,使用异步方式
     * @throws Exception
     */
    public void testUpdateDataAsync() throws Exception {
        CuratorListener listener = new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
                LOGGER.info("event={}", event);
                LOGGER.info(event.getPath());
            }
        };
        curatorClient.getCuratorListenable().addListener(listener);
        curatorClient.setData().inBackground().forPath("/curator", "curator_sync".getBytes());
    }

    /**
     * 更新节点数据,使用异步回调的方式
     * @throws Exception
     */
    public void testUpdateDataAsyncCallback() throws Exception {
        BackgroundCallback callback = new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                LOGGER.info("event={}", event);
                LOGGER.info(event.getPath());
            }
        };
        Stat stat = curatorClient.setData().inBackground(callback).forPath("/curator3", "curator_callback".getBytes());
        LOGGER.info("stat={}", stat);
    }

    /**
     * 删除节点接口
     * @throws Exception
     */
    public void testDelete() throws Exception {
        // 创建测试节点
        curatorClient.create().orSetData().creatingParentsIfNeeded()
                .forPath("/curator/del_key1", "del_key1".getBytes());
        curatorClient.create().orSetData().creatingParentsIfNeeded()
                .forPath("/curator/del_key2", "key2".getBytes());
        curatorClient.create().forPath("/curator/del_key2/test_key", "test_key".getBytes());

        //删除节点
        curatorClient.delete().forPath("/curator/del_key1");
        //级联删除节点
        curatorClient.delete().guaranteed().deletingChildrenIfNeeded().forPath("/curator/del_key2");
    }

    /**
     * 使用事务管理, 遇到异常,事务会回滚
     * @throws Exception
     */
    public void testTransaction() throws Exception {
        CuratorOp createOp = curatorClient.transactionOp().create()
                .forPath("/curator/one_path", "one_path".getBytes());

        CuratorOp updateOp = curatorClient.transactionOp().setData()
                .forPath("/curator/one_path", "update_path".getBytes());

        CuratorOp deleteOp = curatorClient.transactionOp().delete()
                .forPath("/curator/one_path");

        List<CuratorTransactionResult> resultList = curatorClient.transaction()
                .forOperations(createOp, updateOp, deleteOp);
        for (CuratorTransactionResult result : resultList) {
            LOGGER.info("执行结果是: {}, -- {}", result.getForPath(), result.getType());
        }
    }
}


