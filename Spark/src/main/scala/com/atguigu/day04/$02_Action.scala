package com.atguigu.day04

import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

class $02_Action {

  val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))

  /**
    * reduce(func: (RDD元素类型,RDD元素类型) => RDD元素类型)
    * reduce的结果是scala的类型
    * reduce是针对整个rdd数据集聚合不是跟reduceByKey一样对key的所有value值聚合
    */
  @Test
  def reduce(): Unit = {

    val rdd = sc.parallelize(List(1,4,3,6,8,10,9))

    val result = rdd.reduce( (agg,curr)=> agg+curr )

    println(result)
  }

  /**
    * collect是收集rdd每个分区的数据之后用Array封装返回给Driver端  ********
    *     所以如果rdd分区的数据量比较大，而Driver内存比较小,会出现内存溢出,所以在工作中一般设置为5-10G
    * collect一般在工作中只用于广播的场景
    */
  @Test
  def collect():Unit = {
    val rdd = sc.parallelize(List(1,4,3,6,8,10,9))
    println(rdd.collect().toList)
  }

  /**
    * 获取rdd第一个元素
    */
  @Test
  def first():Unit = {
    val rdd = sc.parallelize(List(1,4,3,6,8,10,9))

    val result = rdd.first()

    println(result)
  }

  /**
    * 获取rdd前多少个数据
    */
  @Test
  def take():Unit = {
    val rdd = sc.parallelize(List(1,4,3,6,8,10,9))

    val result = rdd.take(3)

    println(result.toList)
  }

  /**
    * 排序之后取前三,默认升序
    */
  @Test
  def takeOrdered():Unit = {
    val rdd = sc.parallelize(List(1,4,3,6,8,10,9))

    val result = rdd.takeOrdered(3)

    println(result.toList)
  }

  @Test
  def aggregate():Unit = {
    val rdd = sc.parallelize(List(1,4,3,6,8,10,9),2)

   /* rdd.mapPartitionsWithIndex((index,it)=>{
      println(s"index:${index}  it:${it.toList}")
      it
    }).collect()*/
    //index:0  it:List(1, 4, 3)
    //    (agg:Int,curr:Int)=>agg+curr
    //      第一次计算: agg=zeroVlaue=10  curr=1 => 11
    // 18
    //index:1  it:List(6, 8, 10, 9)
    //   第一次计算: agg=zeroVlaue=10  curr=6 => 16
    //43
    // 后续会将两个分区的结果收集到Driver端再次聚合: zerovalue + 18 + 43
    val result = rdd.aggregate(10)((agg:Int,curr:Int)=>agg+curr,(agg:Int,curr:Int)=>agg+curr)
    //最终结果=(分区数+1)*zeroValue
    println(result)
  }
  //fold(zervalue)(func) = aggregate(zervalue,func,func)
  @Test
  def fold():Unit = {
    val rdd = sc.parallelize(List(1,4,3,6,8,10,9),2)

    val result = rdd.fold(10)((agg:Int,curr:Int)=>agg+curr)

    println(result)
  }

  /**
    * 统计每个key出现了多少次  ********
    *   countByKey一般是搭配sample使用判断哪些key出现了数据倾斜
    */
  @Test
  def countByKey():Unit = {

    val rdd = sc.parallelize(List( "aa"->1,"aa"->2,"aa"->3,"cc"->4,"aa"->5,"cc"->6 ))

    println(rdd.countByKey())
//    Map(aa -> 4, cc -> 2)
  }

  @Test
  def save():Unit = {
    val rdd = sc.parallelize(List( "aa"->1,"aa"->2,"aa"->3,"cc"->4,"aa"->5,"cc"->6 ))

    //rdd.saveAsTextFile("output/text")

    rdd.saveAsSequenceFile("output/seq")


  }

  //foreach是针对每个元素进行操作。里面的函数是有多少个元素就调用多少次
  //  foreach没有返回值
  @Test
  def foreach():Unit = {
    val rdd = sc.parallelize(List( "aa"->1,"aa"->2,"aa"->3,"cc"->4,"aa"->5,"cc"->6 ))
    rdd.foreach( x=>{
      println(x)
    } )
  }

  /**
    * foreachPartitions一般用于将数据写入mysql等地方  ********
    */
  @Test
  def foreachPartitions():Unit = {
    val rdd = sc.parallelize(List( "aa"->1,"aa"->2,"aa"->3,"cc"->4,"aa"->5,"cc"->6 ),2)

    rdd.foreachPartition(it=>{
      println("------------------------------")
      it.foreach(println(_))
    })
  }
}
