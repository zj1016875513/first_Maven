package com.atguigu.machineLearn;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.fpm.AssociationRules;
import org.apache.spark.mllib.fpm.FPGrowth;
import org.apache.spark.mllib.fpm.FPGrowthModel;

import java.util.Arrays;
import java.util.List;

public class FPDemo {
    public static void main(String[] args){
//        String data_path;       //数据集路径
        double minSupport = 0.2;//最小支持度   support=同时购买{X,Y}的人数 / 总人数
        int numPartition = 10;  //数据分区   confidence（X−>Y）=同时购买{X,Y}的人数 / 购买X的人数
        double minConfidence = 0.8;//最小置信度
//        if(args.length < 1){
//            System.out.println("<input data_path>");
//            System.exit(-1);
//        }
//        data_path = args[0];
//        if(args.length >= 2)
//            minSupport = Double.parseDouble(args[1]);
//        if(args.length >= 3)
//            numPartition = Integer.parseInt(args[2]);
//        if(args.length >= 4)
//            minConfidence = Double.parseDouble(args[3]);

        SparkConf conf = new SparkConf().setAppName("FPDemo").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        //加载数据，并将数据通过空格分割
        JavaRDD<List<String>> transactions = sc.textFile("Spark/src/main/resources/ml.txt")
                .map(s -> {
                    String[] parts = s.split(" ");
                    return Arrays.asList(parts);
                });

        //创建FPGrowth的算法实例，同时设置好训练时的最小支持度和数据分区
        FPGrowth fpGrowth = new FPGrowth().setMinSupport(minSupport).setNumPartitions(numPartition);
        FPGrowthModel<String> model = fpGrowth.run(transactions);//执行算法

        //查看所有频繁諅，并列出它出现的次数
        for(FPGrowth.FreqItemset<String> itemset : model.freqItemsets().toJavaRDD().collect())
            System.out.println("[" + itemset.javaItems() + "]," + itemset.freq());

        //通过置信度筛选出强规则
        //antecedent表示前项
        //consequent表示后项
        //confidence表示规则的置信度
        for(AssociationRules.Rule<String> rule : model.generateAssociationRules(minConfidence).toJavaRDD().collect())
            System.out.println(rule.javaAntecedent() + "=>" + rule.javaConsequent() + ", " + rule.confidence());
    }
}