package org.ethan.demo.storm.d02;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.transactional.ITransactionalSpout;
import org.apache.storm.tuple.Fields;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CustomTxSpout implements ITransactionalSpout<CustomData> {

    private Map<Long, String> dbMap;
    public CustomTxSpout() {
        this.dbMap = new HashMap<>();
        Random random = new Random();
        String zone = "www.github.com";
        String[] sessionIds = new String[]{"123456","654321","456789","098766","123670"};
        String[] times = new String[]{"2018-06-18 08:38:08","2018-06-18 08:38:18","2018-06-18 08:38:28","2018-06-18 08:38:38","2018-06-18 08:38:48"};

        for (Long i = 0L; i < 100; i++) {
            dbMap.put(i, zone + "\t" + sessionIds[random.nextInt(5)] + "\t" + times[random.nextInt(5)]);
        }
    }

    @Override
    public Coordinator<CustomData> getCoordinator(Map conf, TopologyContext context) {
        return new CustomCoordinator();
    }

    @Override
    public Emitter<CustomData> getEmitter(Map conf, TopologyContext context) {
        return new CustonEmitter(dbMap);
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
