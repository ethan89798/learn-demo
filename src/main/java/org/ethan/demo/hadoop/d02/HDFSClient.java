package org.ethan.demo.hadoop.d02;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 创建hdfs文件目录
 */
public class HDFSClient {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
//        testMkdir();
//        testCopyFromLocalFile();
//        testCopyToLocalFile();
//        testDelete();
//        testRename();
//        testListFiles();
        testListStatus();
        System.out.println("finish");
    }

    /**
     * 创建目录
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    private static void testMkdir() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
//        conf.set("fs.defaultFS", "hdfs://192.218.21.204:9000");
        // 获取hdfs客户端对象
//        FileSystem fs = FileSystem.get(conf);
        FileSystem fs = FileSystem.get(new URI("hdfs://192.218.21.204:9000"), conf, "root");
        //在hdfs上创建路径
        fs.mkdirs(new Path("/ethan/client"));
        //关闭资源
        fs.close();
    }

    /**
     * 上传文件
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     */
    private static void testCopyFromLocalFile() throws IOException, InterruptedException, URISyntaxException {
        Configuration conf = new Configuration();
        // 设置副本数
        conf.set("dfs.replication", "2");
        URI uri = new URI("hdfs://192.218.21.204:9000");
        // 获取文件系统
        FileSystem fs = FileSystem.get(uri, conf, "root");

        // 上传文件
        fs.copyFromLocalFile(new Path("README.md"), new Path("/ethan/client/readme.md"));

        // 关闭资源
        fs.close();

        System.out.println("finish");
    }

    /**
     * 下载文件
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    private static void testCopyToLocalFile() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://192.218.21.204:9000"), conf, "root");
        fs.copyToLocalFile(new Path("/ethan/client/readme.md"), new Path("test.txt"));
        fs.close();
    }

    /**
     * 删除文件
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    private static void testDelete() throws IOException, URISyntaxException, InterruptedException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://192.218.21.204:9000"), conf, "root");
        fs.delete(new Path("/ethan/client/readme.md"), true);
        fs.close();
    }

    /**
     * 修改文件名
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    private static void testRename() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://192.218.21.204:9000"), conf, "root");
        fs.rename(new Path("/ethan/client"), new Path("/ethan/client2"));
        fs.close();
    }

    /**
     * 查看文件列表
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    private static void testListFiles() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://192.218.21.204:9000"), conf, "root");
        RemoteIterator<LocatedFileStatus> iter = fs.listFiles(new Path("/"), true);
        while (iter.hasNext()) {
            LocatedFileStatus fileStatus = iter.next();
            // 查看文件名称,权限,长度,块信息
            System.out.println(fileStatus.getPath().getName());
            System.out.println(fileStatus.getPermission());
            System.out.println(fileStatus.getLen());

            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            for (BlockLocation blockLocation : blockLocations) {
                String[] hosts = blockLocation.getHosts();
                for (String host : hosts) {
                    System.out.println(host);
                }
            }
            System.out.println("--------------");
        }
        fs.close();
    }

    /**
     * 判断是文件还是文件夹
     */
    private static void testListStatus() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://192.218.21.204:9000"), conf, "root");

        FileStatus[] fileStatuses = fs.listStatus(new Path("/"));

        for (FileStatus fileStatus : fileStatuses) {
            if (fileStatus.isFile()) {
                System.out.println("f: " + fileStatus.getPath().getName());
            } else {
                System.out.println("d: " + fileStatus.getPath().getName());
            }
        }
        fs.close();
    }
}
