package org.ethan.demo.storm.partition;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.transactional.TransactionalTopologyBuilder;
import org.ethan.demo.storm.d02.CustomSumTxBolt;
import org.ethan.demo.storm.d02.CustomTxBolt;

public class CustomPartitionOpaqueTxMain {
    public static void main(String[] args) {
        try {
            TransactionalTopologyBuilder builder =
                    new TransactionalTopologyBuilder("opaqueTx", "spoutId", new CustomPartitionOpaqueTxSpout(), 1);
            builder.setBolt("txBolt", new CustomTxBolt(), 3).shuffleGrouping("spoutId");
            builder.setBolt("sumBolt", new CustomSumTxBolt(), 1).shuffleGrouping("txBolt");

            Config config = new Config();
            config.setDebug(false);


            if (args.length > 0) {
                StormSubmitter.submitTopology(args[0], config, builder.buildTopology());
            } else {
                LocalCluster cluster = new LocalCluster();
                cluster.submitTopology("customOpaqueTx", config, builder.buildTopology());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
