package org.ethan.demo.storm.d01;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ethan Huang
 * @since 2018/09/19
 */
public class CallLogCounterBolt implements IRichBolt {

    private static Logger logger = LoggerFactory.getLogger(CallLogCounterBolt.class);
    private Map<String, Integer> countMap;
    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        logger.warn("......CallLogCounterBolt invoked prepare()");
        this.countMap = new HashMap<>();
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        logger.warn("......CallLogCounterBolt invoked execute()");
        String call = input.getString(0);
        Integer duration = input.getInteger(1);
        if (countMap.containsKey(call)) {
            Integer count = countMap.get(call);
            countMap.put(call, count + 1);
        } else {
            countMap.put(call, 1);
        }
        collector.ack(input);
    }

    @Override
    public void cleanup() {
        logger.warn("......CallLogCounterBolt invoked cleanup()");
        countMap.forEach((k, v) -> System.out.println(k + " : " + v));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        logger.warn("......CallLogCounterBolt invoked declareOutputFields()");
        declarer.declare(new Fields("call"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        logger.warn("......CallLogCounterBolt invoked getComponentConfiguration()");
        return null;
    }
}
