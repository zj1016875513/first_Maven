package com.atguigu.day08

import org.apache.spark.sql.SparkSession

object Test {

  def main(args: Array[String]): Unit = {

    //1、创建SparkSession
    val spark = SparkSession.builder().master("local[4]").appName("test").getOrCreate()
    //2、读取数据
    spark.read.option("sep","\t").csv("sqldatas/user_visit_action.txt")
      .toDF("date","user_id","session_id","page_id","action_time","search_keyword","click_category_id","click_product_id","order_category_ids","order_product_ids","pay_category_ids","pay_product_ids","city_id")
      .filter("click_product_id!=-1")
      .createOrReplaceTempView("user_visit_action")

    spark.read.option("sep","\t").csv("sqldatas/city_info.txt")
      .toDF("city_id","city_name","area")
      .createOrReplaceTempView("city_info")

    spark.read.option("sep","\t").csv("sqldatas/product_info.txt")
      .toDF("product_id","product_name","extend_info")
      .createOrReplaceTempView("product_info")

    //注册udaf
    val productUDAF = new ProductUDAF
    import org.apache.spark.sql.functions._
    spark.udf.register("city_market",udaf(productUDAF))
    //3、用户 join 城市 join 产品//4、分组统计
    spark.sql(
      """
        |select b.area,c.product_name,count(1) num,city_market(b.city_name)
        |     from user_visit_action a join city_info b
        |     on a.city_id = b.city_id
        |     join product_info c
        |     on a.click_product_id = c.product_id
        |     group by b.area,c.product_name
      """.stripMargin).show

    //5、结果展示
  }
}
