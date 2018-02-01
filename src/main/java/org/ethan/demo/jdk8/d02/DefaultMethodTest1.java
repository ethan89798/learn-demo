package org.ethan.demo.jdk8.d02;

/**
 * @author Ethan Huang
 * @since 2018-02-01 22:50
 */
public class DefaultMethodTest1 implements MyInterface1, MyInterface2 {
    /**
     * 该方法必须重写,不重写编译不通过,因为不知道哪个默认方法才是真正要调用的方法
     * @author Ethan Huang
     * @since 2018-02-01
     */
    @Override
    public void myMethod() {
        MyInterface1.super.myMethod();
        System.out.println("DefaultMethodTest1");
        MyInterface2.super.myMethod();
    }

    public static void main(String[] args) {
        DefaultMethodTest1 test1 = new DefaultMethodTest1();
        test1.myMethod();
    }
}
