package org.ethan.demo.storm.drpc;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.StormSubmitter;
import org.apache.storm.drpc.LinearDRPCTopologyBuilder;

/**
 * 使用DRPC进行bolt时必须要提供drpc的服务否则无法进行服务 storm drpc
 * 当然也要进行drpc.server的配置
 */
public class BaseDRPCMain {

    public static void main(String[] args) {
        try {

            LinearDRPCTopologyBuilder builder = new LinearDRPCTopologyBuilder("execMethod");
            builder.addBolt(new BaseDRPCBolt(), 3);

            Config config = new Config();
            config.setDebug(true);

            if (args.length > 0) {
                config.setNumWorkers(3);
                StormSubmitter.submitTopology(args[0], config, builder.createRemoteTopology());
            } else {
                LocalDRPC drpc = new LocalDRPC();
                LocalCluster cluster = new LocalCluster();
                cluster.submitTopology("drpc-demo", config, builder.createLocalTopology(drpc));

                //客户端的本地测试
                System.out.println(drpc.execute("execMethod", "hello"));
                System.out.println(drpc.execute("execMethod", "world"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
