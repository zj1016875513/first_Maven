package com.atguigu.day06

import org.apache.spark.{SparkConf, SparkContext}

object $02_BroadCast {

  /**
    * 广播变量:
    *     好处:
    *       1、减少所有task都需要的共同数据的空间占用
    *       2、能够一定程度上减少shuffle操作
    *     场景:
    *       1、在算子中需要使用Driver定义的数据的时候
    *       2、大表 join 小表的时候
    *     使用:
    *       1、广播数据: val bc = sc.broadcast(数据)
    *       2、在task中取出广播数据: bc.value
    *
    * @param args
    */
  /*def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))

    val rdd = sc.parallelize(List("jd","alibaba","tx","sf"))

    val map = Map("jd"->"www.jd.com","alibaba"->"www.alibaba.com","tx"->"www.tx.com","sf"->"www.sf.com")
    //广播数据
    val bc = sc.broadcast(map)

    val rdd2 = rdd.map(x=> {
      //取出广播变量的值
      bc.value.getOrElse(x,"")
    })

    println(rdd2.collect().toList)

  }*/

  //通过广播变量减少shuffle操作
  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))

    val studentsRdd = sc.parallelize(List(
      ("1","zhangsan","1001"),
      ("2","lisi","1002"),
      ("3","wangwu","1001"),
      ("4","zhaoliu","1002"),
      ("5","hanmeimei","1003")
    ))

    val classRdd = sc.parallelize(List(
      ("1001","大数据班"),
      ("1002","java班"),
      ("1003","python班"),
      ("1004","c#班")
    ))

    //获取学生的详细信息和所在班级名称
    val bc = sc.broadcast(classRdd.collect().toMap)
     studentsRdd.map(x=>{
       val className = bc.value.getOrElse(x._3,"")
       (x._1,x._2,className)
     }).foreach(println(_))
    /*studentsRdd.map(x=> {
      val clazz = bc.value.filter(y=> y._1 == x._3).first()
      (x._1,x._2,clazz._2)
    }).foreach(println(_))*/
    /*val stuRdd = studentsRdd.map{
      case (id,name,classid) => (classid,(id,name))
    }

    val infoRdd = stuRdd.join(classRdd)

    infoRdd.map{
      case (classId,((id,name),className)) => (id,name,className)
    }.foreach(println(_))

    Thread.sleep(1000000)*/

  }


}
