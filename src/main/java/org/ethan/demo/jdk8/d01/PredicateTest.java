package org.ethan.demo.jdk8.d01;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 */
public class PredicateTest {
    public static void main(String[] args) {
        Predicate<String> predicate = p -> p.length() > 5;

        System.out.println(predicate.test("Hello"));
        System.out.println(predicate.test("Hello world"));

        PredicateTest test = new PredicateTest();
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println("============");
        test.conditionFilter(list, item -> item % 2 == 0);
        System.out.println("============");
        test.conditionFilter(list, item -> item % 2 != 0);
        System.out.println("============");
        test.conditionFilter(list, item -> item > 6);
        System.out.println("============");
        test.conditionFilter(list, item -> item < 3);



    }

    public void conditionFilter(List<Integer> list, Predicate<Integer> predicate) {
        list.forEach(item -> {
            if (predicate.test(item)) {
                System.out.println(item);
            }
        });
    }
}
