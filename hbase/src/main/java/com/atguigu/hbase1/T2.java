package com.atguigu.hbase1;

public class T2 {
    private char qqq[];

    public T2() {
        this.qqq=(new T2()).qqq;   // 类似String源码中的这句话 this.value = "".value;
    }
}
