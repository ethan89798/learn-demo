package org.ethan.demo.jdk8.d01;

/**
 * @author Ethan Huang
 * @since 2018-01-20 22:21
 */
public class LambdaTest02 {

    public static void main(String[] args) {
        LambdaTest02 test = new LambdaTest02();
        test.myTest(() -> {
            System.out.println("MyTest");
        });

        MyInterface myInterface = () -> System.out.println("My Test2");
        test.myTest(myInterface);

        System.out.println(myInterface.getClass());
        //因为这个对象默认是Object的子类,所以这个对象就实现了接口中的toString()方法,故JDK还是认为这个接口是函数式接口
        System.out.println(myInterface.getClass().getSuperclass());

    }

    public void myTest(MyInterface myInterface) {
        System.out.println("1111");
        myInterface.test();
        System.out.println("2222");
    }
}

@FunctionalInterface
interface MyInterface {
    void test();
    String toString();
}
