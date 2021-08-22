package com.atguigu.day07

import org.apache.spark.sql.{Dataset, Row, SparkSession}

object $08_DataFrameAndDataSet {

  /**
    * DataFrame与DataSet的区别:
    *     1、DataFrame是弱类型<不管每一行是什么类型,表现出来都是Row类型>
    *       DataSet是强类型<每一行是什么类型表现出来就是什么类型>
    *     2、DataFrame是运行期安全，DataSet是编译器和运行期都安全
    * DataFrame与DataSet使用场景:
    *     1、如果是rdd转换sparksql编程，同时rdd里面的数据类型是样例类,此时随便转DataFrame与DataSet都可以
    *       同时rdd里面的数据类型是元组,此时推荐通过toDF转成DataFrame,同时可以通过toDF指定列名
    *     2、如果想要使用map、flatMap这种写函数的强类型算子，此时推荐使用DataSet
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().master("local[4]").appName("test").getOrCreate()
    import spark.implicits._
    val rdd = spark.sparkContext.parallelize(List( Person("zhangsan",20),Person("lisi",30) ))
    val rdd2 = spark.sparkContext.parallelize(List( ("zhangsan",20),("lisi",30) ))
    // DataFrame是弱类型<不管每一行是什么类型,表现出来都是Row类型>
    val df:Dataset[Row] = rdd.toDF
    val df2:Dataset[Row] = rdd2.toDF
    // DataSet是强类型<每一行是什么类型表现出来就是什么类型>
    val ds = rdd.toDS()
    val ds2 = rdd2.toDS()

    //DataFrame是运行期安全,编译器不安全
    //运行报错: address列不存在
    //df.selectExpr("name","age","address").show()

    //运行报错: address列不存在
    //df.map(x=> x.getAs[String]("address")).show()
    //DataSet是编译器和运行期都安全
    //编译报错: 没有address属性
    //ds.map(x=>x.address)
  }
}
