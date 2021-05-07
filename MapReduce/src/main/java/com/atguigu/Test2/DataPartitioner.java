package com.atguigu.Test2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class DataPartitioner extends Partitioner<Data, Text> {
    @Override
    public int getPartition(Data data, Text text, int i) {
        String tel = text.toString();
        if (tel.startsWith("136")){
            return 0;
        }else if(tel.startsWith("137")){
            return 1;
        }else if(tel.startsWith("138")){
            return 2;
        }else if (tel.startsWith("139")){
            return 3;
        }else{
            return 4;
        }
    }
}
