package com.atguigu.teacher2;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class GroupCompartor extends WritableComparator {

    //1.构造器
    public GroupCompartor(){
        /*
        WritableComparator(Class<? extends WritableComparable> keyClass,
                boolean createInstances)
           第1个参数：分组中排序对象的类的运行时类的实例
           第2个参数 ：是否创建对象的实例
         */
        //调用父类的构造器
        super(OrderBean.class,true);
    }

    //2.重写分组排序的方法 --- 按照pid分组
    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        if (a instanceof OrderBean && b instanceof OrderBean){
            //向下转型
            OrderBean oa = (OrderBean) a;
            OrderBean ob = (OrderBean) b;
            return oa.getPid().compareTo(ob.getPid());
        }
        return 0;
    }
}
