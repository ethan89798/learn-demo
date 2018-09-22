package org.ethan.demo.storm.d02;

import org.apache.commons.lang.StringUtils;
import org.apache.storm.coordination.BatchOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseTransactionalBolt;
import org.apache.storm.transactional.TransactionAttempt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class CustomTxBolt extends BaseTransactionalBolt {

    private long count = 0;
    private BatchOutputCollector collector;
    private TransactionAttempt id;
    @Override
    public void prepare(Map conf, TopologyContext context, BatchOutputCollector collector, TransactionAttempt id) {
        this.collector = collector;
        this.id = id;
    }

    @Override
    public void execute(Tuple tuple) {
        TransactionAttempt tx = (TransactionAttempt) tuple.getValue(0);
        String logStr = tuple.getString(1);
        System.out.println("TransactionAttempt tx_id=" + tx.getTransactionId() + ",attempt_id=" + tx.getAttemptId());
        System.out.println("log = " + logStr);
        if (StringUtils.isNotEmpty(logStr)) {
            count++;
        }
    }

    @Override
    public void finishBatch() {
        collector.emit(new Values(id, count));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("tx", "count"));
    }
}
