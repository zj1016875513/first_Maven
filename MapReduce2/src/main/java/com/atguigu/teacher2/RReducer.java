package com.atguigu.teacher2;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class RReducer extends Reducer<OrderBean, NullWritable,OrderBean,NullWritable> {

    /*
                key                          value
     null     1     null     小米             null
     1001   1     1 　 null                  null
     1004   1     4        null             null

     */
    @Override
    protected void reduce(OrderBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        //先获取第一条数据的key
        Iterator<NullWritable> iterator = values.iterator();
        iterator.next();
        //获取第一条数据key中的pname值
        String pname = key.getPname();

        //遍历之后所有的key并将Pname进行替换
        while(iterator.hasNext()){
            iterator.next();//指针下移指向下一条value,对应的key也会发生改变。
            //将key的pname进行替换
            key.setPname(pname);
            //写出k,v 注意从第二条数据开始写出
            context.write(key,NullWritable.get());
        }

    }
}
