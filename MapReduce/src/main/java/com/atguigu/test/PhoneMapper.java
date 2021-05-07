package com.atguigu.test;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class PhoneMapper extends Mapper <LongWritable,Text,Text,Phone> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String s = value.toString();
        String[] tel=s.split("\t");
        Text mkey=new Text(tel[1]);
        Phone mvalue=new Phone(Integer.parseInt(tel[tel.length-3]),Integer.parseInt(tel[tel.length-2]));
//        System.out.println(keyout);
//        System.out.println(valueout);
        context.write(mkey,mvalue);
    }
//    @Test
//    public void aa(){
//        String bb="123456";
//        System.out.println(Integer.parseInt(bb)+4);
////        System.out.println(Long.parseLong(bb));
//    }
}
