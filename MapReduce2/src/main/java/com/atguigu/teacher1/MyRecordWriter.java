package com.atguigu.teacher1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/*
    RecordWriter<K, V>
    K： OutputFormat的key
    V : OutputFormat的Value
 */
public class MyRecordWriter extends RecordWriter<LongWritable, Text> {
    private FSDataOutputStream atguiguIO;
    private FSDataOutputStream otherIO;
    /*
        构造器的作用 ：创建流
     */
    public MyRecordWriter(TaskAttemptContext job){
        try {
            //创建文件对象
            FileSystem fs = FileSystem.get(job.getConfiguration());
            //创建流
            Path outputPath = FileOutputFormat.getOutputPath(job);
            atguiguIO =
                    fs.create(new Path(outputPath, "atguigu.txt"));
            otherIO =
                    fs.create(new Path(outputPath, "other.txt"));
        } catch (IOException e) {
            e.printStackTrace();
            IOUtils.closeStream(atguiguIO);
            IOUtils.closeStream(otherIO);
            //终止程序
            throw new RuntimeException("xxxxx");
        }
    }
    /**
     * 写数据的
     * @param key 偏移量
     * @param value 数据
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void write(LongWritable key, Text value) throws IOException, InterruptedException {
        //1.将value转成String
        String webAddress = value.toString()+"\n";
        //2.判断内容中是否包含了atguigu
        if (webAddress.contains("atguigu")){//写到atguigu.txt
            atguiguIO.write(webAddress.getBytes());
        }else{//写到other.txt
            otherIO.write(webAddress.getBytes());
        }
    }

    /**
     * 关闭资源的方法
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
        IOUtils.closeStream(atguiguIO);
        IOUtils.closeStream(otherIO);
    }
}
