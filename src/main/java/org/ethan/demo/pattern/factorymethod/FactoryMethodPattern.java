package org.ethan.demo.pattern.factorymethod;

/**
 * 如果使用简单工厂模式,随着类型越来越多,会导致简单工厂的方法会不断增加,就违背了单一职责原则和开闭原则
 * 所以可以使用工厂方法模式进行优化,每个类型都增加一个工厂类,通过工厂类创建各自类型的实例,这样就可以回避了简单工厂模式
 * 的问题,增加类型时只需要增加相对应该类型和工厂就可以,无需要修改原有的类型和工厂.
 */
public class FactoryMethodPattern {
    public static void main(String[] args) {
        FruitFactory fruitFactory = new AppleFactory();
        Fruit fruit = fruitFactory.createFruit();
        fruit.show();
        fruitFactory = new BananaFactory();
        fruit = fruitFactory.createFruit();
        fruit.show();
    }
}
interface Fruit {
    void show();
}
class Apple implements Fruit {
    @Override
    public void show() {
        System.out.println("Apple");
    }
}
class Banana implements Fruit {
    @Override
    public void show() {
        System.out.println("Banana");
    }
}
interface FruitFactory {
    Fruit createFruit();
}
class AppleFactory implements FruitFactory {
    @Override
    public Fruit createFruit() {
        return new Apple();
    }
}
class BananaFactory implements FruitFactory {
    @Override
    public Fruit createFruit() {
        return new Banana();
    }
}