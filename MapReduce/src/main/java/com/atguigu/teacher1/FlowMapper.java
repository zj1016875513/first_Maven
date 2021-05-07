package com.atguigu.teacher1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlowMapper extends Mapper<LongWritable, Text,Text,FlowBean> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1.将value转成String
        String line = value.toString();
        //2.切割数据
        String[] phoneInfo = line.split("\t");
        //3.封装K,V
        Text outKey = new Text(phoneInfo[1]);
        FlowBean outValue = new FlowBean(Long.parseLong(phoneInfo[phoneInfo.length - 3]),
                Long.parseLong(phoneInfo[phoneInfo.length - 2]));
        //4.写出K,V
        context.write(outKey,outValue);
    }
}
