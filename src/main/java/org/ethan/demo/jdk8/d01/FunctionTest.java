package org.ethan.demo.jdk8.d01;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 *
 */
public class FunctionTest {

    public static void main(String[] args) {
        FunctionTest test = new FunctionTest();
//        System.out.println(test.compute(1, item -> item + 1));
//        System.out.println(test.compute(2, item -> 2 * item));
//        System.out.println(test.compute(3, item -> item * item));
//
//        System.out.println(test.convert(5, item -> String.valueOf(item+" hello world")));

        // 在这里思考一下执行的结果是什么, 为什么会出现这种结果
        System.out.println(test.testFunction1(2, item -> item * 5, item -> item * item));
        System.out.println(test.testFunction2(2, item -> item * 5, item -> item * item));

        System.out.println("===============");

        System.out.println(test.testBiFunction(2, 3, (a, b) -> a + b));
        System.out.println(test.testBiFunction(2, 3, (a, b) -> a * b));
        System.out.println(test.testBiFunction(2, 3, (a, b) -> a / b));
        System.out.println(test.testBiFunction(2, 3, (a, b) -> a - b));

        System.out.println("===============");

        System.out.println(test.testBiFunction(2, 3, (a, b) -> a + b, a -> a * a));

    }

    public int testBiFunction(int a, int b, BiFunction<Integer, Integer, Integer> bi1, Function<Integer, Integer> f1) {
        return bi1.andThen(f1).apply(a, b);
    }

    public int testBiFunction(int a, int b, BiFunction<Integer, Integer, Integer> biFunction) {
        return biFunction.apply(a, b);
    }


    public int testFunction1(int a, Function<Integer, Integer> f1, Function<Integer, Integer> f2) {
        return f1.compose(f2).apply(a);
    }

    public int testFunction2(int a, Function<Integer, Integer> f1, Function<Integer, Integer> f2) {
        return f1.andThen(f2).apply(a);
    }


    public int compute(int a, Function<Integer, Integer> function) {
        return function.apply(a);
    }

    public String convert(int a, Function<Integer, String> function) {
        return function.apply(a);
    }
}
