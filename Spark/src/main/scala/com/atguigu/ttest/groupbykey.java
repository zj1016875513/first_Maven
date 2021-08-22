package com.atguigu.ttest;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class groupbykey {
    public static void main(String[] args) {
//        JavaSparkContext jsc = new JavaSparkContext(new SparkConf().setMaster("local[4]").setAppName("groupbykey"));

        SparkSession sparkSession = SparkSession.builder().master("local[4]").appName("groupbykey").getOrCreate();
        JavaSparkContext jsc = new JavaSparkContext(sparkSession.sparkContext());
//        jsc.setLogLevel("INFO");

        ArrayList<Tuple2<String,Long>> list0 = new ArrayList<>();
        list0.add(new Tuple2<String,Long>("zhangsan",100L));
        list0.add(new Tuple2<String,Long>("lisi",100L));
        list0.add(new Tuple2<String,Long>("wangwu",90L));
        list0.add(new Tuple2<String,Long>("zhaoliu",80L));
        list0.add(new Tuple2<String,Long>("zhouqi",70L));
        list0.add(new Tuple2<String,Long>("zhangsan",50L));
        list0.add(new Tuple2<String,Long>("lisi",40L));

        List<Tuple2<String, Long>> list1 = jsc.parallelize(list0).mapToPair(x -> {
            return new Tuple2<String, Long>(x._1(), x._2());
        }).reduceByKey((a, b) -> (a + b)).sortByKey(true).collect();

        List<Tuple2<String, Long>> list2 = jsc.parallelize(list0).mapToPair(x -> {
            return new Tuple2<String, Long>(x._1(), x._2());
        }).groupByKey().mapToPair(x -> {
            Long sum = 0L;
            for (Long aLong : x._2) {
                sum = sum + aLong;
            }
            return new Tuple2<String, Long>(x._1(), sum);
        }).collect();

//        Collections.sort(list2, (o1, o2) -> Integer.parseInt(String.valueOf(o1._2-o2._2)));
//这一行报错的原因是类型为List 没有向下转为ArrayList

        List<Tuple2<String, Long>> ttt = new ArrayList<>(list2);
        Collections.sort(ttt, (o1, o2) -> Integer.parseInt(String.valueOf(o1._2-o2._2)));


        System.out.println(list1);
        System.out.println(ttt);


    }
}
