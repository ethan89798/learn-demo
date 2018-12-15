package org.ethan.demo.jvm.ssy.d03;

public class TestLoader2 {
    public static void main(String[] args) {
        //根类加载器的目录
        System.out.println(System.getProperty("sun.boot.class.path"));
        //扩展类加载器的目录
        System.out.println(System.getProperty("java.ext.dirs"));
        //系统或应用类加载器的目录
        System.out.println(System.getProperty("java.class.path"));
    }
}
