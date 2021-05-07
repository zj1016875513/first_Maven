package com.atguigu.exercrise.first

import scala.collection.TraversableOnce.MonadOps
import scala.io.Source

object e6_nong_chan_ping {
  def main(args: Array[String]): Unit = {
    val province = Source.fromFile("First_Scala/datas/allprovince.txt").getLines().toList
    val products = Source.fromFile("First_Scala/datas/product.txt").getLines().toList

//    1、获取哪些省份没有农产品市场
//    2、获取农产品种类最多的三个省份
//    3、获取每个省份农产品种类最多的三个农贸市场
//    test01(province, products)
//    test02(products)
    test03(products)


  }

  def test01(province:List[String],products:List[String])={
    val filter_product=products.filter(x=>x.split("\t").size==6)
    val product_province=filter_product.map(x=>x.split("\t")(4))
    val distinct_province=product_province.distinct
    province.diff(distinct_province).foreach(println(_))
  }

  def test02(products:List[String])={
    val filter_products=products.filter(x=>x.split("\t").size==6)
    //1 过滤参数不全的产品数据
    //2 裁减列和菜名
      //3 去重 一个省份相同的菜名
      //4 按照省份分组
      //5 排序前三
      //6 结果展示
      .map(line=>{
        val arr=line.split("\t")
        val province =arr(4)
        val name=arr(0)
        (province,name)
      }).distinct.groupBy(x=>x._1).map(x=>(x._1,x._2.size)).toList.sortBy(x=>x._2).reverse.take(3).foreach(println(_))
  }

//  def test03(product:List[String])={
//    val filter_product=product.filter(x=>x.split("\t").size==6)
//      .map(line=>{
//        val province = line.split("\t")(4)
//        val market = line.split("\t")(3)
//        val name = line.split("\t")(0)
//        (province,market,name)
//      }).distinct.groupBy(x=>(x._1,x._2)).map(x=>(x._1,x._2.size)).groupBy(x=>x._1._1)
//      .map(x=>{
//      val take3=x._2.toList.sortBy(x=>x._2).reverse.take(3).map(y=>(y._1._2,y._2))
//        (x._1,take3)
//    }).foreach(println(_))
//  }

  def test03 (products:List[String])={
    val filter_products=products.filter(x=>x.split("\t").size==6)
      .map(line=>{
        val province = line.split("\t")(4)
        val market=line.split("\t")(3)
        val name = line.split("\t")(0)
        (province,market,name)
      }).distinct.groupBy(x=>(x._1,x._2)).map(x=>(x._1,x._2.size)).groupBy(x=>x._1._1).map(x=>{
      val take3=x._2.toList.sortBy(x=>x._2).reverse.take(3).map(x=>(x._1._2,x._2))
      (x._1,take3)
    }).foreach(println(_))
  }

}












