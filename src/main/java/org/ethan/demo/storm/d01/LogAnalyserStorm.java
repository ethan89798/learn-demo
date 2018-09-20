package org.ethan.demo.storm.d01;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * @author Ethan Huang
 * @since 2018/09/19
 */
public class LogAnalyserStorm {

    public static void main(String[] args) throws InterruptedException {
        Config config = new Config();
        config.setDebug(true);


        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("call-log-reader-sport", new FakeCallLogReaderSpout());
        builder.setBolt("call-log-creator-bolt", new CallLogCreatorBolt()).shuffleGrouping("call-log-reader-sport");
        builder.setBolt("call-log-countor-bolt", new CallLogCounterBolt()).fieldsGrouping("call-log-creator-bolt", new Fields("call"));

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("LogAnalyserStorm", config, builder.createTopology());

        Thread.sleep(20000);

        cluster.shutdown();
    }
}
