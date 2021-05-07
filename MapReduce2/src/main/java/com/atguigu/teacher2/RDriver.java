package com.atguigu.teacher2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class RDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Job job = Job.getInstance(new Configuration());

        job.setJarByClass(RDriver.class);
        //设置自定义分组的类--默认分组方式和排序方式相同
        job.setGroupingComparatorClass(GroupCompartor.class);

        job.setMapperClass(RMapper.class);
        job.setReducerClass(RReducer.class);

        job.setMapOutputKeyClass(OrderBean.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setOutputKeyClass(OrderBean.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job,new Path("D:\\io\\input9"));
        FileOutputFormat.setOutputPath(job,new Path("D:\\io\\output9"));

        job.waitForCompletion(true);
    }
}
