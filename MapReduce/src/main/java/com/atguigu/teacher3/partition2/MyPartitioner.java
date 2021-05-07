package com.atguigu.teacher3.partition2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/*
    Partitioner<KEY, VALUE>
    KEY : Mapper输出的Key的类型
    VALUE : Mapper输出的Value的类型
 */
public class MyPartitioner extends Partitioner<Text,FlowBean> {
    /**
     * 手机号136、137、138、139开头都分别放到一个独立的4个文件中，其他开头的放到一个文件中。
     * 获取分区号
     * @param text key(电话号码)
     * @param flowBean value(流量的对象)
     * @param numPartitions reduceTask的数量
     * @return
     */
    public int getPartition(Text text, FlowBean flowBean, int numPartitions) {
        String phoneNumber = text.toString();
        //判断手机号需要的分区
        if(phoneNumber.startsWith("136")){
            return 0;
        }else if(phoneNumber.startsWith("137")){
            return 1;
        }else if(phoneNumber.startsWith("138")){
            return 2;
        }else if(phoneNumber.startsWith("139")){
            return 3;
        }else{
            return 4;
        }

    }
}
