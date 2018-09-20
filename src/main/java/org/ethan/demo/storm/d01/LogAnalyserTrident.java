package org.ethan.demo.storm.d01;

import com.google.common.collect.ImmutableList;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.operation.builtin.*;
import org.apache.storm.trident.testing.FeederBatchSpout;
import org.apache.storm.trident.testing.MemoryMapState;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @author Ethan Huang
 * @since 2018/09/19
 */
public class LogAnalyserTrident {

    private static Logger logger = LoggerFactory.getLogger(LogAnalyserTrident.class);

    public static void main(String[] args) {

        TridentTopology topology = new TridentTopology();

        FeederBatchSpout testSpout = new FeederBatchSpout(ImmutableList.of("from", "to", "duration"));

        TridentState callCounts = topology.newStream("fixed-batch-spout", testSpout)
                .each(new Fields("from", "to"), new FormatCall(), new Fields("call"))
                .groupBy(new Fields("call"))
                .persistentAggregate(new MemoryMapState.Factory(), new Count(), new Fields("count"));

        LocalDRPC drpc = new LocalDRPC();

        topology.newDRPCStream("call_count", drpc).stateQuery(callCounts, new Fields("args"), new MapGet(), new Fields("count"));

        topology.newDRPCStream("multiple_call_count", drpc).each(new Fields("args"), new CSVSplit(), new Fields("call"))
                .groupBy(new Fields("call")).stateQuery(callCounts, new Fields("call"), new MapGet(), new Fields("count"))
                .each(new Fields("call", "count"), new Debug())
                .each(new Fields("count"), new FilterNull())
                .aggregate(new Fields("count"), new Sum(), new Fields("sum"));

        Config config = new Config();
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("trident", config, topology.build());

        Random random = new Random();
        int idx = 0;
        while (idx < 10) {
            testSpout.feed(ImmutableList.of(new Values("1234123401", "1234123402", random.nextInt(60))));
            testSpout.feed(ImmutableList.of(new Values("1234123401", "1234123403", random.nextInt(60))));
            testSpout.feed(ImmutableList.of(new Values("1234123401", "1234123404", random.nextInt(60))));
            testSpout.feed(ImmutableList.of(new Values("1234123402", "1234123403", random.nextInt(60))));
            idx++;
        }

        logger.warn(drpc.execute("call_count", "1234123401-1234123404"));
        logger.warn(drpc.execute("multiple_call_count", "1234123401-1234123402,1234123401-1234123403"));

        cluster.shutdown();
        drpc.shutdown();
    }
}

class FormatCall extends BaseFunction {

    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
        String from = tuple.getString(0);
        String to = tuple.getString(1);
        collector.emit(new Values(from + "-" + to));
    }
}

class CSVSplit extends BaseFunction {

    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
        for (String word : tuple.getString(0).split(",")){
            if (word.length() > 0) {
                collector.emit(new Values(word));
            }
        }
    }
}