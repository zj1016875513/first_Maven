package com.atguigu.teacher1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/*
     FileOutputFormat<K, V>  : 泛型的类型
        K: 在这是InputFormat的Key
        V: 在这是InputFormat的Value
 */
public class MyOutputFormat extends FileOutputFormat<LongWritable, Text> {
    /**
     * 获取RecordWriter的对象
     * @param job
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public RecordWriter<LongWritable, Text> getRecordWriter(TaskAttemptContext job) throws IOException, InterruptedException {
        return new MyRecordWriter(job);
    }
}
