package org.ethan.demo.jdk8.d03;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorTest {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("nihao", "hello", "world", "welcome");

//        Collections.sort(list);
//        Collections.sort(list, (a, b) -> a.compareTo(b));
//        list.sort(Comparator.naturalOrder());
//        Collections.sort(list, Comparator.naturalOrder());
//        Collections.sort(list, Comparator.comparing(item -> item));

//        Collections.sort(list, (a, b) -> -a.compareTo(b));
//        list.sort(Comparator.reverseOrder());
//        Collections.sort(list, Comparator.reverseOrder());
//        Collections.sort(list, Comparator.comparing(item -> item).reversed());
        Collections.sort(list, Comparator.comparing((String item) -> item).reversed());


        System.out.println(list);
    }
}
