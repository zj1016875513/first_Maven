package com.atguigu.test;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class PhoneReducer extends Reducer <Text,Phone,Text,Phone>{
    @Override
    protected void reduce(Text key, Iterable<Phone> values, Context context) throws IOException, InterruptedException {
        int sumupdata=0;
        int sumdowndata=0;
        for (Phone value : values) {
            sumupdata+=value.getUpdata();
            sumdowndata+=value.getDowndata();
        }
        Phone rout =new Phone(sumupdata,sumdowndata);
        context.write(key,rout);
    }
}
