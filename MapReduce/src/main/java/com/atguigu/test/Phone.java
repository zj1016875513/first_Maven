package com.atguigu.test;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Phone implements Writable {
    private int updata;
    private int downdata;
    private int sumdata;

    public Phone() {
    }

    public Phone(int updata, int downdata) {
        this.updata = updata;
        this.downdata = downdata;
        sumdata=updata+downdata;
    }

    public void write(DataOutput out) throws IOException {
        out.writeInt(updata);
        out.writeInt(downdata);
        out.writeInt(sumdata);
    }

    public void readFields(DataInput in) throws IOException {
        updata=in.readInt();
        downdata=in.readInt();
        sumdata=in.readInt();
    }

    @Override
    public String toString() {
        return updata+" "+downdata+" "+sumdata;
    }

    public int getSumdata() {
        return sumdata;
    }

    public void setSumdata(int sumdata) {
        this.sumdata = sumdata;
    }

    public int getUpdata() {
        return updata;
    }

    public void setUpdata(int updata) {
        this.updata = updata;
    }

    public int getDowndata() {
        return downdata;
    }

    public void setDowndata(int downdata) {
        this.downdata = downdata;
    }
}
