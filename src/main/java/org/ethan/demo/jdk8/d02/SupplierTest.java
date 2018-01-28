package org.ethan.demo.jdk8.d02;

import java.util.function.Supplier;

/**
 *
 * @author Ethan Huang
 * @since 2018-01-25 20:06
 */
public class SupplierTest {
    public static void main(String[] args) {
        Supplier<String> supplier = () -> "Hello world";
        System.out.println(supplier.get());

        System.out.println("============");

        //通过构造方法进行创建函数式对象
        Supplier<String> supplier1 = String::new;
        System.out.println(supplier1.get());
    }
}
