package org.ethan.demo.zookeeper.d01;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class ZookeeperAuthTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperAuthTest.class);

    public static void main(String[] args) {

        String path = "/test-auth";
        byte[] data = "测试权限".getBytes();

        ZookeeperAuthClient authClient = new ZookeeperAuthClient();
        authClient.getConnection("192.168.1.210:2181", 1000,  "123456");
        LOGGER.info("创建权限节点开始");
        authClient.createPath(path, data);
        LOGGER.info("创建权限节点结束");
        ZookeeperAuthClient badAuthClient = new ZookeeperAuthClient();
        badAuthClient.getConnection("192.168.1.210:2181", 1000, "654321");
        ZookeeperAuthClient noAuthClient = new ZookeeperAuthClient();
        noAuthClient.getConnection("192.168.1.210:2181", 1000, null);

        LOGGER.info("请取权限数据开始");
        LOGGER.info("没有权限读, data={}", noAuthClient.readData(path, true));
        LOGGER.info("认证错误读, data={}",badAuthClient.readData(path, true));
        LOGGER.info("正确认证读, data={}", authClient.readData(path, true));
        LOGGER.info("读取权限数据结束");

        LOGGER.info("更新权限数据开始");
        LOGGER.info("没有权限更新, data={}", noAuthClient.updatePath(path, "百事可乐1".getBytes()));
        LOGGER.info("认证错误更新, data={}",badAuthClient.updatePath(path, "百事可乐2".getBytes()));
        LOGGER.info("正确认证更新, data={}", authClient.updatePath(path, "百事可乐3".getBytes()));
        LOGGER.info("更新权限数据结束");

        /**
         * 测试结果无论是错误授权或无授权都可以删除,不需要认证.
         * TODO 后缀去了解下这个删除的原因
         */
        LOGGER.info("删除权限数据开始");
        LOGGER.info("认证错误删除, data={}",badAuthClient.deletePath(path));
        LOGGER.info("没有权限删除, data={}", noAuthClient.deletePath(path));
        LOGGER.info("正确认证删除, data={}", authClient.deletePath(path));
        LOGGER.info("删除权限数据结束");

        noAuthClient.closeConnection();
        badAuthClient.closeConnection();
        authClient.closeConnection();
    }
}

class ZookeeperAuthClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperAuthClient.class);
    private ZooKeeper zookeeper;

    public ZooKeeper getConnection(String addr, int timeout, String pwd) {
        try {
            this.zookeeper = new ZooKeeper(addr, timeout, new ZookeeperAuthWatcher());
            if (!StringUtils.isEmpty(pwd)) {
                this.zookeeper.addAuthInfo("digest", pwd.getBytes());
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return this.zookeeper;
    }

    public void closeConnection() {
        try {
            if (this.zookeeper != null) {
                this.zookeeper.close();
            }
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public boolean createPath(String path, byte[] data) {
        try {

            String s = this.zookeeper.create(path, data, ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
            if (!StringUtils.isEmpty(s)) {
                return true;
            }
        } catch (KeeperException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    public boolean updatePath(String path, byte[] data) {
        try {
            Stat stat = this.zookeeper.setData(path, data, -1);
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

    public boolean deletePath(String path) {
        try {
            this.zookeeper.delete(path, -1);
            return true;
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (KeeperException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    public boolean exsisPath(String path, boolean needWatch) {
        try {
            Stat stat = this.zookeeper.exists(path, needWatch);
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

    public byte[] readData(String path, boolean needWatch) {
        try {
            return this.zookeeper.getData(path, needWatch, null);
        } catch (KeeperException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return new byte[0];
    }


}

class ZookeeperAuthWatcher implements Watcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperAuthWatcher.class);

    @Override
    public void process(WatchedEvent event) {
        LOGGER.info("zookeeper 监听开始... event={}", event);
        if (event == null) {
            return;
        }

        // 连接状态
        Event.KeeperState keeperState = event.getState();
        // 事件类型
        Event.EventType eventType = event.getType();
        // 受影响路径
        String path = event.getPath();

        LOGGER.info("连接状态-->{}", keeperState);
        LOGGER.info("事件状态-->{}", eventType);

        if (Event.KeeperState.SyncConnected == keeperState) {
            if (Event.EventType.None == eventType) {
                LOGGER.info("成功连接上服务器");
            }
        } else if (Event.KeeperState.Disconnected == keeperState) {
            LOGGER.info("与zk服务器断开连接");
        } else if (Event.KeeperState.AuthFailed == keeperState) {
            LOGGER.info("权限检查失败");
        } else if (Event.KeeperState.Expired == keeperState) {
            LOGGER.info("会话失效");
        }
        LOGGER.info("---- 监听结束 -----");

    }
}
