package org.ethan.demo.pattern.abstractfactory;

/**
 * 抽象工厂模式(Abstract Factory Pattern):
 * 提供一个创建一系列相关或相互依赖对象的接口,而无须指定它们具体类
 */
public class AbstractFactoryPattern {
    public static void main(String[] args) {
        FruitFactory factory = new AppleFactory();
        Fruit fruit = factory.createFruit();
        Wrapper wrapper = factory.createWrapper();

        factory = new BananaFactory();
        fruit = factory.createFruit();
        wrapper = factory.createWrapper();
    }
}
interface FruitFactory {
    Fruit createFruit();
    Wrapper createWrapper();
}
class AppleFactory implements FruitFactory {
    @Override
    public Fruit createFruit() {
        return new Apple();
    }
    @Override
    public Wrapper createWrapper() {
        return new AppleWrapper();
    }
}
class BananaFactory implements FruitFactory {
    @Override
    public Fruit createFruit() {
        return new Banana();
    }
    @Override
    public Wrapper createWrapper() {
        return new BananaWrapper();
    }
}
interface Fruit {
}
class Apple implements Fruit {
}
class Banana implements Fruit {
}
interface Wrapper {
}
class AppleWrapper implements Wrapper {
}
class BananaWrapper implements Wrapper {
}