package org.ethan.demo.hadoop.d01;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.*;

public class HdfsTest {

    public static void main(String[] args) throws IOException {

        Configuration configuration = new Configuration();

        FileSystem fileSystem = FileSystem.get(configuration);

        String fileName = "/user/ethan/mapreduce/input/wcinput/input.txt";

        Path path = new Path(fileName);

        FSDataInputStream inputStream = fileSystem.open(path);

        try {
            IOUtils.copyBytes(inputStream, System.out, 512, false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(inputStream);
        }


        System.out.println("=========================================");

        fileName = "/user/ethan/mapreduce/input/wcinput/build.gradle";

        Path output = new Path(fileName);

        FSDataOutputStream outputStream = fileSystem.create(output);

        DataInputStream dis = new DataInputStream(new FileInputStream(new File("build.gradle")));

        try {
            IOUtils.copyBytes(dis, outputStream, 1024, false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(outputStream);
            IOUtils.closeStream(dis);
        }



    }



}