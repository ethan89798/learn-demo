package org.ethan.demo.jvm.d04;

/**
 * 方法的静态分派
 *
 * 方法重载是一种静态行为,在编译期就可以完全确定.
 *
 */
public class StaticCommandTest {

    // 方法重载, 是一种静态的行为
    public void test(Grandpa grandpa) {
        System.out.println("grandpa");
    }

    public void test(Father father) {
        System.out.println("father");
    }

    public void test(Son son) {
        System.out.println("son");
    }

    public static void main(String[] args) {
        // g1的静态类型是Grandpa, 而g1的实际类型(真正指向的类型)是Father
        // 变量的静态类型是不会变化的, 而变量的实际类型是可以发生变化的(多态的一种体现), 实际类型是在运行时可确定
        Grandpa g1 = new Father();
        Grandpa g2 = new Son();

        StaticCommandTest test = new StaticCommandTest();
        test.test(g1);
        test.test(g2);
    }
}

class Grandpa {

}

class Father extends Grandpa {

}

class Son extends Father {

}
