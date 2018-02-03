package org.ethan.demo.jdk8.d01;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class LambadTest03 {
    public static void main(String[] args) {
//        TheInterface i1 = () -> {};
//        System.out.println(i1.getClass().getInterfaces()[0]);
//
//        TheInterface2 l2 = () -> {};
//        System.out.println(l2.getClass().getInterfaces()[0]);
//
//        new Thread(() -> System.out.println("Hello World")).start();

        List<String> list = Arrays.asList("hello", "world", "hello world");

//        list.forEach(item -> System.out.println(item.toUpperCase()));
//        list.stream().map(item -> item.toUpperCase()).forEach(item -> System.out.println(item));
        list.stream().map(String::toUpperCase).forEach(System.out::println);
    }
}

@FunctionalInterface
interface TheInterface {
    void myMethod();
}

@FunctionalInterface
interface TheInterface2 {
    void myMethod2();
}