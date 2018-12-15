package org.ethan.demo.jvm.ssy.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Ethan Huang
 * @since 2018-10-28 15:13
 */
public class DynamicSubject implements InvocationHandler {

    private Subject subject;

    public DynamicSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("befor calling: " + method);

        Object result = method.invoke(subject, args);

        System.out.println("after calling: " + method);

        return result;
    }
}
