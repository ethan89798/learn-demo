package org.ethan.demo.storm.d02;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.transactional.TransactionalTopologyBuilder;

public class CustomTopyMain {

    public static void main(String[] args) {
        try {
            TransactionalTopologyBuilder builder =
                    new TransactionalTopologyBuilder("buiderId", "spoutId", new CustomTxSpout(), 1);

            builder.setBolt("txBolt", new CustomTxBolt(), 3).shuffleGrouping("spoutId");
            builder.setBolt("sumBolt", new CustomSumTxBolt(), 1).shuffleGrouping("txBolt");

            Config config = new Config();
            config.setDebug(true);

            if (args.length > 0) {
                StormSubmitter.submitTopology(args[0], config, builder.buildTopology());
            } else {
                LocalCluster cluster = new LocalCluster();
                cluster.submitTopology("customTx", config, builder.buildTopology());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
