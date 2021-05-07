package com.atguigu.teacher3.comparable2;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/*
    1.如果Mapper输出的Key的类型为自定义类型，那么该类必须实现WritableComparable。
    2.WritableComparable<T> 继承了 Writable和 Comparable<T>
    3.实现该接口后重写readFileds(),write(),compareTo()
 */
public class FlowBean implements WritableComparable<FlowBean> {
    private long sumFlow;
    private long upFlow;
    private long downFlow;


    public FlowBean(){

    }

    public FlowBean(long upFlow, long downFlow,long sumFlow) {
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        this.sumFlow = sumFlow;
    }
    /*
        用来排序的方法
     */
    public int compareTo(FlowBean o) {
        /*
            按照总流量进行排序
         */
        return Long.compare(this.sumFlow,o.sumFlow);
    }

    public void write(DataOutput out) throws IOException {
        out.writeLong(sumFlow);
        out.writeLong(upFlow);
        out.writeLong(downFlow);
    }


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
