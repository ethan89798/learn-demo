package org.ethan.demo.jdk8.d03;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 *
 */
public class MyCollector2<T> implements Collector<T, Set<T>, Map<T, T>> {
    @Override
    public Supplier<Set<T>> supplier() {
        System.out.println("supplier invoked!!");
        return HashSet::new;
    }

    @Override
    public BiConsumer<Set<T>, T> accumulator() {
        System.out.println("accumulator invoked!!");
        return (set, item) -> {
            /*
             在并行流中,一个线程对容器进遍历, 另一个线程有可能会添加元素,这样就会很容易抛出异常
             */
            System.out.println("set[" + set + "]" + Thread.currentThread().getName());
            set.add(item);
        };
    }

    @Override
    public BinaryOperator<Set<T>> combiner() {
        System.out.println("combiner invoked!!");
        return (left, right) -> {
            left.addAll(right);
            return left;
        };
    }

    @Override
    public Function<Set<T>, Map<T, T>> finisher() {
        System.out.println("finisher invoked!!");
        return set -> {
            Map<T, T> map = new HashMap<>(set.size());
            set.forEach(item -> map.put(item, item));
            return map;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED, Characteristics.CONCURRENT));
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            List<String> list = Arrays.asList("hello", "world", "hello world", "aa");
            Map<String, String> map = list.parallelStream().collect(new MyCollector2<>());
            System.out.println(map);
        }
    }
}
