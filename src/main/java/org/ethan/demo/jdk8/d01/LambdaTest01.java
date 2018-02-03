package org.ethan.demo.jdk8.d01;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class LambdaTest01 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        //最早的循环方式
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        //后来改善的循环方式
        for (Integer integer : list) {
            System.out.println(integer);
        }
        //JDK8提供的lambda表达式进行循环
        list.forEach(System.out::println);
        //这里的i没有类型,因为JDK可以进行类型推断出这个i是Integer类型
        list.forEach(i -> System.out.println(i));
        //这里使用的时方法引用来创建一个函数式接口的实例
        list.forEach(System.out::println);
    }
}
