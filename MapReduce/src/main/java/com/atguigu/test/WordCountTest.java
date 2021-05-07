package com.atguigu.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class WordCountTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf =new Configuration();
        Job job =Job.getInstance(conf);

        job.setJarByClass(WordCountTest.class);

        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.setInputPaths(job,new Path("D:\\io\\input1"));
        FileOutputFormat.setOutputPath(job,new Path("D:\\io\\output1"));

        job.waitForCompletion(true);
    }
}
