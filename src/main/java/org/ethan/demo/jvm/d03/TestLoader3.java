package org.ethan.demo.jvm.d03;

import org.ethan.demo.jvm.d02.CustomizeClassLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestLoader3 {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        CustomizeClassLoader loader1 = new CustomizeClassLoader("loader1");
        loader1.setPath("/home/ethan/Desktop/");
        CustomizeClassLoader loader2 = new CustomizeClassLoader("loader2");
        loader2.setPath("/home/ethan/Desktop/");

        Class<?> clazz1 = loader1.loadClass("org.ethan.demo.jvm.d03.Person");
        Class<?> clazz2 = loader2.loadClass("org.ethan.demo.jvm.d03.Person");

        System.out.println(clazz1 == clazz2);

        Object object1 = clazz1.newInstance();
        Object object2 = clazz2.newInstance();

        Method method = clazz1.getMethod("setPerson", Object.class);
        method.invoke(object1, object2);
    }
}
