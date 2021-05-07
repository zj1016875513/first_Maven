package com.atguigu.teacher1;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/*
    Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
    第一组：
        KEYIN ： 输入的Key的类型（Mapper输出的Key的类型）
        VALUEIN ：输入的Value的类型（Mapper输出的Value的类型）

    第二组：
        KEYOUT： 输出的Key的类型
        VALUEOUT： 输出的Value的类型
 */
public class WCReducer extends Reducer<Text, IntWritable,Text,IntWritable> {
    private IntWritable outValue = new IntWritable();
    /**
     * 在reduce方法中实现需要在ReduceTask中实现的功能
     * @param key 输入的key            即每个单词
     * @param values 输入的所有value    即每个单词每一行中出现的次数
     * @param context 上下文在这用来写出key和value
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;//用来将所有的value进行累加
        //1.遍历所有value
        for (IntWritable value : values) {
            //2.将所有的value进行累加
            sum+=value.get();
            System.out.println("key="+key.toString());
            System.out.println("sum="+sum);
        }
        System.out.println("=========");
        //3.封装K,V
        outValue.set(sum);
        //4.写出K,V
        context.write(key,outValue);
    }
}
