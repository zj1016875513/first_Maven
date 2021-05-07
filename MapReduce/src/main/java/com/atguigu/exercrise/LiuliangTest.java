package com.atguigu.exercrise;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class LiuliangTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration con =new Configuration();
        Job job= Job.getInstance(con);

        job.setJarByClass(LiuliangTest.class);

        job.setNumReduceTasks(5);
        job.setPartitionerClass(Liuliangpartions.class);

        job.setMapperClass(LiuliangMapper.class);
        job.setReducerClass(LiuliangReaducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Liuliang.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Liuliang.class);

        FileInputFormat.setInputPaths(job,new Path("D:\\io\\input2"));
        FileOutputFormat.setOutputPath(job,new Path("D:\\io\\output002"));

        job.waitForCompletion(true);
    }
}
