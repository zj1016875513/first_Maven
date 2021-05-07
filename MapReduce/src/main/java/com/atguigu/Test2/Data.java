package com.atguigu.Test2;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Data implements WritableComparable {
    private int up;
    private int down;
    private int sum;

    public Data() {
    }

    public Data(int up, int down, int sum) {
        this.up = up;
        this.down = down;
        this.sum = sum;
    }

    public int compareTo(Object o) {
       Data d=(Data) o;
        return  this.sum-d.sum;
//        (this.sum>o.sum)?1:(this.sum==o.sum)?0:-1
    }

    public void write(DataOutput out) throws IOException {
        out.writeInt(up);
        out.writeInt(down);
        out.writeInt(sum);
    }

    public void readFields(DataInput in) throws IOException {
        up=in.readInt();
        down=in.readInt();
        sum=in.readInt();
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return up+" "+down+" "+sum;
    }

}
