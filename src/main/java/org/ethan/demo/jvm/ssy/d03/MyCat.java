package org.ethan.demo.jvm.ssy.d03;

public class MyCat {

    public MyCat() {
        System.out.println("MyCat is loaded by " + this.getClass().getClassLoader());
    }
}
