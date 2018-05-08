package org.ethan.demo.jvm.d03;

import com.sun.crypto.provider.AESKeyGenerator;
import org.ethan.demo.jvm.d02.CustomizeClassLoader;

public class TestLoaded1 {
    public static void main(String[] args) throws Exception {
        CustomizeClassLoader loader = new CustomizeClassLoader("load1");

//        Class<?> clazz = loader.loadClass("org.ethan.demo.jvm.d03.MySimple");
//        System.out.println(clazz.hashCode());
        /*
        注释这一行代码并不会导致类的主动使用，所以并不会导致类的初始化。
         */
//        Object object = clazz.newInstance();


        //把这个类复制到根类加载器要加载的目录, 这样这个类就会被根类加载器所加载
        Class<?> clazz = loader.loadClass("org.ethan.demo.jvm.d03.MyCat");
        System.out.println(clazz.hashCode());
        clazz.newInstance();

        AESKeyGenerator aesKeyGenerator = new AESKeyGenerator();
        System.out.println(aesKeyGenerator.getClass().getClassLoader());
        System.out.println(TestLoaded1.class.getClassLoader());
    }
}
