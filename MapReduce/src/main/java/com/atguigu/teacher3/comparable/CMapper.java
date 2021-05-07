package com.atguigu.teacher3.comparable;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class CMapper extends Mapper<LongWritable, Text,FlowBean,Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //将value转成String
        String line = value.toString();
        //切割数据
        String[] phoneInfo = line.split(" ");
        //封装K,V
        Text outValue = new Text(phoneInfo[0]);
        FlowBean outKey = new FlowBean(Long.parseLong(phoneInfo[1]), Long.parseLong(phoneInfo[2])
                , Long.parseLong(phoneInfo[3]));
        //写出K,V
        context.write(outKey,outValue);
    }
}
