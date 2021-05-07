package com.atguigu.exercrise;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Abcmapper extends Mapper <LongWritable, Text, Text, IntWritable>{

    private Text mkey = new Text();
    private IntWritable mvalue =new IntWritable();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String s = value.toString();
        String[] ss= s.split(" ");
        for (String s1 : ss) {
            mkey.set(s1);
            mvalue.set(1);
            context.write(mkey,mvalue);
        }
    }
}
