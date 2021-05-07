package com.atguigu.exercrise;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Liuliang implements Writable {
    private int up ;
    private int down ;
    private int sum ;

    public Liuliang() {
    }

    public Liuliang(int up, int down) {
        this.up = up;
        this.down = down;
        sum=up+down;
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
