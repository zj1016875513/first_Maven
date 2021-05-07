package com.atguigu.teacher3.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/*
    在本地向集群上提交Job
    1.在Configuration设置参数
         //设置在集群运行的相关参数-设置HDFS,NAMENODE的地址
        conf.set("fs.defaultFS", "hdfs://hadoop102:9820");
        //指定MR运行在Yarn上
        conf.set("mapreduce.framework.name","yarn");
        //指定MR可以在远程集群运行
        conf.set(
                "mapreduce.app-submission.cross-platform","true");
        //指定yarn resourcemanager的位置
        conf.set("yarn.resourcemanager.hostname",
                "hadoop103");
     2.打jar包
     3.将job.setJarByClass(WCDriver3.class);注释掉
        设置jar的路径：job.setJar("jar的路径")
     4.在Configurations中配置如下：
            VMOptions : -DHADOOP_USER_NAME=atguigu
            Program Arguments : hdfs://hadoop102:9820/input hdfs://hadoop102:9820/output2
     5.右键run执行
 */
public class WCDriver3 {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //1.创建一个Job对象
        Configuration conf = new Configuration();
        //设置在集群运行的相关参数-设置HDFS,NAMENODE的地址
        conf.set("fs.defaultFS", "hdfs://hadoop102:9820");
        //指定MR运行在Yarn上
        conf.set("mapreduce.framework.name","yarn");
        //指定MR可以在远程集群运行
        conf.set(
                "mapreduce.app-submission.cross-platform","true");
        //指定yarn resourcemanager的位置
        conf.set("yarn.resourcemanager.hostname",
                "hadoop103");


        Job job = Job.getInstance(conf);

        //2.给Job对象赋值参数
        //2.1设置jar加载路径
        //job.setJarByClass(WCDriver3.class);//如果是在本地执行可以不加该参数
        job.setJar("D:\\class_video\\1130\\04-hadoop\\3.代码\\MRDemo\\target\\MRDemo-1.0-SNAPSHOT.jar");


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
        FileInputFormat.setInputPaths(job,new Path(args[0]));
        //注意：输出的目录必须不存在
        FileOutputFormat.setOutputPath(job,new Path(args[1]));

        //3.执行job
        //返回值：如果为true则表执行成功
        //参数 ：如果为true则打印执行的过程
        job.waitForCompletion(true);
    }
}
