package com.atguigu.teacher3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MDriver {
    public static void main(String[] args) throws IOException, URISyntaxException, ClassNotFoundException, InterruptedException {
        Job job = Job.getInstance(new Configuration());

        job.setJarByClass(MDriver.class);
        job.setMapperClass(MMapper.class);
        job.setMapOutputKeyClass(com.atguigu.mapjoin.OrderBean.class);
        job.setMapOutputValueClass(NullWritable.class);

        //因为没有Reducer所以最终输出的K,V就是Mapper的K,V
        job.setOutputKeyClass(com.atguigu.mapjoin.OrderBean.class);
        job.setOutputValueClass(NullWritable.class);
        //如果不使用Reducer可以将ReduceTask的数量设置为0
        job.setNumReduceTasks(0);//如果没有ReduceTask那么不会排序（不走shuffle）。
        //添加缓存路径
        job.addCacheFile(new URI("file:///D:/io/input9/pd.txt"));


        FileInputFormat.setInputPaths(job,new Path("D:\\io\\input9\\order.txt"));
        FileOutputFormat.setOutputPath(job,new Path("D:\\io\\output999"));
        job.waitForCompletion(true);
    }
}
