package org.ethan.demo.jdk8.d03;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 将Stream转换成集合主要分为两种方式
 * 1) 先转为数组然后再转为集合
 * 2) 直接通过collect()方法进行转换(collect方法也有两个种重载的,一种是通过JDK自身实现的收集器进行转换,另一种是通过自己定义的方式进行转换)
 *    像一些JDK并没有提供的收集器实现的,那就要自己实现(比如LinkedList);当然也可以通过Collectors.toCollection(LinkedList::new)实现
 *
 */
public class StreamTest3 {
    public static void main(String[] args) {
        Stream<String> stream = Stream.of("hello", "world", "hello world");
        // 第一种方式
//        String[] strings = stream.toArray(length -> new String[length]);
//        List<String> list = Arrays.asList(strings);
//        list.forEach(System.out::println);

        // 第二种方式
//        stream.collect(Collectors.toList()).forEach(System.out::println);

        // 第三种方式
//        stream.collect(() -> new ArrayList<>(), (list, item) -> list.add(item), (left, right) -> left.addAll(right))
//                .forEach(System.out::println);

        // 第四种方式
//        stream.collect(LinkedList::new, LinkedList::add, LinkedList::addAll).forEach(System.out::println);

        // 第五种方式(通过这种方式也可以实现LinkedList的收集)
//        stream.collect(Collectors.toCollection(TreeSet::new)).forEach(System.out::println);

        // flatMap将流集合进行磨平
        Stream<List<Integer>> intStream = Stream.of(Arrays.asList(1), Arrays.asList(2, 3), Arrays.asList(4, 5, 6));
        intStream.flatMap(list -> list.stream()).map(item -> item * 2).forEach(System.out::println);

        System.out.println("=========");
        Stream<String> stream1 = Stream.generate(() -> UUID.randomUUID().toString());
        stream1.findFirst().ifPresent(System.out::println);

        System.out.println("========");

        Stream<Integer> integerStream = Stream.iterate(1, item -> item + 2).limit(6);
        integerStream.forEach(System.out::println);

        System.out.println("========");

        // 作业题:大于2,每个元素乘以2,忽略前两个,拿前两个数的和
        Stream<Integer> stream2 = Stream.iterate(1, item -> item + 2).limit(6);
//        OptionalInt optional = stream2.filter(item -> item > 2).mapToInt(item -> item * 2).skip(2).limit(2).reduce(Integer::sum);
//        optional.ifPresent(System.out::println);
        int sum = stream2.filter(item -> item > 2).mapToInt(item -> item * 2).skip(2).limit(2).sum();
        System.out.println(sum);
    }
}
