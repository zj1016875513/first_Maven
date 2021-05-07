package com.atguigu.exercrise;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class LiuliangReaducer extends Reducer<Text,Liuliang,Text,Liuliang> {
    @Override
    protected void reduce(Text key, Iterable<Liuliang> values, Context context) throws IOException, InterruptedException {
        int sumup=0;
        int sumdown=0;
        for (Liuliang value : values) {
            sumup+=value.getUp();
            sumdown+=value.getDown();
        }
        Liuliang out =new Liuliang(sumup,sumdown);
        context.write(key,out);
    }
}
