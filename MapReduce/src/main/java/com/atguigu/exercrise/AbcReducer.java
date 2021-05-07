package com.atguigu.exercrise;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class AbcReducer extends Reducer<Text, IntWritable,Text,IntWritable> {
    IntWritable out =new IntWritable();
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int num=0;
        for (IntWritable value : values) {
            num+=value.get();
        }
        out.set(num);
        context.write(key,out);
    }
}
