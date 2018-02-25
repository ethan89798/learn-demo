package org.ethan.demo.jdk8.d03;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 常见创建流的三种方式
 * 本质上第一种与第二种差不多是同一种方式, 只是做多了一层包装
 */
public class StreamTest {
    public static void main(String[] args) {
        // 1.
        Stream<String> stream = Stream.of("hello", "world", "hello world");
        // 2.
        Stream<String> stream1 = Arrays.stream(new String[]{"hello", "world", "hello world"});
        // 3.
        List<String> list = Arrays.asList("hello", "world", "hello world");
        Stream<String> stream2 = list.stream();
    }
}
