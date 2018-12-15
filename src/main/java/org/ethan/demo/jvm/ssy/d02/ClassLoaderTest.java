package org.ethan.demo.jvm.ssy.d02;

/**
 * @author Ethan Huang
 * @since 2018-03-11 17:55
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> clazz = Class.forName("java.lang.String");
        System.out.println(clazz.getClassLoader());

        Class<?> clazz2 = Class.forName("org.ethan.demo.jvm.ssy.d02.C");
        //使用应用类加载器或系统类加载器
        System.out.println(clazz2.getClassLoader());
    }
}

class C {

}