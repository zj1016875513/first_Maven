package com.atguigu.exercrise;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;
public class Liuliangpartions extends Partitioner<Text,Liuliang> {
    @Override
    public int getPartition(Text text, Liuliang liuliang, int fenqu) {
        String s = text.toString();
        if(s.startsWith("136")){
            return 0;
        }else if(s.startsWith("137")){
            return 1;
        }else if(s.startsWith("138")){
            return 2;
        }else if(s.startsWith("139")){
            return 3;
        }else {
            return 4;
        }
    }
}