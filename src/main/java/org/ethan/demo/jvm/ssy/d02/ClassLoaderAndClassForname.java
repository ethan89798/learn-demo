package org.ethan.demo.jvm.ssy.d02;

/**
 * 使用ClassLoader的loadClass方法加载类时,类的静态代码块并没有调用,因些,并不会导致类的主动使用,并不会导致类的初始化.
 * 但是使用Class.forName加载类时,类的静态代码块得到调用,因些导致了类的主动使用, 所以整个类已经被初始化.
 * @author Ethan Huang
 * @since 2018-03-11 19:24
 */
public class ClassLoaderAndClassForname {
    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        Class<?> clazz = loader.loadClass("org.ethan.demo.jvm.ssy.d02.CL");
        System.out.println(clazz);
        System.out.println("==========");
        clazz = Class.forName("org.ethan.demo.jvm.ssy.d02.CL");
        System.out.println(clazz);
    }
}

class CL {
    static {
        System.out.println("CL Class static invoked");
    }
}