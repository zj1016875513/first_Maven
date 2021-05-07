package com.atguigu.test;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.junit.Test;

import java.io.IOException;

public class WordCountMapper extends Mapper<LongWritable, Text,Text, IntWritable> {
    private Text keyout =new Text();
    private IntWritable valueout=new IntWritable();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String s = value.toString();
        String[] ss= s.split(" ");
        for (String a : ss) {
            keyout.set(a);
            valueout.set(1);
            context.write(keyout,valueout);
        }
    }
}
