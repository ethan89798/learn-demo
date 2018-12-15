package org.ethan.demo.jvm.ssy.d01;

import java.util.Random;

/**
 * 当一个接口在初始化时, 并不要求其父类接口都完成了初始化;
 * 只有在真正使用到该接口时才会导致接口的初始化(如引用接口定义的变量使用).
 */
public class InterfaceStaticTest {
    public static void main(String[] args) {
//        System.out.println(MyClildInterface.a);
//        System.out.println(MyParentInterface.a);
        System.out.println(MyClildInterface.b);
    }
}

interface MyParentInterface {
//    public static int a = 5;
    public static int a = new Random().nextInt(4);
}

interface MyClildInterface extends MyParentInterface {
//    public static int b = new Random().nextInt(3);
    public static int b = 3;
}