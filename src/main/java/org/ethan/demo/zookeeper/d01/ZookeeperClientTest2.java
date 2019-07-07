package org.ethan.demo.zookeeper.d01;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ZookeeperClientTest2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperClientTest2.class);


    public static void main(String[] args) {
        String path = "/ethan";
        byte[] data = "测试数据".getBytes();
        ZookeeperClient client = new ZookeeperClient();
        client.getConnection("192.168.1.210:2181", 10000);
//        LOGGER.info("创建/ethan节点开始");
//        client.createPath(path, data);
//        LOGGER.info("创建/ethan节点结束");
//        LOGGER.info("更新/ethan节点开始");
//        client.setData(path, data);
//        LOGGER.info("更新/ethan节点结束");
//        LOGGER.info("获取数据开始");
//        byte[] d = client.readData(path, true);
//        LOGGER.info("获取数据结束, " + new String(d));
//        LOGGER.info("获取子节点开始");
//        List<String> children = client.getChildren(path, true);
//        LOGGER.info("获取子节点结束, " + children);
        LOGGER.info("删除节点开始");
        boolean r = client.deleteAllPath(path);
        LOGGER.info("删除节点结束, " + r);
        client.releaseConnection();
    }
}

class ZookeeperClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperClient.class);
    private ZooKeeper zooKeeper;

    /**
     * 创建zk连接
     * @param addr 服务器地址
     * @param timeout 超里时间
     * @return
     */
    public ZooKeeper getConnection(String addr, int timeout) {
        if (zooKeeper == null) {
            try {
                zooKeeper = new ZooKeeper(addr, timeout, new ZookeeperWatcher());
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return zooKeeper;
    }

    /**
     * 关闭zk连接
     */
    public void releaseConnection() {
        if (this.zooKeeper != null) {
            try {
                this.zooKeeper.close();
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 创建节点
     * @param path 节点路径
     * @param data 数据内容
     * @return
     */
    public boolean createPath(String path, byte[] data) {
        try {
            // 设置监控(由于zookeeper的监控都是一次性,所以每次都要设置监控)
            Stat stat = this.zooKeeper.exists(path, true);
            if (stat != null) {
                LOGGER.warn("路径节点已经存在,不需要重复创建");
            } else {
                //参数,create(路径,数据,所有可见,永久存储)
                this.zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            return true;
        } catch (KeeperException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 读取指定节点数据内容
     * @param path 节点路径
     * @param needWatch 是否需要监听
     * @return
     */
    public byte[] readData(String path, boolean needWatch) {
        try {
            return this.zooKeeper.getData(path, needWatch, null);
        } catch (KeeperException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return new byte[0];
    }

    /**
     * 更新节点的值
     * @param path 路径
     * @param data 数据内容
     * @return
     */
    public boolean setData(String path, byte[] data) {
        try {
            this.zooKeeper.setData(path, data, -1);
            return true;
        } catch (KeeperException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 删除指定节点
     * @param path 节点路径
     * @return
     */
    public boolean deletePath(String path) {
        try {
            this.zooKeeper.delete(path, -1);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (KeeperException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 判断指定节点是否存在
     * @param path 节点路径
     * @param needWatch 是否需要监控
     * @return
     */
    public boolean existsPath(String path, boolean needWatch) {
        try {
            Stat stat = this.zooKeeper.exists(path, needWatch);
            if (stat != null) {
                return true;
            }
        } catch (KeeperException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 获取所有子节点路径
     * @param path 路径
     * @param needWatch 是否需要监控
     * @return
     */
    public List<String> getChildren(String path, boolean needWatch) {
        try {
            return this.zooKeeper.getChildren(path, needWatch);
        } catch (KeeperException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    /**
     * 删除路径下的所有子节点
     * @param path 路径
     * @return
     */
    public boolean deleteAllPath(String path) {
        List<String> childrens = getChildren(path, false);
        if (childrens == null && childrens.isEmpty()) {
            deletePath(path);
            return true;
        } else {
            childrens.forEach(item -> {
                String url = path + File.separator + item;
                LOGGER.info("delete path ------> ", url);
                deleteAllPath(url);
            });
        }
        return true;
    }
}

class ZookeeperWatcher implements Watcher {

    private static final Logger logger = LoggerFactory.getLogger(ZookeeperWatcher.class);
    private final AtomicInteger sequeue = new AtomicInteger();

    @Override
    public void process(WatchedEvent event) {
        logger.info("进入 process , event=" + event);
        if (event == null) {
            logger.warn("没有任务事件触发");
            return;
        }

        // 事件连接状态
        Watcher.Event.KeeperState keeperState = event.getState();
        // 事件类型
        Watcher.Event.EventType eventType = event.getType();
        // 受影响的path
        String path = event.getPath();

        String logPrefix = "[watcher- " + this.sequeue.incrementAndGet() + " ]";

        logger.info(logPrefix + " 连接状态: " + keeperState.toString());
        logger.info(logPrefix + " 事件类型: " + eventType.toString());


        if (Event.KeeperState.SyncConnected == keeperState) {
            if (Event.EventType.None == eventType) {
                // 成功连接上zk服务器
                logger.info(logPrefix + " 成功连上zk服务器");
            } else if (Event.EventType.NodeCreated == eventType) {
                // 创建节点
                logger.info(logPrefix + " 节点创建");
                // 判断节点是不存在

            } else if (Event.EventType.NodeDataChanged == eventType) {
                // 更新节点
                logger.info(logPrefix + " 节点数据更新");
                // 读取数据

            } else if (Event.EventType.NodeChildrenChanged == eventType) {
                // 更新子节点
                logger.info(logPrefix + " 子节点变更");
                // 读取子节点列表
            } else if (Event.EventType.NodeDeleted == eventType) {
                // 删除节点
                logger.info(logPrefix + " 节点 " + path + " 被删除");

            }
        } else if (Event.KeeperState.Disconnected == keeperState) {
            // 服务器断开
            logger.info(logPrefix + " 与zk服务器断开连接 ");
        } else if (Event.KeeperState.AuthFailed == keeperState) {
            // 权限检查失败
            logger.info(logPrefix + "权限检查失败");
        } else if (Event.KeeperState.Expired == keeperState) {
            // 会话失效
            logger.info(logPrefix + " 会话失效");
        }
        logger.info("");
    }
}
