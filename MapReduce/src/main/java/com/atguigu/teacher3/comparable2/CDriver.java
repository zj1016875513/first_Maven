package com.atguigu.teacher3.comparable2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class CDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Job job = Job.getInstance(new Configuration());

        //设置自定义分区
        job.setPartitionerClass(MyPartitioner.class);
        //设置ReduceTask的数量
        job.setNumReduceTasks(5);

        job.setJarByClass(CDriver.class);
        job.setMapperClass(CMapper.class);
        job.setReducerClass(CReducer.class);
        job.setMapOutputKeyClass(FlowBean.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        FileInputFormat.setInputPaths(job,new Path("D:\\io\\input6"));
        FileOutputFormat.setOutputPath(job,new Path("D:\\io\\output11"));

        job.waitForCompletion(true);

    }
}
