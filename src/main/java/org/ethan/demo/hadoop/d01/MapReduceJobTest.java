package org.ethan.demo.hadoop.d01;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * @author Ethan Huang
 * @since 2018/08/29
 */
public class MapReduceJobTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args[0] == null || args[1] == null) {
            System.out.println("input or output directory is empty");
        }

        Configuration config = new Configuration();

        Path inPath = new Path(args[0]);
        Path outPath = new Path(args[1]);

        Job job = Job.getInstance(config, "wordcount-job");
        job.setJarByClass(MapReduceJobTest.class);

        FileInputFormat.addInputPath(job, inPath);
        job.setMapperClass(WordCountMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //设置排序
//        job.setSortComparatorClass();
        //设置分区
//        job.setPartitionerClass();
        //设置压缩
//        job.setCombinerClass();

        job.setReducerClass(WordCountReducer.class);
        FileOutputFormat.setOutputPath(job, outPath);

        job.waitForCompletion(true);

    }
}

class WordCountMapper extends Mapper<Text, IntWritable, Text, IntWritable> {
    private static final IntWritable WRITABLE = new IntWritable(1);
    @Override
    protected void map(Text key, IntWritable value, Context context) throws IOException, InterruptedException {
        StringTokenizer tokenizer = new StringTokenizer(key.toString());
        while (tokenizer.hasMoreTokens()) {
            String k = tokenizer.nextToken();
            context.write(new Text(k), WRITABLE);
        }
    }
}

class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        Iterator<IntWritable> iterator = values.iterator();
        while (iterator.hasNext()) {
            sum += iterator.next().get();
        }
        context.write(key, new IntWritable(sum));
    }
}
