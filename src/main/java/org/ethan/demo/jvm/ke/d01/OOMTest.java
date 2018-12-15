package org.ethan.demo.jvm.ke.d01;

import java.util.LinkedList;
import java.util.List;

/**
 * 设置运行参数-Xms5m -Xmx5m -XX:+PrintGC
 * 以下出现两个堆内存溢出:
 * 1. java.lang.OutOfMemoryError: GC overhead limit exceeded
 *      一般是某个循环在不断分配对象,导致把堆内存撑爆
 * 2. java.lang.OutOfMemoryError: Java heap space
 *      一般是分配了巨型对象,导致把堆内存撑爆
 */
public class OOMTest {
    public static void main(String[] args) {
        //方式一: java.lang.OutOfMemoryError: GC overhead limit exceeded
        List<Object> list = new LinkedList<>();
        int i = 0;
        while (true) {
            i++;
            if (i % 10000 == 0) {
                System.out.println("i = " + i);
            }
            list.add(new Object());
        }

        //方式二: java.lang.OutOfMemoryError: Java heap space
//        String[] arr = new String[100000000];

    }
}
