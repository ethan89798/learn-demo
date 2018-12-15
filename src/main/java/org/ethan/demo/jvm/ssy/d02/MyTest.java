package org.ethan.demo.jvm.ssy.d02;

/**
 *
 * 通过子类的引用调用父类的变量或方法时,并不会导致子类的初始化.
 * 但是子类的调用变量或方法时,会导致父类被初始化.而且这个类的所有父类都要坟初始化.
 * 所有的初始化都是在首次主动使用时进行的.
 * @author Ethan Huang
 * @since 2018-03-11 19:16
 */
public class MyTest {
    static {
        System.out.println("MyTest static invoked!");
    }
    public static void main(String[] args) {
        System.out.println(Child2.a);
        System.out.println("========");
        Child2.doSomething();
    }
}

class Parent2 {
    static int a = 2;
    static {
        System.out.println("Parent static invoked!");
    }

    static void doSomething() {
        System.out.println("doSomething invoked!");
    }
}

class Child2 extends Parent2 {
    static int b = 3;
    static {
        System.out.println("Child static invoked!");
    }
}
