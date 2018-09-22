package org.ethan.demo.storm.partition;

import org.apache.commons.lang.StringUtils;
import org.apache.storm.coordination.BatchOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.transactional.TransactionAttempt;
import org.apache.storm.transactional.partitioned.IOpaquePartitionedTransactionalSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.ethan.demo.storm.d02.CustomData;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CustomPartitionOpaqueTxSpout implements IOpaquePartitionedTransactionalSpout<CustomData> {

    private Map<Integer, Map<Long, String>> partitionMap = new HashMap<>(5);

    public CustomPartitionOpaqueTxSpout() {
        Random random = new Random();
        String zone = "www.github.com";
        String[] sessionIds = new String[]{"123456","654321","456789","098766","123670"};
        String[] times = new String[]{"2018-06-18 08:38:08","2018-06-18 08:38:18","2018-06-18 08:38:28","2018-06-18 08:38:38","2018-06-18 08:38:48"};

        //这些是模拟分区数据
        for (int j = 0; j < 5; j++) {
            Map<Long, String> map = new HashMap<>();
            for (Long i = 0L; i < 100; i++) {
                map.put(i, zone + "\t" + sessionIds[random.nextInt(5)] + "\t" + times[random.nextInt(5)]);
            }
            partitionMap.put(j, map);
        }
    }


    @Override
    public Emitter<CustomData> getEmitter(Map conf, TopologyContext context) {
        return new CustomPartitionOpequeEmitter(partitionMap);
    }

    @Override
    public Coordinator getCoordinator(Map conf, TopologyContext context) {
        return new CustomPartitionOpaqueCoordinator();
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("tx", "log"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}

class CustomPartitionOpaqueCoordinator implements IOpaquePartitionedTransactionalSpout.Coordinator {
    @Override
    public boolean isReady() {
        return true;
    }
    @Override
    public void close() {
    }
}

class CustomPartitionOpequeEmitter implements IOpaquePartitionedTransactionalSpout.Emitter<CustomData> {

    private static final long NUM = 10;
    private Map<Integer, Map<Long, String>> dataMap;

    public CustomPartitionOpequeEmitter(Map<Integer, Map<Long, String>> dataMap) {
        this.dataMap = dataMap;
    }

    @Override
    public CustomData emitPartitionBatch(TransactionAttempt tx, BatchOutputCollector collector, int partition, CustomData lastPartitionMeta) {
        long begin = 0;
        if (lastPartitionMeta != null) {
            begin = lastPartitionMeta.getBegin();
        }

        CustomData data = new CustomData();
        data.setNum(NUM);
        data.setBegin(begin);

        System.err.println("启动一个透明的分区事务: " + data);


        Map<Long, String> map = dataMap.get(partition);
        for (long i = begin; i < begin + NUM; i++) {
            String log = map.get(i);
            if (StringUtils.isNotEmpty(log)) {
                collector.emit(new Values(tx, log));
            }
        }

        return data;
    }

    @Override
    public int numPartitions() {
        return dataMap.size();
    }

    @Override
    public void close() {

    }
}