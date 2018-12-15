package org.ethan.demo.jvm.ssy.d01;

/**
 * @author Ethan Huang
 * @since 2018-02-15 23:24
 */
public class ArrayTest {
    public static void main(String[] args) {
//        Parent p = new Parent();
//        System.out.println("=======");
//        Parent p2 = new Parent();

        Parent[] arrry = new Parent[2];
        System.out.println(arrry.getClass());
        Parent[][] array = new Parent[1][2];
        System.out.println(arrry.getClass());
        System.out.println(arrry.getClass().getSuperclass());
        System.out.println(array.getClass());
        System.out.println(array.getClass().getSuperclass());

        int[] i = new int[1];
        System.out.println(i.getClass());
        char[] c = new char[1];
        System.out.println(c.getClass());
        boolean[] b = new boolean[1];
        System.out.println(b.getClass());
        short[] s = new short[1];
        System.out.println(s.getClass());
        long[] l = new long[1];
        System.out.println(l.getClass());
    }
}

class Parent {
    static {
        System.out.println("加载父类");
    }
}