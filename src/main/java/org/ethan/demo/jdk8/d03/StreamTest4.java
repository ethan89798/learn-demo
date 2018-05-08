package org.ethan.demo.jdk8.d03;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StreamTest4 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(5000000);
        for (int i = 0; i < 5000000; i ++) {
            list.add(UUID.randomUUID().toString());
        }
        System.out.println("开始排序");
        long start = System.nanoTime();
        list.stream().sorted();
        long end = System.nanoTime();
        System.out.println("第一次用时: " + (end - start));
//        long start1 = System.nanoTime();
//        list.parallelStream().sorted();
//        long end1 = System.nanoTime();
//        System.out.println("第二次用时: " + (end1 - start1));
    }
}
