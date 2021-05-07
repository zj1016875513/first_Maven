package com.atguigu.Test2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class DataReaducer extends Reducer<Data, Text,Text,Data> {
    Text outkey= new Text();
    @Override
    protected void reduce(Data key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for (Text tel : values) {
            outkey= tel;
            context.write(outkey,key);
        }
    }
}
