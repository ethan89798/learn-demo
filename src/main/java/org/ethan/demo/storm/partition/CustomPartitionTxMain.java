package org.ethan.demo.storm.partition;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.transactional.TransactionalTopologyBuilder;
import org.ethan.demo.storm.d02.CustomSumTxBolt;
import org.ethan.demo.storm.d02.CustomTxBolt;

public class CustomPartitionTxMain {

    public static void main(String[] args) {
        try {

            /*
            使用分区事务时,各个Bolt的实现与其它的Bolt实现一致,只是spout的实现有所有不.
            即是数据录取的方式实现不同而已.
            目前分区的实现在众多高吞吐量的框架中得到使用,所以要清楚使用方式.
             */
            TransactionalTopologyBuilder builder =
                    new TransactionalTopologyBuilder("partitionId", "spoutId", new CustomPartitionTxSpout(), 1);
            builder.setBolt("txBolt", new CustomTxBolt(), 3).shuffleGrouping("spoutId");
            builder.setBolt("sumBolt", new CustomSumTxBolt(), 1).shuffleGrouping("txBolt");

            Config config = new Config();
            config.setDebug(false);

            if (args.length > 0) {
                StormSubmitter.submitTopology(args[0], config, builder.buildTopology());
            } else {
                LocalCluster cluster = new LocalCluster();
                cluster.submitTopology("partitionTop", config, builder.buildTopology());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
