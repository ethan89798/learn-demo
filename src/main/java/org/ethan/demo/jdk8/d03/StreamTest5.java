package org.ethan.demo.jdk8.d03;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest5 {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello", "world", "hello world");
//        list.stream().mapToInt(String::length).filter(item -> item == 5).findFirst().ifPresent(System.out::println);
        list.stream().map(item -> {
            int length = item.length();
            //打印这个主要是为了测试终止操作后有没有向下执行操作
            System.out.println(length);
            return length;
        }).filter(item -> item == 5).findFirst().ifPresent(System.out::println);

        System.out.println("========");

        List<String> list1 = Arrays.asList("hello world", "hello welcome", "hello world welcome");
        list1.stream().flatMap(item -> Stream.of(item.split(" "))).distinct().forEach(System.out::println);

        System.out.println("========");
        List<String> list2 = Arrays.asList("nihao", "hello", "你好");
        List<String> list3 = Arrays.asList("zhangsan", "lisi", "wangwu", "zhaolu");
//        list2.stream().map(item -> list3.stream().map(item2 -> item + " " + item2)).forEach(item -> {
//            item.forEach(System.out::println);
//        });
        list2.stream().flatMap(item -> list3.stream().map(item2 -> item + " " + item2)).forEach(System.out::println);

//        list2.stream().collect(Collectors.groupingBy(Student::getAge, Collectors.groupingBy(Student::getName())));

        list2.stream().sorted(Comparator.comparingInt((String item) -> item.length()).reversed().thenComparing(Comparator.comparing(String::toLowerCase)).thenComparing(Comparator.naturalOrder()));
        Collections.sort(list2, Comparator.comparingInt(String::length).reversed());
    }
}
