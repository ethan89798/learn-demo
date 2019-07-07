package org.ethan.demo.zookeeper.d01;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * zookeeper我们在创建ZooKeeper对象时马上使用可能会出现问题,因为ZooKeeper可能正在连接的状态
 * 所以我们在使用时应该使用CountDownLatch类来进行await(),待ZooKeeper的Watcher返回后我们再进行countDown(),然后再返回使用ZooKeeper对象
 */
public class ZookeeperSessionTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperSessionTest.class);
    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        testSession1("192.168.1.210:2181", 3000);
        testSession2("192.168.1.210:2181", 3000);
    }

    private static void testSession1(String addr, int timeout) throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper(addr, timeout, null);
        LOGGER.info("zookeeper对象信息={}", zooKeeper);
        LOGGER.info("zookeeper对象状态={}", zooKeeper.getState());
    }

    private static void testSession2(String addr, int timeout) throws InterruptedException, IOException {
        ZooKeeper zooKeeper = new ZooKeeper(addr, timeout, event -> {
            LOGGER.info("event={}", event);
            if (Watcher.Event.KeeperState.SyncConnected == event.getState()) {
                COUNT_DOWN_LATCH.countDown();
                LOGGER.info("已经成功获取到连接");
            }
        });
        COUNT_DOWN_LATCH.await();
        LOGGER.info("zookeeper对象信息={}", zooKeeper);
        LOGGER.info("zookeeper对象状态={}", zooKeeper.getState());
    }

}
