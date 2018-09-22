package org.ethan.demo.storm.d02;

import java.io.Serializable;

public class CustomData implements Serializable {
    //事务开始位置
    private long begin;
    //batch个数
    private long num;

    public long getBegin() {
        return begin;
    }

    public void setBegin(long begin) {
        this.begin = begin;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "CustomData{" +
                "begin=" + begin +
                ", num=" + num +
                '}';
    }
}
