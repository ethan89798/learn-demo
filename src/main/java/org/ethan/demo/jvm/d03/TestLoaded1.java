package org.ethan.demo.jvm.d03;

import org.ethan.demo.jvm.d02.CustomizeClassLoader;

public class TestLoaded1 {
    public static void main(String[] args) throws Exception {
        CustomizeClassLoader loader = new CustomizeClassLoader("load1");

        Class<?> clazz = loader.loadClass("org.ethan.demo.jvm.d03.MySimple");
        System.out.println(clazz.hashCode());

//        Object object = clazz.newInstance();
    }
}
