package org.ethan.demo.jvm.d04;

/**
 * 主要分析class的字节码, 程序不重要
 */
public class ByteCodeTest2 {

    String str = "Hello";

    private int x = 5;

    public static Integer in = 10;

    public ByteCodeTest2() {
    }

    public ByteCodeTest2(int x) {
    }

    public void setX(int x) {
        this.x = x;
    }

    public synchronized void test() {

    }

    public void test2() {
        synchronized (this) {
            System.out.println("aaaa");
        }
    }

    public static void main(String[] args) {
        ByteCodeTest2 test2 = new ByteCodeTest2();
        test2.setX(8);
        in = 20;
    }
}
