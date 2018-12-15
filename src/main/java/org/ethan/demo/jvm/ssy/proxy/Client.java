package org.ethan.demo.jvm.ssy.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 
 */
public class Client {

    public static void main(String[] args) {
        // 生成class代理类在我们的本地磁盘
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");


        RealSubject rs = new RealSubject();
        InvocationHandler ih = new DynamicSubject(rs);
        Class<?> clazz = rs.getClass();

        Subject proxySubject = (Subject) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), ih);

        proxySubject.sayHello("");

        System.out.println(proxySubject.getClass());
        System.out.println(proxySubject.getClass().getSuperclass());
    }

}
