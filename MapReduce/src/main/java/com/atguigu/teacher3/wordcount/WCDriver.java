package com.atguigu.teacher3.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class WCDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //1.创建一个Job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //2.给Job对象赋值参数
        //2.1设置jar加载路径
        job.setJarByClass(WCDriver.class);//如果是在本地执行可以不加该参数
        //2.2设置Mapper和Reducer
        job.setMapperClass(WCMapper.class);
        job.setReducerClass(WCReducer.class);
        //2.3设置Mapper输出的K,V类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //2.4设置最终输出的K,V类型（如果有Reducer那就是Redcuer的k,v如果没有那就是Mapper的k,v）
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //2.5设置输入和输出的路径
        FileInputFormat.setInputPaths(job,new Path("D:\\io\\input"));
        //注意：输出的目录必须不存在
        FileOutputFormat.setOutputPath(job,new Path("D:\\io\\output33"));

        //3.执行job
        //返回值：如果为true则表执行成功
        //参数 ：如果为true则打印执行的过程
        job.waitForCompletion(true);
    }
}
