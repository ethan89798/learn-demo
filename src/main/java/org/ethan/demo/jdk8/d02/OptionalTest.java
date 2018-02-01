package org.ethan.demo.jdk8.d02;

import java.util.Optional;

/**
 * Optional不要作为方法的参数或类的成员变量, 因为Optional设计上只是作为方法的返回值,并且没有实现序列化的,
 * Optional只作为方法的返回值使用.
 * @author Ethan Huang
 * @since 2018-01-28 22:34
 */
public class OptionalTest {
    public static void main(String[] args) {
        //有三种构造方法, of(),empty(),ofNullable()
        Optional<String> optional = Optional.ofNullable("Hello");

        //不建议这样写,因为这样与与之前判断为null是没有区别的
        if (optional.isPresent()) {
            System.out.println(optional.get());
        }

        System.out.println("===============");
        optional.ifPresent(System.out::println);

        //如果没有值就打印world
        System.out.println(optional.orElse("World"));
        //
        System.out.println(optional.orElseGet(() -> "Nihao"));
    }

    public void testOptional(Optional optional) {

    }
}
