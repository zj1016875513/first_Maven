package test


import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.junit.Test

class DataFrameCreate {

  private val spark: SparkSession = SparkSession.builder().master("local[4]").appName("dataframecreate").getOrCreate()

  @Test
  def fromfiletoDF: Unit ={
    val df = spark.read.option("sep"," ").csv("datas/agent.log")
    val df2 = df.toDF("时间", "1", "2", "3", "4")

    df.show()
    df2.show()
  }

  @Test
  def fromother ={

    val rdd = spark.sparkContext.parallelize(
      List(
      Row(1, "zhangsan", 20),
      Row(2, "lisi", 30),
      Row(3, "wangwu", 40),
      Row(4, "zhaoliu", 50),
      Row(5, "zhouqi", 60))
    )
    val arr = Array(StructField("id", IntegerType), StructField("name", StringType), StructField("age", IntegerType))
    val aa = StructType(arr)
    spark.createDataFrame(rdd,aa).show()
  }

  import spark.implicits._

  @Test
  def sql={
    val list1 = List((1, "zhangsan", 20),
                      (2, "lisi", 30),
                      (3, "wangwu", 40),
                      (4, "zhaoliu", 50),
                      (5, "zhouqi", 60))
    val ll = list1.toDF("id","name","age")
    ll.createOrReplaceTempView("person")

    spark.sql(
      """select * from person""".stripMargin
    ).show
  }

  @Test
  def dsl={
    val list1 = List((1, "zhangsan", 20),
                    (2, "lisi", 30),
      (3, "wangwu", 40),
      (3, "wangwu", 70),
                    (4, "zhaoliu", 50),
                    (5, "zhouqi", 60))
    val ll = list1.toDF("id","name","age")
    ll.filter("age>25").select('*).distinct().show()
  }

  @Test
  def toset={
    val list1 = List((1, "zhangsan", 20),
      (2, "lisi", 30),
      (3, "wangwu", 40),
      (4, "zhaoliu", 50),
      (5, "zhouqi", 60))
    val ds1 = list1.toDS()
    ds1.show

    val rdd = spark.sparkContext.parallelize(list1)

    val ds2 = rdd.toDS()

    ds2.show()
  }


  @Test
  def createDataSetfromfile={
    val aa = spark.read.textFile("datas/agent.log")
    val bb = aa.flatMap(_.split(" "))
    bb.createOrReplaceTempView("wordcount")

    spark.sql(
      "select value as words,count(value) as num from wordcount group by value".stripMargin
    ).show()

  }
}
