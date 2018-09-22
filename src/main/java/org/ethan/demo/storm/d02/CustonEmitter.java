package org.ethan.demo.storm.d02;

import org.apache.commons.lang.StringUtils;
import org.apache.storm.coordination.BatchOutputCollector;
import org.apache.storm.transactional.ITransactionalSpout;
import org.apache.storm.transactional.TransactionAttempt;
import org.apache.storm.tuple.Values;

import java.math.BigInteger;
import java.util.Map;

public class CustonEmitter implements ITransactionalSpout.Emitter<CustomData> {

    private Map<Long, String> dbMap;
    public CustonEmitter(Map<Long, String> dbMap) {
        this.dbMap = dbMap;
    }

    @Override
    public void emitBatch(TransactionAttempt tx, CustomData coordinatorMeta, BatchOutputCollector collector) {
        long begin = coordinatorMeta.getBegin();
        long num = coordinatorMeta.getNum();
        long end = begin + num;
        for (long i = begin; i < end; i++) {
            String log = dbMap.get(i);
            if (StringUtils.isNotEmpty(log)) {
                collector.emit(new Values(tx, log));
            }
        }
    }

    @Override
    public void cleanupBefore(BigInteger txid) {

    }

    @Override
    public void close() {

    }
}
