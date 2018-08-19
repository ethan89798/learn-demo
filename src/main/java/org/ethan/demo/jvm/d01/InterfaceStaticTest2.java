package org.ethan.demo.jvm.d01;

/**
 * 当一个接口初始化时，并不会导致父接口初始化
 */
public class InterfaceStaticTest2 {
    public static void main(String[] args) {
//        System.out.println(MyInterfaceChild.thread);
        /*
         * 当一个接口初始化时，并不会导致它的父接口进行了初始化
         */
//        System.out.println(MyInterfaceChild.thread2);
//        new C();

        /*
         * 当一个类初始化时，并不会导致所实现在类进行初始化
         */
        System.out.println(MyClassChild.a);

    }
}

/**
 * 代码块与构造方法，每创建一个类都会执行一次代码块的代码，且代码块的代码比构造方法的代码先执行
 * 静态代码块只会执行一次，就是类加载的时候会执行
 */
class C {
    public C() {
        System.out.println("invoked C()");
    }

    {
        System.out.println("C block");
    }
}

interface MyInterfaceParent {
    Thread thread = new Thread(){
        {
            System.out.println("MyInterfaceParent thread init");
        }
    };

}

interface MyInterfaceChild extends MyInterfaceParent {
    Thread thread2 = new Thread(){
        {
            System.out.println("MyInterfaceChild thread init");
        }
    };
}

class MyClassChild implements MyInterfaceParent {
    public static int a = 5;
    static {
        System.out.println("MyClassChild invoked");
    }
}
