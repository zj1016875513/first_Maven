package com.atguigu.teacher3;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;

public class MMapper extends Mapper<LongWritable, Text, com.atguigu.mapjoin.OrderBean, NullWritable> {
    //创建一个map集合用来缓存数据
    private HashMap<String,String> map = new HashMap<>();
    /*
        只执行一次。在map方法执行前执行。
        在这的作用 ：用来缓存pd.txt中的内容
     */
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //创建文件系统对象
        FileSystem fs = FileSystem.get(context.getConfiguration());
        //创建流
        //1.获取缓存路径
        URI[] cacheFiles = context.getCacheFiles();
        //2.创建流
        FSDataInputStream fis = fs.open(new Path(cacheFiles[0]));
        //3.将字节流转成字符缓冲流 ： 字节流 ----> 字符流 ----> 字符缓冲流
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
        //4.读取数据
        String str="";
        while((str = br.readLine()) != null){
            //将字符串进行切割
            String[] info = str.split("\t");
            //5.将数据缓存起来（使用map缓存）
            map.put(info[0],info[1]);
        }
        //5.关流
        IOUtils.closeStream(br);
        IOUtils.closeStream(fis);
    }

    /*
            读取的文件是Order.txt
            1001	1	1
            1002	2	2
            1003	3	3
         */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] orderInfo = line.split("\t");
        //封装K,V
        // OrderBean(String pid, String id, String amount, String pname)
        com.atguigu.mapjoin.OrderBean outkey =
                new com.atguigu.mapjoin.OrderBean(orderInfo[1],orderInfo[0],orderInfo[2],map.get(orderInfo[1]));
        context.write(outkey,NullWritable.get());
    }
}
