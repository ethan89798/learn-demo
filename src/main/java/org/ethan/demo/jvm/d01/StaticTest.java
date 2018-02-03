package org.ethan.demo.jvm.d01;

/**
 * 对于静态字段来说,只有直接定义了该字段的类才会被初始化;
 * 当一个类在初始化时,要求其继承的全部父类都已经初始化完毕了
 *
 * -XX:+TraceClassLoading, 用于追踪类的加载信息并打印出来
 *
 * 对于JVM的参数设置,一般有以下规律:
 * -XX:+<option>, 表示开启option选项
 * -XX:-<option>, 表示关闭option选项
 * -XX:<option>=<value>, 表示将option选项的值设置为value
 *
 */
public class StaticTest {

    public static void main(String[] args) {
        /*
        对于静态变量来说,只有直接定义该变量的类才会被初始化.
         */
        System.out.println(Child1.str);
        /*
        如果该类静态变量被调用时,初始化该类之前,必须初始化该类所继承的全部父类
         */
//        System.out.println(Child1.str2);
    }
}

class Parent1 {
    public static String str = "hello world";
    static {
        System.out.println("parent invoke block");
    }
}

class Child1 extends Parent1 {
    public static String str2 = "welcome";
    static {
        System.out.println("child1 invoke block");
    }
}
