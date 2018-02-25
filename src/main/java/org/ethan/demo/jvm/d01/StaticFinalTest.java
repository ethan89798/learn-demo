package org.ethan.demo.jvm.d01;

import java.util.UUID;

/**
 * 静态变量能否在编译时确定，如果不能在编译时被确定，那么这个常量就不会放到调用类的常量池里
 * ，这样我们在使用时也会导致类的初始化。
 */
public class StaticFinalTest {
    public static void main(String[] args) {
        System.out.println(Parent3.STR);
    }
}

class Parent3 {
    public static final String STR = UUID.randomUUID().toString();
    static {
        System.out.println("Parent2 static block invoke!");
    }
}
