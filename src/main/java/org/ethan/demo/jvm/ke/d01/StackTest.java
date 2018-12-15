package org.ethan.demo.jvm.ke.d01;

/**
 * 设置参数 -Xss256k 设置栈大小
 * java.lang.StackOverflowError
 *  一般出现在递归的情况比较多.
 *  递归与非递归,如果在树的实现时,递归代码简洁,但非递归的代码复杂但性能高
 *  方法的参数会影响栈深度,参数越多深度越浅.
 */
public class StackTest {

    private int stackLength = 0;

    public void test(int i, String bb, String dd) {
        stackLength++;
        test(i, bb, dd);
    }

    public static void main(String[] args) {
        StackTest test = new StackTest();
        try {
            test.test(1, "", "");
        } catch (Throwable throwable) {
            System.out.println("stack length = " + test.stackLength);
            throwable.printStackTrace();
        }
    }
}
