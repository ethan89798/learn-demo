package org.ethan.demo.jvm.ssy.d05;

/**
 * jmap <option> PID
 * 	-heap 打印java堆内存的汇总信息
 * 	-clstats 打印java的类加载器
 * 	-histo 打印堆中类的内存实例数量和字节数(-histo:live打印存活的)
 * 	-finalizerinfo 打印堆中等待垃圾回收的对象
 * 	-dump:<dump-option> 以hprof的二进制格式存储堆信息
 * 		live 只存储存活的对象
 * 		format=b 二进制格式
 * 		file=<file> 存储在文件
 *
 * jstat <option> PID <interval-时间间隔> <count-打印次数> 查看元空间信息
 * 	-gc 打印元空间的信息, MC(当前分配元空间的大小),MU(已被使用的元空间大小)
 * 	-gcutil 打印元空间的GC统计,各个空间的百分比
 * 	-gccapacity 打印空间的容量
 * 	-gcnewcapacity 打印年轻代的占用情况
 * 	-gcoldcapacity 打印老年代的占用情况
 * 	...
 *
 * jcmd PID <option>
 * 	VM.flags 查看JVM的启动参数
 * 	help 列出当前运行的Java进程可以执行的操作
 * 	help JFR.dump 查看具体命令选项
 * 	PerfCounter.print 查看JVM性能相关参数
 * 	VM.uptime 查看JVM的启动时长
 * 	GC.class_histogram 查看系统中类的统计信息
 * 	Thread.print 查看线程堆栈信息(与命令jstack相同)
 * 	GC.heap_dump 查看JVM的堆信息(与jmap命令相同),可以通过jvisualvm查看
 * 	VM.system_properties 查看JVM的属性信息
 *
 */
public class JStatTest {
    public static void main(String[] args) {
        for (; ; ) {
            System.out.println("hello");
        }
    }
}
