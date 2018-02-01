package org.ethan.demo.jdk8.d02;

/**
 * @author Ethan Huang
 * @since 2018-02-01 22:48
 */
public interface MyInterface2 {
    default void myMethod() {
        System.out.println("MyInterface2");
    }
}
