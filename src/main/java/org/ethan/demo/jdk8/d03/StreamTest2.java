package org.ethan.demo.jdk8.d03;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class StreamTest2 {
    public static void main(String[] args) {
        IntStream.of(new int[]{2, 3, 4, 5, 6}).forEach(System.out::println);
        System.out.println("=========");
        IntStream.range(3, 8).forEach(System.out::println);
        System.out.println("=========");
        IntStream.rangeClosed(3, 8).forEach(System.out::println);

        System.out.println("=========");
        List<Integer> list = Arrays.asList(3, 4, 5, 6, 7);
        list.stream().map(v -> v * 2).reduce(Integer::sum).ifPresent(System.out::println);
        System.out.println(list.stream().mapToInt(v -> v * 2).sum());


    }
}
