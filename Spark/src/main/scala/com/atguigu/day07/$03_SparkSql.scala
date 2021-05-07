package com.atguigu.day07

import org.apache.spark.sql.SparkSession
import org.junit.Test

class $03_SparkSql {

  val spark = SparkSession.builder().master("local[4]").appName("test").getOrCreate()

  import spark.implicits._

  /**
    * sparksql有两种编程风格:
    *     1、sql方式: 通过写sql语句操作数据
    *         1、注册成表:
    *             createOrReplaceTempView : 注册成临时表<只能在当前sparksession中使用,使用的时候直接使用表名即可>,如果临时表以及存在则替换
    *             createOrReplaceGlobalTempView : 注册成公共表<是多个SparkSession都可以使用,使用的时候是通过 global_temp.表名 的方式使用>,如果公共表以及存在则替换
    *         2、通过spark.sql() 写sql代码
    *     2、DSL方式: 通过map、flatMap..算子操作数据
    */
  @Test
  def sqlMethod(): Unit ={

    val list = List(
      Person("zhangsan",20),
      Person("lisi",30),
      Person("wangwu",40),
      Person("zhaoliu",100),
      Person("hanmeimei",18)
    )

    val df = list.toDF()

    //将结果集注册成表
    //注册成临时表,只能在当前SparkSession中使用,其他sparkSession使用不了
    df.createOrReplaceTempView("person") //工作常用
//    df.createOrReplaceGlobalTempView("person")

    spark.sql(
      """
        | select
        |   age
        |     from person where age>25
      """.stripMargin)//.show

    val session = spark.newSession()
    session.sql(
      """
        | select
        |   age
        |     from global_temp.person where age>25
      """.stripMargin).show
  }

  @Test
  def dsl(): Unit ={
    val list = List(
      Person("zhangsan",20),
      Person("lisi",30),
      Person("wangwu",40),
      Person("wangwu",25),
      Person("zhaoliu",100),
      Person("hanmeimei",18)
    )

    val df = list.toDF()

    df.filter("age>25")
      .select('age)//.show()

    //常用dsl api  [过滤、去重、列裁剪]
    //过滤[filter、where]
    df.filter(" age > 25 ")//.show

    //去重
    //distinct去重: 所有列的值都相同，这两行数据才算重复
    //
    df.distinct()//.show
    //dropDuplicates去重: 只要指定列的数据相同,这两行数据才算重复
    df.dropDuplicates("name")//.show

    //列裁剪
    //df.selectExpr("name","age").show()
    df.selectExpr("sum(age)  sum_age")//.show()

    import org.apache.spark.sql.functions._
    df.select(sum('age)).show
  }
}
