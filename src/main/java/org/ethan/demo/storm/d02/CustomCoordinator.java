package org.ethan.demo.storm.d02;

import org.apache.storm.transactional.ITransactionalSpout;

import java.math.BigInteger;

public class CustomCoordinator implements ITransactionalSpout.Coordinator<CustomData> {

    private static final int BATCH_NUM = 10;

    @Override
    public CustomData initializeTransaction(BigInteger txid, CustomData prevMetadata) {
        long begin = 0;
        if (prevMetadata != null) {
            begin = prevMetadata.getBegin() + prevMetadata.getNum();
        }
        CustomData data = new CustomData();
        data.setBegin(begin);
        data.setNum(BATCH_NUM);
        System.out.println("启动一个事务:" + data);
        return data;
    }

    @Override
    public boolean isReady() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void close() {

    }
}
