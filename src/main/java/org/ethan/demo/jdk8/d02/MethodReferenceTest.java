package org.ethan.demo.jdk8.d02;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Ethan Huang
 * @since 2018-01-31 22:20
 */
public class MethodReferenceTest {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello", "world", "Hello world");
//        list.forEach(item -> System.out.println(item));

        list.sort(String::compareTo);
        list.forEach(System.out::println);


        System.out.println("------------");

        MethodReferenceTest test = new MethodReferenceTest();
        System.out.println(test.getString(String::new));

        System.out.println(test.getString2("Hello", String::new));
    }

    public String getString(Supplier<String> supplier) {
        return supplier.get() + "nihao";
    }

    public String getString2(String str, Function<String, String> function) {
        return function.apply(str);
    }
}
