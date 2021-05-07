package com.atguigu.teacher3.comparable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class CReducer extends Reducer<FlowBean, Text,Text,FlowBean> {
    /*
        将K,V顺序转换一下
     */
    @Override
    protected void reduce(FlowBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        //遍历values
        for (Text phoneNumber : values) {
            context.write(phoneNumber,key);
        }
    }
}
