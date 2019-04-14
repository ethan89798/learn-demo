package org.ethan.demo.jvm.ssy.d05;

/**
 * 虚拟机栈溢出
 *
 * -Xss160k 设置JVM栈的大小
 * -Xss设置最小要求160k,否则虚拟机启动不起来
 *
 * jvisualvm
 * jconsole
 *
 */
public class MyTest2 {

    private int length;

    public int getLength() {
        return length;
    }

    public void test() {
        length++;
        /*
        出现无限递归,会出现JVM栈溢出
         */
        test();
    }

    public static void main(String[] args) {
        MyTest2 test2 = new MyTest2();
        try {
            test2.test();
        } catch (Throwable ex) {
            System.out.println(test2.length);
            ex.printStackTrace();
        }
    }
}
