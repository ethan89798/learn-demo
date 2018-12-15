package org.ethan.demo.jvm.ssy.d04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;

/**
 * 对于Java类中的每一个实例方法(非静态方法), 在编译后所生成的字节码中, 方法参数的数量总会比源代码中方法参数的数量多一个(this),
 * 它位于方法的第一个参数位置, 这样我们就可以在Java的实例中使用this来去访问当前对象的属性及其它方法.
 *
 * 这个操作在编译期间完成的,即由javac编译器在编译时, 将对this的访问转化为对一个普通实例方法的访问, 接下来在运行期间,
 * 由JVM在调用实例方法时,自动向实例方法传入this参数. 所以, 在实例方法的局部变量表中, 至少会有一个指向当前对象的局部变量.
 *
 * Java字节码对于异常的处理方式:
 * 1. 统一采用异常表的方式来对异常进行处理
 * 2. 在jdk1.4.2之前的版本,并不是使用异常表的方式来进行异常处理的,而是采用特定的指令方式
 * 3. 当异常处理存在finally语句块时,现代化的JVM采取的处理方式是将finally语句块的字节码拼接到每一个catch块后面
 *    即有多少catch块, 就有多少finally的重复
 */
public class ByteCodeExceptionTest {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        try {
            InputStream is = new FileInputStream("test.txxt");
            ServerSocket serverSocket = new ServerSocket(9999);
            serverSocket.accept();
        } catch (FileNotFoundException ffe) {

        } catch (IOException ie) {

        } catch (Exception e) {

        } finally {
            System.out.println("finally");
        }
    }

}
