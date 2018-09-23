package org.ethan.demo.storm.trident;

import org.apache.kafka.common.utils.Utils;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.StormSubmitter;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.trident.operation.builtin.FilterNull;
import org.apache.storm.trident.operation.builtin.MapGet;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.testing.MemoryMapState;
import org.apache.storm.trident.testing.Split;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.ethan.demo.storm.function.CustomSplit;

import java.util.Random;

public class TridentPVCountMain {

    public static void main(String[] args) {

        try {
            Random random = new Random();
            String zone = "www.github.com";
            String[] sessionIds = new String[]{"123456", "654321", "456789", "098766", "123670"};
            String[] times = new String[]{"2018-06-17 08:38:08", "2018-06-17 08:38:18", "2018-06-18 08:38:28", "2018-06-18 08:38:38", "2018-06-18 08:38:48"};

            FixedBatchSpout spout = new FixedBatchSpout(new Fields("log"), 3,
                    new Values(zone + "\t" + sessionIds[random.nextInt(5)] + "\t" + times[random.nextInt(5)]),
                    new Values(zone + "\t" + sessionIds[random.nextInt(5)] + "\t" + times[random.nextInt(5)]),
                    new Values(zone + "\t" + sessionIds[random.nextInt(5)] + "\t" + times[random.nextInt(5)]),
                    new Values(zone + "\t" + sessionIds[random.nextInt(5)] + "\t" + times[random.nextInt(5)]),
                    new Values(zone + "\t" + sessionIds[random.nextInt(5)] + "\t" + times[random.nextInt(5)]));

            spout.setCycle(false);
//            spout.setCycle(true);

            TridentTopology topology = new TridentTopology();
            TridentState counts = topology.newStream("txId", spout)
                    .each(new Fields("log"), new CustomSplit("\t"), new Fields("session", "dateStr"))
                    .groupBy(new Fields("dateStr"))
                    .persistentAggregate(new MemoryMapState.Factory(), new Fields("session"), new Count(), new Fields("PV"));
            LocalDRPC drpc = null;
            if (!(args.length > 0)) {
                drpc = new LocalDRPC();
            }

            topology.newDRPCStream("datePV", drpc)
                    .each(new Fields("args"), new Split(), new Fields("date"))
                    .groupBy(new Fields("date"))
                    .stateQuery(counts, new Fields("date"), new MapGet(), new Fields("PV"))
                    .each(new Fields("PV"), new FilterNull());

            Config config = new Config();
            config.setDebug(true);
            config.setMaxSpoutPending(20);

            if (args.length > 0) {
                config.setNumWorkers(3);
                StormSubmitter.submitTopology(args[0], config, topology.build());
            } else {
                LocalCluster cluster = new LocalCluster();
                cluster.submitTopology("localTrident", config, topology.build());

                //模拟客户调用
                for (int i = 0; i < 10; i++) {
                    System.err.println("DRPC RESULT: " + drpc.execute("datePV", "2018-06-17 2018-06-18"));
                    Utils.sleep(1000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
