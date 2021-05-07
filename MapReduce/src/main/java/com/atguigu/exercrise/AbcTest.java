package com.atguigu.exercrise;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class AbcTest{
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration con = new Configuration();
        Job job = Job.getInstance(con);

        job.setJarByClass(AbcTest.class);
        job.setMapperClass(Abcmapper.class);
        job.setReducerClass(AbcReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.setInputPaths(job,new Path("D:\\io\\input1"));
        FileOutputFormat.setOutputPath(job,new Path("D:\\io\\output01"));

        job.waitForCompletion(true);
    }
}
