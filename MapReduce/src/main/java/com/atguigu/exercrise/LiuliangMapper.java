package com.atguigu.exercrise;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class LiuliangMapper extends Mapper<LongWritable,Text, Text,Liuliang> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String s = value.toString();
        String[] ss =s.split("\t");
        Text mkey=new Text(ss[1]);
        Liuliang mvalue =new Liuliang(Integer.parseInt(ss[ss.length-3]),Integer.parseInt(ss[ss.length-2]));
        context.write(mkey,mvalue);
    }
}
