package test

import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

class t1 {
  @Test
//1.创建SparkConf并设置App名称
val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

//2.创建SparkContext，该对象是提交Spark App的入口
val sc: SparkContext = new SparkContext(conf)


//4.关闭连接
sc.stop()

}
