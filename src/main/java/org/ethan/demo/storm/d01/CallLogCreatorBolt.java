package org.ethan.demo.storm.d01;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Bolt是一个使用元组作为输入，处理元组，并产生新的元组作为输出的组件。Bolts将实现IRichBolt接口。
 * @author Ethan Huang
 * @since 2018/09/19
 */
public class CallLogCreatorBolt implements IRichBolt {

    private static Logger logger = LoggerFactory.getLogger(CallLogCreatorBolt.class);
    private OutputCollector collector;

    /**
     * 为Bolt提供要执行的环境, 执行器将运行些方法来初始化Spout.
     * @param stormConf 为Bolt提供Storm配置
     * @param context 提供有关拓扑中的Bolt位置, 其任务ID, 输入和输出信息等的完整信息
     * @param collector 使用我们能够发出处理的元组
     */
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        logger.warn(".....CallLogCreatorBolt invoked prepare()");
        this.collector = collector;
    }

    /**
     * 处理单个元组输入
     * @param input 元组信息
     */
    @Override
    public void execute(Tuple input) {
        logger.warn(".....CallLogCreatorBolt invoked execute()");

        String fromNum = input.getString(0);
        String toNum = input.getString(1);
        Integer duration = input.getInteger(2);
        collector.emit(new Values(fromNum + " - " + toNum, duration));
    }

    /**
     * 当Spout要关闭时调用
     */
    @Override
    public void cleanup() {
        logger.warn(".....CallLogCreatorBolt invoked cleanup()");
    }

    /**
     * 声明元组的输出模式
     * @param declarer 用于声明输出流id，输出字段等
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        logger.warn(".....CallLogCreatorBolt invoked declareOutputFields()");
        declarer.declare(new Fields("call", "duration"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        logger.warn(".....CallLogCreatorBolt invoked getComponentConfiguration()");
        return null;
    }
}
