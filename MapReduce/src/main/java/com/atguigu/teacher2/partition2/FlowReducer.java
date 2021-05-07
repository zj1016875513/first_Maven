package com.atguigu.teacher2.partition2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FlowReducer extends Reducer<Text, FlowBean,Text, FlowBean> {

    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
        int sumUpFlow = 0;
        int sumDownFlow = 0;
        //遍历同一组数据（手机号相同）的Value
        for (FlowBean value : values) {
            //将上行流量和下行流量进行累加
            sumUpFlow += value.getUpFlow();
            sumDownFlow += value.getDownFlow();
        }
        //封装K,V
        FlowBean outValue = new FlowBean(sumUpFlow, sumDownFlow);
        //将k,v写出
        context.write(key,outValue);
    }
}
