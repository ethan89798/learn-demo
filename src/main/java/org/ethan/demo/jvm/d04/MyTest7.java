package org.ethan.demo.jvm.d04;

import java.util.Date;

public class MyTest7 {
    public static void main(String[] args) {
        Animal animal = new Animal();
        Animal dog = new Dog();

        animal.test("");
        dog.test(new Date());
    }
}

class Animal {
    public void test(String string) {
        System.out.println("animal str");
    }

    public void test(Date date) {
        System.out.println("animal date");
    }
}

class Dog extends Animal {
    @Override
    public void test(String string) {
        System.out.println("dog str");
    }

    @Override
    public void test(Date date) {
        System.out.println("dog date");
    }
}