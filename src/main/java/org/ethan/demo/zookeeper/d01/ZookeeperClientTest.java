package org.ethan.demo.zookeeper.d01;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZookeeperClientTest {
    private static Logger logger = LoggerFactory.getLogger(ZookeeperClientTest.class);

    /**
     * 连接zookeeper
     */
    private static ZooKeeper getZookeeperClient(String host) throws IOException {
        ZooKeeper zk = new ZooKeeper("192.168.0.186", 5000, (we) -> {
            logger.info("WatchedEvent = {}" + we);
        });
        return zk;
    }

    /**
     * 创建路径和设置值
     * 针对Zookeeper提供的是否存在(exist),创建(create),更新值(setData)
     */
    private static void setPathValue(ZooKeeper zk, String path, byte[] data) throws KeeperException, InterruptedException {
        //判断路径是否存在
        Stat stat = zk.exists(path, false);
        if (null == stat) {
            /*
             * 注意这里的create方法不能创建多层目录, 否出会报错.
             */
            //创建路径,并设置值
            zk.create(path, "My Test Data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } else {
            logger.info("stat = {}", stat);
            zk.setData(path, data, stat.getVersion());
        }
    }

    private static void getPathData(ZooKeeper zk, String path) throws Exception {
        Stat stat = zk.exists(path, false);
        if (stat != null) {
            byte[] data = zk.getData(path, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    logger.info("WatchedEvent={}", event);
                    if (event.getType() == Event.EventType.None) {
                        if (event.getState() == Event.KeeperState.Expired) {

                        }
                    } else {
                        try {
                            byte[] bn = zk.getData(path, false, null);
                            String data = new String(bn, "UTF-8");
                            logger.info("second data={}", data);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, null);

            logger.info("data={}", new String(data, "UTF-8"));
        } else {
            logger.warn("zookeeper not exist node");
        }
    }

    /**
     * 获取zookeeper下面的所有子节点(getChildren)
     */
    private static void getChildren(ZooKeeper zk, String path) throws Exception {
        Stat stat = zk.exists(path, false);
        if (stat != null) {
            List<String> childs = zk.getChildren(path, false);
            childs.forEach(p -> logger.info(p));
        } else {
            logger.warn("zookeeper not exist node");
        }
    }

    /**
     * 删除zookeeper上面的节点,当然如果目录下还有子目录,不能删除.必须是一个空目录才能使用
     */
    private static void delete(ZooKeeper zk, String path) throws Exception {
        Stat stat = zk.exists(path, false);
        if (null != stat) {
            zk.delete(path, stat.getVersion());
        } else {
            logger.warn("zookeeper not exist node");
        }
    }

    public static void main(String[] args) throws Exception {

        String path = "/myFirstPath";
        ZooKeeper zk = getZookeeperClient("192.168.0.186");
//        setPathValue(zk, path, "TEST TEST".getBytes());
//        getPathData(zk, path);
        getChildren(zk, path);

        delete(zk, path);

//        final CountDownLatch latch = new CountDownLatch(1);
//        ZooKeeper zk = new ZooKeeper("192.168.0.186", 5000, (we) -> {
//            logger.info("WatchedEvent = {}" + we);
//            if (we.getState() == Watcher.Event.KeeperState.SyncConnected) {
//                latch.countDown();
//            }
//        });
//        latch.await();
//
//        String path = "/myFirstPath";
//        //判断路径是否存在
//        Stat stat = zk.exists(path, false);
//        if (null == stat) {
//            /*
//             * 注意这里的create方法不能创建多层目录, 否出会报错.
//             */
//            //创建路径,并设置值
//            zk.create(path, "My Test Data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        } else {
//            logger.info("stat = {}", stat);
//        }
//
//        zk.close();
    }
}
