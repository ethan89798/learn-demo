package org.ethan.demo.storm.drpc;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.StormSubmitter;
import org.apache.storm.drpc.DRPCSpout;
import org.apache.storm.drpc.ReturnResults;
import org.apache.storm.topology.TopologyBuilder;

public class NomalDRPCMain {

    public static void main(String[] args) {
        try {
            TopologyBuilder builder = new TopologyBuilder();

            Config config = new Config();
            config.setDebug(true);

            if (args.length > 0) {
                DRPCSpout spout = new DRPCSpout("testMethod");
                builder.setSpout("drpcSpout", spout, 1);
                builder.setBolt("drpcBolt", new BaseDRPCBolt(), 3).shuffleGrouping("drpcSpout");
                builder.setBolt("returnBolt", new ReturnResults(), 3).shuffleGrouping("drpcBolt");
                StormSubmitter.submitTopology(args[0], config, builder.createTopology());
            } else {
                LocalDRPC drpc = new LocalDRPC();
                DRPCSpout spout = new DRPCSpout("testMethod", drpc);
                builder.setSpout("drpcSpout", spout, 1);
                builder.setBolt("drpcBolt", new BaseDRPCBolt(), 3).shuffleGrouping("drpcSpout");
                builder.setBolt("returnBolt", new ReturnResults(), 3).shuffleGrouping("drpcBolt");

                LocalCluster cluster = new LocalCluster();
                cluster.submitTopology("DRPC-demo2", config, builder.createTopology());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
