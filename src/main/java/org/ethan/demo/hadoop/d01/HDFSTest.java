package org.ethan.demo.hadoop.d01;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author Ethan Huang
 * @since 2018/08/29
 */
public class HDFSTest {

    public static void main(String[] args) {

        //读取HDFS文件
        Configuration config = new Configuration();
        Path path = new Path("/user/bing/mapreduce/input/wcinput/input.wc");
        FSDataInputStream inputStream = null;
        try {
            FileSystem fs = FileSystem.get(config);
            inputStream = fs.open(path);
            IOUtils.copyBytes(inputStream, System.out, 1024, false);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流处理
            IOUtils.closeStream(inputStream);
        }
        System.out.println("======================");



        //上传HDFS文件
        FileInputStream fis = null;
        FSDataOutputStream dos = null;
        try {
            FileSystem outfs = FileSystem.get(config);
            dos = outfs.create(new Path("/user/bing/mapreduce/input/wcinput/build.gradle"));
            fis = new FileInputStream(new File("build.gradle"));
            IOUtils.copyBytes(fis, dos, 1024, false);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            IOUtils.closeStream(fis);
            IOUtils.closeStream(dos);
        }

        System.out.println("上传成功");
    }
}
