package com.atguigu.Test2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class DataTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration con = new Configuration();
        Job job = Job.getInstance(con);

        job.setPartitionerClass(DataPartitioner.class);
        job.setNumReduceTasks(5);

        job.setJarByClass(DataTest.class);
        job.setMapperClass(DataMapper.class);
        job.setReducerClass(DataReaducer.class);
        job.setMapOutputKeyClass(Data.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Data.class);
        FileInputFormat.setInputPaths(job,new Path("D:\\io\\input01"));
        FileOutputFormat.setOutputPath(job,new Path("D:\\io\\Output01"));

        job.waitForCompletion(true);
    }
}
