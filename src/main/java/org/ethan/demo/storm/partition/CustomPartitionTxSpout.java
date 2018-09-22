package org.ethan.demo.storm.partition;

import org.apache.commons.lang.StringUtils;
import org.apache.storm.coordination.BatchOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.transactional.TransactionAttempt;
import org.apache.storm.transactional.partitioned.IPartitionedTransactionalSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.ethan.demo.storm.d02.CustomData;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CustomPartitionTxSpout implements IPartitionedTransactionalSpout<CustomData> {


    private Map<Integer, Map<Long, String>> partitionMap = new HashMap<>(5);

    public CustomPartitionTxSpout() {
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
    public Coordinator getCoordinator(Map conf, TopologyContext context) {
        return new CustomPartitionCoordinator();
    }

    @Override
    public Emitter<CustomData> getEmitter(Map conf, TopologyContext context) {
        return new CustomPartitionEmitter(partitionMap);
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

class CustomPartitionCoordinator implements IPartitionedTransactionalSpout.Coordinator {

    @Override
    public int numPartitions() {
        return 5;
    }

    @Override
    public boolean isReady() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void close() {

    }
}

class CustomPartitionEmitter implements IPartitionedTransactionalSpout.Emitter<CustomData> {

    private static final long BATCH_NUM = 10L;
    private Map<Integer, Map<Long, String>> data;

    public CustomPartitionEmitter(Map<Integer, Map<Long, String>> data) {
        this.data = data;
    }

    @Override
    public CustomData emitPartitionBatchNew(TransactionAttempt tx, BatchOutputCollector collector, int partition, CustomData lastPartitionMeta) {
        long begin = 0;
        if (lastPartitionMeta != null) {
            begin = lastPartitionMeta.getBegin() + lastPartitionMeta.getNum();
        }
        CustomData data = new CustomData();
        data.setBegin(begin);
        data.setNum(BATCH_NUM);
        emitPartitionBatch(tx, collector, partition, data);
        System.err.println("启动一个分区事务: " + data);
        return data;
    }

    @Override
    public void emitPartitionBatch(TransactionAttempt tx, BatchOutputCollector collector, int partition, CustomData partitionMeta) {

        System.err.println("emitPartitionBatch partition=" + partition);

        long begin = partitionMeta.getBegin();
        long num = partitionMeta.getNum();

        Map<Long, String> map = this.data.get(partition);
        for (long i = begin; i < begin + num; i++) {
            String log = map.get(i);
            if (StringUtils.isNotEmpty(log)) {
                collector.emit(new Values(tx, log));
            }
        }
    }

    @Override
    public void close() {

    }
}
