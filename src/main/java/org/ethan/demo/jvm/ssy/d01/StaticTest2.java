package org.ethan.demo.jvm.ssy.d01;

/**
 * 常量在编译的阶段就会存入到调用这个常量的方法所在的类的常量池当中,
 * 本质上,调用类并没有直接引用到定义常量类,因此并不会触发定义常量类的初始化
 *
 * Note: 在这里指的常量在编译时已经将常量值存放到StaticTest2的常量池中,
 * 后面的调用这个常量再与Parent2再也没有关系了, 甚至我们可以删除这个Parent2
 * 这个class文件都是可以执行的
 *
 */
public class StaticTest2 {
    public static void main(String[] args) {
        System.out.println(Parent2.STR);
    }
}

class Parent2 {
    public static final String STR = "Hello World";

    static {
        System.out.println("Parent2 invoke static block");
    }
}
