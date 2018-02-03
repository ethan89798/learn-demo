package org.ethan.demo.jdk8.d01;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 */
public class StringComparator {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("zhangsan", "lisi", "wangwu", "hello");
        //这种方式是之前的排序
//        Collections.sort(names, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o2.compareTo(o1);
//            }
//        });

        //expression
        //statement

        //这种是JDK8提供Lambda方式, 可以简化代码并且容易理解
//        Collections.sort(names, (o1, o2) -> o2.compareTo(o1));

        Collections.sort(names, Comparator.reverseOrder());
    }
}
