package com.atguigu.teacher3.partition2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class FlowDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Job job = Job.getInstance(new Configuration());


        //设置ReduceTask的数量
        /*
            注意：
                ReduceTask的数量 < 分区的数量 （不对会抛异常）
                ReduceTask的数量 > 分区的数量 (可以但是浪费ReduceTask的资源)
                ReduceTask的数量 = 分区的数量
         */
        job.setNumReduceTasks(5);
        //设置分区类
        job.setPartitionerClass(MyPartitioner.class);

        job.setJarByClass(FlowDriver.class);
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);
        FileInputFormat.setInputPaths(job,new Path("D:\\io\\input2"));
        FileOutputFormat.setOutputPath(job,new Path("D:\\io\\output2"));


        job.waitForCompletion(true);
    }
}
