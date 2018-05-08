package org.ethan.demo.jvm.d03;

import java.sql.Driver;
import java.util.Iterator;
import java.util.ServiceLoader;

public class TestLoader4 {

    public static void main(String[] args) {
        ServiceLoader<Driver> loader = ServiceLoader.load(Driver.class);

        Iterator<Driver> iterator = loader.iterator();

        while (iterator.hasNext()) {

            Driver driver = iterator.next();

            System.out.println("driver class = " + driver.getClass() + ", loader = " + driver.getClass().getClassLoader());
        }

        System.out.println("currentThreadClassLoader = " + Thread.currentThread().getContextClassLoader());
        System.out.println("serviceLoader = " + ServiceLoader.class.getClassLoader());
    }
}
