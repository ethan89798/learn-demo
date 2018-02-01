package org.ethan.demo.jdk8.d02;

/**
 * @author Ethan Huang
 * @since 2018-02-01 22:52
 */
public class DefaultMethodTest2 extends MyInterface1Impl implements MyInterface2 {

    /**
     * 这里不用重写myMethod方法,因为编译器认为父类的方法比接口的默认方法的优先级别要高,
     * 因些不重写只会调用父类的myMethod方法.
     */
    public static void main(String[] args) {
        DefaultMethodTest2 test2 = new DefaultMethodTest2();
        test2.myMethod();
    }
}
