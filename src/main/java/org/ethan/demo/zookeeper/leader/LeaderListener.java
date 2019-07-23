package org.ethan.demo.zookeeper.leader;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 实现伪选举: 思想-> 每个应用在启动时都向zookeeper创建一个临时节点,
 * 最终只有一个应用能创建成功,创建成功的就成功leader,其它不成功的就成功flower;
 * 如果主节点挂掉,那么其它flower就会收到节点删除的通知,然后所有flower也会向zookeeper创建一个节点
 * 当然也只有一个能成功,其它都为失败,成功的就会成为leader
 *
 * zookeeper作为配置中心也是差不多的原理, 每个应用增加一个节点数据改变的事件监听,
 * 如果这个节点发生了改变就会通知到各个应用, 然后应用就可以进行自己的配置更新.
 */
public class LeaderListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeaderListener.class);

    private static final ZkClient CLIENT = new ZkClient("192.168.1.210:2181");

    private static final String path = "/leader";

    private static boolean IS_LEADER = false;

    public static void main(String[] args) {
        init();

        //等待this.wait();
    }

    private static void init() {
        LOGGER.info("启动中......");
        contendLeader();
        CLIENT.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                LOGGER.warn("主节点故障,进行重新选举");
                // 开启一个定时器,如果竞争成功后,其它节点还会不断竞争
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        contendLeader();
                    }
                },5000);
                timer.purge();
            }
        });
    }

    private static void contendLeader() {
        try {
            CLIENT.createEphemeral(path);
            IS_LEADER = true;
            LOGGER.info("竞争Leader成功");
        } catch (Exception e) {
            LOGGER.error("竞争失败");
            IS_LEADER = false;
        }
    }

    private static void close() {
        CLIENT.close();
    }
}
