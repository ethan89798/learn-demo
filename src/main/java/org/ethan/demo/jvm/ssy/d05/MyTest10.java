package org.ethan.demo.jvm.ssy.d05;

import java.util.ArrayList;
import java.util.List;

public class MyTest10 {

    public static void main(String[] args) {
        List<MyTest10> list = new ArrayList<>();
        for (; ; ) {
            list.add(new MyTest10());
            System.gc();
        }
    }
}
