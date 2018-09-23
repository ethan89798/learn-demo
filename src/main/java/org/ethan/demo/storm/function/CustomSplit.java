package org.ethan.demo.storm.function;

import org.apache.commons.lang.StringUtils;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;
import org.ethan.demo.comm.utils.DateUtils;

import java.text.ParseException;

public class CustomSplit extends BaseFunction {

    private String splitStr;

    public CustomSplit(String splitStr) {
        this.splitStr = splitStr;
    }

    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
        String log = tuple.getString(0);
        if (StringUtils.isNotEmpty(log)) {
            try {
                String[] arr = log.split(splitStr);
                String dateStr = DateUtils.getDateStr(arr[2]);
                collector.emit(new Values(arr[1], dateStr));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
