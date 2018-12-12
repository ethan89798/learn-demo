package org.ethan.demo.hadoop.d02;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HDFSIo {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
//        testPutFileToHDFS();
//        testGetFileToLocal();

        //两块可以使用type part2 >> part1, 修改回文件名
//        testReadFileSeek1();
//        testReadFileSeed2();


        System.out.println("finish");
    }

    private static void testPutFileToHDFS() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://192.218.21.204:9000"), conf, "root");

        // 获取输入流
        FileInputStream fis = new FileInputStream(new File("README.md"));

        // 获取输出流
        FSDataOutputStream dos = fs.create(new Path("/ethan/client2/test.md"));

        // 流复制
        IOUtils.copyBytes(fis, dos, conf);

        // 关闭资源
        IOUtils.closeStream(dos);
        IOUtils.closeStream(fis);
        fs.close();
    }

    /**
     * 下载文件
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    private static void testGetFileToLocal() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://192.218.21.204:9000"), conf, "root");

        // 获取输出流
        FileOutputStream fos = new FileOutputStream(new File("test.txt"));

        // 获取hdfs的输入流
        FSDataInputStream dis = fs.open(new Path("/ethan/client2/test.md"));

        // 文件复制
        IOUtils.copyBytes(dis, fos, conf);

        // 关闭资源
        IOUtils.closeStream(fos);
        IOUtils.closeStream(dis);
        fs.close();
    }

    /**
     * 下载第一块
     */
    private static void testReadFileSeek1() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://192.218.21.204:9000"), conf, "root");

        FSDataInputStream dis = fs.open(new Path("/hadoop-2.7.7.tar.gz"));

        FileOutputStream fos = new FileOutputStream(new File("hadoop-2.7.7.tar.gz.part1"));

        // 流对拷, 只拷第一块
        byte[] buf = new byte[1024];
        for (int i = 0; i < 1024 * 128; i++) {
            dis.read(buf);
            fos.write(buf);
        }

        IOUtils.closeStream(fos);
        IOUtils.closeStream(dis);
        fs.close();
    }

    /**
     * 下载第二块
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    private static void testReadFileSeed2() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://192.218.21.204:9000"), conf, "root");

        FSDataInputStream fis = fs.open(new Path("/hadoop-2.7.7.tar.gz"));
        fis.seek(1024 * 1024 * 128);

        FileOutputStream fos = new FileOutputStream(new File("hadoop-2.7.7.tar.gz.part2"));

        IOUtils.copyBytes(fis, fos, conf);

        IOUtils.closeStream(fos);
        IOUtils.closeStream(fis);
        fs.close();
    }


}
