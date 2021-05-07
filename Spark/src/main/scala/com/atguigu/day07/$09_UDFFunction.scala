package com.atguigu.day07

import org.apache.spark.sql.SparkSession

object $09_UDFFunction {

  /**
    * 自定义Udf函数：
    *     1、定义一个函数
    *     2、注册
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().master("local[4]").appName("test").getOrCreate()

    import spark.implicits._
    val df = List(
      ("1001","zhangsan"),
      ("000108","lisi"),
      ("00112","wangwu"),
      ("300","zhaoliu"),
      ("004","hanmeimei")
    ).toDF("id","name")

    df.createOrReplaceTempView("person")


    //员工编号因为一些原因导致不全，正常应该是八位，现在将不满8位的员工编号不全，在前面用0补全

    //注册udf函数
    spark.udf.register("prefixid",func)

    df.selectExpr("prefixid(id) id","name").show()
  }

  //定义udf函数
  val func = (id:String) => {
    //补全的长度
    val len = 8 - id.length

    s"${"0"*len}${id}"
  }
}
