package com.atguigu.teacher2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class RMapper extends Mapper<LongWritable, Text,OrderBean, NullWritable> {
    private String fileName;
    /**
        在map方法执行前执行该方法且只执行一次
     */
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //获取切片信息
        FileSplit inputSplit = (FileSplit) context.getInputSplit();
        //通过切片信息读取文件的名字
        fileName = inputSplit.getPath().getName();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1.将value变成String
        /*
            读取到的数据是不一样的
         */
        String line = value.toString();
        //2.将数据切割
        String[] pdInfo = line.split("\t");
        //3.封装key----判断当前的数据是哪个文件的
        OrderBean orderBean = new OrderBean();
        if ("pd.txt".equals(fileName)){
            orderBean.setPid(pdInfo[0]);
            orderBean.setPname(pdInfo[1]);
            //注意：对象中的字段不能为null。(因为要排序)
            orderBean.setId("");
            orderBean.setAmount("");
        }else if("order.txt".equals(fileName)){
            orderBean.setId(pdInfo[0]);
            orderBean.setPid(pdInfo[1]);
            orderBean.setAmount(pdInfo[2]);
            //注意：对象中的字段不能为null。(因为要排序)
            orderBean.setPname("");
        }
        //4.写出K，V
        context.write(orderBean,NullWritable.get());
    }

    /*
        在map方法执行完后执行该方法且只执行一次
     */
    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {

    }
}
