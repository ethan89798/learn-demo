package org.ethan.demo.pattern.simplefactory;

public class SimpleFactoryPattern {

    /**
     * 方式一:
     *   简单,但是使用者要对源码或实现有一定的了解,并且要清楚知道哪个类型所对应该的对象是什么
     * @param type
     * @return
     */
    public static final int type_apple = 1;
    public static final int type_banana = 2;
    public static Fruit getFuirt(int type) {
        switch (type) {
            case type_apple:
                return new Apple();
            case type_banana:
                return new Banana();
            default:
                break;
        }
        return null;
    }

    /**
     * 方式二:
     *   简单易用,只需要作简单的了解就可以使用
     *   减少逻辑判断
     * @return
     */
    public static Fruit getApple() {
        return new Apple();
    }
    public static Fruit getBanana() {
        return new Banana();
    }
}

interface Fruit {
}

class Apple implements Fruit {
}

class Banana implements Fruit {
}