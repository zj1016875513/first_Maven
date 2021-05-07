package com.atguigu.teacher1;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/*
    Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
        第一组：
            KEYIN ：读取的数据的偏移量的类型
            VALUEIN ：读取的数据的类型（一行一行的数据）
        第二组：
            KEYOUT ：输出的key的类型（单词的类型）
            VALUEOUT ：输出的value的类型 （单词数量的类型）
 */
public class WCMapper extends Mapper<LongWritable, Text,Text, IntWritable> {

    //封装的key
    private Text outKey = new Text();
    //封装的value
    private IntWritable outValue = new IntWritable();

    /**
     * 在map方法中去实现需要在mapTask中执行的功能
     * @param key 读取数据的偏移量 就是当前单词在整个文本中的位置（从开头到现在是多少个了）
     * @param value 一行一行的数据  指读的一整行的数据
     * @param context 上下文在这用来将k,v写出去
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1.将数据切割
        //1.1将value转成String
        String line = value.toString();
//        System.out.println(line);
//        System.out.println(key);
        String[] words = line.split(" ");

        //2.封装K,V  （把读到的这一行的字符串按空格分割成单词，然后编成数组，然后每个单词赋给outkey，然后每个outvalue赋值1）
        for (String word : words) {
            //2.1封装的key
            System.out.println("**********");
            System.out.println(word);
            System.out.println("**********");
            outKey.set(word);
            //2.2封装的value
            outValue.set(1);
            //3.将K,V写出(封装一个K,V就写出去一个)
            context.write(outKey,outValue);
        }

    }
}
