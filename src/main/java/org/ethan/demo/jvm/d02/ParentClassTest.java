package org.ethan.demo.jvm.d02;

/**
 * 调用子类的方法或变量时,会导致这个类的所有父类都要被初始化.
 * @author Ethan Huang
 * @since 2018-03-11 18:47
 */
public class ParentClassTest {

    static {
        System.out.println("ParentClassTest class invoked!");
    }

    public static void main(String[] args) {
        System.out.println(Child.b);
    }
}

class MyParent {
    static int a = 2;
    static  {
        System.out.println("MyParent class invoked!");
    }
}

class Child extends MyParent {
    static int b = 3;
    static {
        System.out.println("Child class invoked!");
    }
}