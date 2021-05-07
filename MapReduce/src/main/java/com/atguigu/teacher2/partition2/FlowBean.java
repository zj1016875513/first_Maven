package com.atguigu.teacher2.partition2;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/*
    Hadoop序列化：
        1.自定义一个类并实现Writable接口
        2.重写write和readFileds方法
        3.write是序列化时需要调用的方法。readFiles是反序列化时需要调用的方法
        4.反序列化的顺序和序列化时的顺序必须保持一致。
 */
public class FlowBean implements Writable {
    private long sumFlow;
    private long upFlow;
    private long downFlow;

    /*
        如果是JavaBean必须有一个空参构造器
     */
    public FlowBean(){

    }

    public FlowBean(long upFlow, long downFlow) {
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        sumFlow = upFlow + downFlow;
    }

    /*
            序列化时需要调用的方法
         */
    public void write(DataOutput out) throws IOException {
        out.writeLong(sumFlow);
        out.writeLong(upFlow);
        out.writeLong(downFlow);
    }

    /*
        反序列化时需要调用的方法
        注意：反序列化时的顺序必须会序列化时的顺序保持一致
     */
    public void readFields(DataInput in) throws IOException {
        sumFlow = in.readLong();
        upFlow = in.readLong();
        downFlow = in.readLong();
    }

    /*
        当Reducer将对象写出去以后。实际上文件上的内容是该对象toString方法的内容。
     */
    @Override
    public String toString() {
        return upFlow + " " + downFlow + " " + sumFlow;
    }

    public long getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(long sumFlow) {
        this.sumFlow = sumFlow;
    }

    public long getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(long upFlow) {
        this.upFlow = upFlow;
    }

    public long getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(long downFlow) {
        this.downFlow = downFlow;
    }


}
