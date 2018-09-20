package org.ethan.demo.storm.d01;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Spout是用于数据生成的组件。基本上，一个Spout将实现一个IRichSpout接口。
 * @author Ethan Huang
 * @since 2018/09/19
 */
public class FakeCallLogReaderSpout implements IRichSpout {

    private static Logger logger = LoggerFactory.getLogger(FakeCallLogReaderSpout.class);
    private TopologyContext context;
    private SpoutOutputCollector collector;
    private Integer idx = 0;
    private Random random = new Random();

    /**
     * 为Spout提供执行环境, 执行器将运行些方法来初始化喷头
     * @param conf 为Spout提供Storm配置
     * @param context 提供有关拓扑中的Spout位置,其任务ID,输出和输入信息的完整信息
     * @param collector 使我们能够发出将由Bolt处理的元组
     */
    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        logger.warn("......FakeCallLogReaderSpout invoked open()");
        this.context = context;
        this.collector = collector;
    }

    /**
     * 当Spout将要关闭时调用这方法
     */
    @Override
    public void close() {
        logger.warn("......FakeCallLogReaderSpout invoked close()");
    }

    /**
     *
     */
    @Override
    public void activate() {
        logger.warn("......FakeCallLogReaderSpout invoked activate()");
    }

    /**
     *
     */
    @Override
    public void deactivate() {
        logger.warn("......FakeCallLogReaderSpout invoked deactivate()");
    }

    /**
     * 通过收集器发出生成的数据
     */
    @Override
    public void nextTuple() {
        logger.warn("......FakeCallLogReaderSpout invoked nextTuple()");
        if (this.idx <= 1000) {
            List<String> mobileNum = new ArrayList<>(4);
            mobileNum.add("1234123401");
            mobileNum.add("1234132402");
            mobileNum.add("1234123403");
            mobileNum.add("1234123404");

            int localIdx = 0;
            while (localIdx++ < 100 && this.idx++ < 1000) {
                String fromMobile = mobileNum.get(random.nextInt(4));
                String toMobile = mobileNum.get(random.nextInt(4));
                while (fromMobile.equals(toMobile)) {
                    toMobile = mobileNum.get(random.nextInt(4));
                }

                Integer duration = random.nextInt(60);
                this.collector.emit(new Values(fromMobile, toMobile, duration));
            }
        }
    }

    /**
     * 确认处理了特定元组
     * @param msgId
     */
    @Override
    public void ack(Object msgId) {
        logger.warn("......FakeCallLogReaderSpout invoked ack()");
    }

    /**
     * 指定不处理和不重新处理特定元组
     * @param msgId
     */
    @Override
    public void fail(Object msgId) {
        logger.warn("......FakeCallLogReaderSpout invoked fail()");
    }

    /**
     * 用于指定元组的输出模式
     * @param declarer 它用于声明输出流id，输出字段等
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        logger.warn("......FakeCallLogReaderSpout invoked declareOutputFields()");
        declarer.declare(new Fields("from", "to", "duration"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        logger.warn("......FakeCallLogReaderSpout invoked getComponentConfiguration()");
        return null;
    }
}
