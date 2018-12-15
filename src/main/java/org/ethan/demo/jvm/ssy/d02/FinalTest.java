package org.ethan.demo.jvm.ssy.d02;

import java.util.Random;

/**
 * 如果静态常量在编译时能够确定的值,那么这个静态常量会在使用这个常量的方法所有的类的静态常量池中,使用时并不会导致这个类的主动使用,
 * 更不会导致类的初始化,甚至可以将这个类的Class文件都可以删除.程序都可以正常运行的.
 * 但是这个静态常量在编译时并不能确定,要在运行时动态确定的话,那么使用这个静态常量就会导致类的主动使用,并且会导致类的初始化.
 * @author Ethan Huang
 * @since 2018-03-11 18:36
 */
public class FinalTest {
    public static void main(String[] args) {
        System.out.println(FinalDemo.x);
    }
}

class FinalDemo {
    //    public static final int x = 3;
    public static final int x = new Random().nextInt(3);
    static {
        System.out.println("FinalDemo");
    }
}