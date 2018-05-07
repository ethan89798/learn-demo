package org.ethan.demo.jvm.d02;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * hotspot的实现根类加载器是用null来表示;
 * 数组并没有类加载器，而数组的类型是Java虚拟机在运行时动态创建的，
 * 并且这个类型的加载器和每一个数组元素的类型的类加载器是一样的，
 * 请注意这里的数组的类加载器与数组类型的类加载器，这两个并不是同一个东西。
 */
public class ClassLoaderTest2 {
    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        System.out.println(classLoader);
        while (classLoader != null) {
            classLoader = classLoader.getParent();
            System.out.println(classLoader);
        }
        System.out.println("=================");
        classLoader = Thread.currentThread().getContextClassLoader();
        String reourceName = "org/ethan/demo/jvm/d02/ClassLoaderTest2.class";
        Enumeration<URL> ruls = classLoader.getResources(reourceName);
        while (ruls.hasMoreElements()) {
            System.out.println(ruls.nextElement());
        }
        System.out.println("===========");
        classLoader = String.class.getClassLoader();
        System.out.println(classLoader);

        System.out.println("========");
        String[] strings = new String[1];
        System.out.println(strings.getClass().getClassLoader());

        System.out.println("============");
        ClassLoaderTest2[] classLoaderTest2s = new ClassLoaderTest2[1];
        System.out.println(classLoaderTest2s.getClass().getClassLoader());

        System.out.println("==============");
        int[] ints = new int[1];
        System.out.println(ints.getClass().getClassLoader());
    }
}
