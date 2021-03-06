package com.atguigu.test;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class PhoneTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
            Job job=Job.getInstance(new Configuration());

            job.setJarByClass(PhoneTest.class);
            job.setMapperClass(PhoneMapper.class);
            job.setReducerClass(PhoneReducer.class);

            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Phone.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Phone.class);

            FileInputFormat.setInputPaths(job,new Path("D:\\io\\input2"));
            FileOutputFormat.setOutputPath(job,new Path("D:\\io\\output3"));

            job.waitForCompletion(true);
    }
}
