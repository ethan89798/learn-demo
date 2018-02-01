package org.ethan.demo.jdk8.d02;

/**
 * @author Ethan Huang
 * @since 2018-02-01 22:47
 */
public interface MyInterface1 {
    default void myMethod() {
        System.out.println("MyInterface1");
    }
}
