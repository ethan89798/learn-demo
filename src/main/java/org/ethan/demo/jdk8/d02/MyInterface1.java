package org.ethan.demo.jdk8.d02;

/**
 *
 */
public interface MyInterface1 {
    default void myMethod() {
        System.out.println("MyInterface1");
    }
}
