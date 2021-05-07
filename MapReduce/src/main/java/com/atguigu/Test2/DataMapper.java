package com.atguigu.Test2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class DataMapper extends Mapper<LongWritable,Text,Data,Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String s = value.toString();
        String[] s1 = s.split(" ");
            Text mvalue=new Text(s1[0]);
            Data mkey=new Data(Integer.parseInt(s1[1]),Integer.parseInt(s1[2]),Integer.parseInt(s1[3]));
            context.write(mkey,mvalue);
    }
}
