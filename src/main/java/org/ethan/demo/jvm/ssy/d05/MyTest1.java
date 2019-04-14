package org.ethan.demo.jvm.ssy.d05;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆内存溢出
 * -Xms10m 设置堆最小内存
 * -Xmx10m 设置堆最大内存
 * --XX:+HeapDumpOnOutOfMemoryError 出现堆错误时dump出错误日志
 */
public class MyTest1 {
    public static void main(String[] args) {
        List<MyTest1> list = new ArrayList<>();

        for (; ; ) {
            list.add(new MyTest1());

            System.gc();
        }
    }
}
