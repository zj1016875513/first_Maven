package com.atguigu.ttest;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class mappartitons_test2 {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[4]").setAppName("mappartiton");
        JavaSparkContext jsc = new JavaSparkContext(conf);

        ArrayList<Tuple2<String, Long>> result = new ArrayList<>();//

        String[] word ={"A","B","C","D","E"};
        List<String> collect = Stream.of(word).collect(Collectors.toList());
        for (String s : collect) {
            String path = "Spark/shuju/"+s+".txt";
            List<Tuple2<String, Long>> test2 = Count_List(jsc, path, s, result);
            result.addAll(test2);
        }
        jsc.parallelize(result).mapToPair(x->x).reduceByKey((a,b)->a+b).coalesce(1).saveAsTextFile("Spark/output/mapPartiton");
    }

    private static List<Tuple2<String, Long>> Count_List(JavaSparkContext jsc,String path,String s,ArrayList<Tuple2<String, Long>> result) {
        List<Tuple2<String, Long>> collectList = jsc.textFile(path).mapPartitions(it -> {
            ArrayList<Tuple2<String, Long>> res = new ArrayList<>();
            Long count = 0L;
            while (it.hasNext()) {
                String next = it.next();
                String[] split = next.split(" ");
                long num = Long.parseLong(split[1]);
                count += num;
            }
            res.add(new Tuple2<String, Long>(s, count));//计算完一个文件就生成一个元组放入临时集合res里
            return res.iterator(); //mapPartitions由迭代器计算后又返回一个新的迭代器
        }).collect();//把各task收集起来形成一个名为collectList的list
//        result.addAll(collectList);//将分区里所有的数据添加到全局集合result中
        return collectList;
    }
}
