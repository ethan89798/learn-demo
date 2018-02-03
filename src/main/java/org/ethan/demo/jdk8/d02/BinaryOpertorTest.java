package org.ethan.demo.jdk8.d02;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.BinaryOperator;

/**
 *
 */
public class BinaryOpertorTest {

    public static void main(String[] args) {
        System.out.println(compute(1, 2, (a, b) -> a + b));
        System.out.println(compute(1, 2, (a, b) -> a - b));
        System.out.println(compute(1, 2, (a, b) -> a * b));

        System.out.println("============");

        System.out.println(getShort("hello123", "world", (a, b) -> a.length() - b.length()));
        System.out.println(getShort("hello123", "world", Comparator.comparing(String::length)));
        System.out.println(getShort("hello123", "world", Comparator.comparingInt(String::length)));
        System.out.println(getShort("heelo123", "world", (a, b) -> a.charAt(0) - b.charAt(0)));
        System.out.println(getShort("heelo123", "world", Comparator.comparingInt(item -> item.charAt(0))));
        System.out.println(getShort("heelo123", "world", Comparator.comparing(item -> item.charAt(0))));

        System.out.println();

        Optional<String> optional = Optional.of("hello");
        optional.ifPresent(System.out::println);

        Optional<String> optional1 = Optional.empty();
        optional1.ifPresent(System.out::println);

        System.out.println(optional1.orElse("world"));
        System.out.println(optional1.orElseGet(String::new));
    }

    private static int compute(int a, int b, BinaryOperator<Integer> operator) {
        return operator.apply(a, b);
    }

    private static String getShort(String a, String b, Comparator<String> comparator) {
        return BinaryOperator.minBy(comparator).apply(a, b);
    }
}
