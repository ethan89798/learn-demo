package org.ethan.demo.jvm.d03;

public class MySimple {
    public MySimple() {
        System.out.println("MySimple is loaded is " + this.getClass().getClassLoader());
        new MyCat();
    }
}
