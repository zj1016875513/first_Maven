package com.atguigu.mapjoin;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class OrderBean implements WritableComparable<OrderBean> {
    private String pid;
    private String id;
    private String amount;
    private String pname;

    public OrderBean(){

    }

    public OrderBean(String pid, String id, String amount, String pname) {
        this.pid = pid;
        this.id = id;
        this.amount = amount;
        this.pname = pname;
    }

    /*
        排序 ：先按照pid排序再按照pname排序
     */
    @Override
    public int compareTo(OrderBean o) {
        int sortPid = this.pid.compareTo(o.pid);
        if (sortPid == 0){
            return -this.pname.compareTo(o.pname);
        }
        return sortPid;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(pid);
        out.writeUTF(id);
        out.writeUTF(amount);
        out.writeUTF(pname);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        pid = in.readUTF();
        id = in.readUTF();
        amount = in.readUTF();
        pname = in.readUTF();
    }
    @Override
    public String toString() {
        return id + " " + pname + " " + amount;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }


}
