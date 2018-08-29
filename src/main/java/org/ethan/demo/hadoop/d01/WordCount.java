package org.ethan.demo.hadoop.d01;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
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
 * 1. Map class
 * 2. Reduce class
 * 3. Driver, component job
 *
 * Map Reduce的模板代码
 */
public class WordCount {
    public static void main(String[] args) {
        try {
            run(args[0], args[1]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void run(String in, String  out) throws IOException, ClassNotFoundException, InterruptedException {

        //加载hadoop配置
        Configuration configuration = new Configuration();
        //创建一个Job任务
        Job job = Job.getInstance(configuration, WordCount.class.getName());
        //设置主类
        job.setJarByClass(WordCount.class);

        //设置输入目录
        FileInputFormat.addInputPath(job, new Path(in));
        //设置map操作类
        job.setMapperClass(WordCountJobMapper.class);
        //设置Map结果的key和value的类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //设置Reduce操作类
        job.setReducerClass(WordCountJobReducer.class);
        //设置输出目录
        FileOutputFormat.setOutputPath(job, new Path(out));

        //提交作业
        job.waitForCompletion(true);
    }
}

class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private static final Text outKey = new Text();
    private static final IntWritable outValue = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //TODO 写map逻辑
        StringTokenizer tokenizer = new StringTokenizer(value.toString());
        while (tokenizer.hasMoreTokens()) {
            String v = tokenizer.nextToken();
            outKey.set(v);
            context.write(outKey, outValue);
        }
    }
}

class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable outValue = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        // TODO 写reduce逻辑
        int sum = 0;

        Iterator<IntWritable> iterator = values.iterator();
        while (iterator.hasNext()) {
            sum += iterator.next().get();
        }
        outValue.set(sum);
        context.write(key, outValue);
    }
}

