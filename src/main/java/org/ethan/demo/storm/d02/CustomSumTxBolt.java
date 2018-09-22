package org.ethan.demo.storm.d02;

import org.apache.storm.coordination.BatchOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseTransactionalBolt;
import org.apache.storm.transactional.ICommitter;
import org.apache.storm.transactional.TransactionAttempt;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

public class CustomSumTxBolt extends BaseTransactionalBolt implements ICommitter {

    private static final String DB_KEY = "count_key";
    private static Map<String, DBValue> dbMap = new HashMap<>(1);
    private BatchOutputCollector collector;
    private TransactionAttempt id;
    private long sum = 0;

    @Override
    public void prepare(Map conf, TopologyContext context, BatchOutputCollector collector, TransactionAttempt id) {
        this.collector = collector;
        this.id = id;
    }

    @Override
    public void execute(Tuple tuple) {
        long count = tuple.getLong(1);
        sum += count;
        System.out.println("sum Bolt count=" + count);
    }

    @Override
    public void finishBatch() {
        DBValue value = dbMap.get(DB_KEY);
        if (value == null || !value.getTxId().equals(id.getTransactionId().longValue())) {
            DBValue newValue = new DBValue();
            newValue.setTxId(id.getTransactionId().longValue());
            if (value == null) {
                newValue.setSum(this.sum);
            } else {
                newValue.setSum(this.sum + value.getSum());
            }
            dbMap.put(DB_KEY, newValue);
        }
        System.out.println("sum=" + dbMap.get(DB_KEY).getSum());

//        需要继续往下发送
//        collector.emit(new Values("", ""));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}

class DBValue {

    private Long txId;
    private long sum;

    public Long getTxId() {
        return txId;
    }

    public void setTxId(Long txId) {
        this.txId = txId;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }
}