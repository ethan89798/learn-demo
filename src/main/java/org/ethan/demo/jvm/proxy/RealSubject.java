package org.ethan.demo.jvm.proxy;

/**
 * @author Ethan Huang
 * @since 2018-10-28 15:12
 */
public class RealSubject implements Subject {
    @Override
    public void sayHello(String str) {
        System.out.println("from real Subject");
    }
}
