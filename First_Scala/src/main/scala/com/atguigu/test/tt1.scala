package com.atguigu.test

import com.sun.org.apache.xalan.internal.lib.ExsltStrings.split

import java.io.FileInputStream
import scala.collection.mutable.ListBuffer
import scala.io.Source

object tt1 {
  def main(args: Array[String]): Unit = {

//    val province =Source.fromFile("D:\\尚硅谷文件\\scala\\代码\\scala\\com\\atguigu\\allprovince.txt", "utf-8")
//      .getLines().map(x=>(x,x.length)).toMap  //toMap 得先用map变为两个值的集合，再tomap
    val province =Source.fromFile("D:\\尚硅谷文件\\scala\\代码\\scala\\com\\atguigu\\allprovince.txt", "utf-8").getLines().toList
    val product =Source.fromFile("D:\\尚硅谷文件\\scala\\代码\\scala\\com\\atguigu\\product.txt","utf-8").getLines().toList

    var filter_product =new ListBuffer[String]
    for (elem <- product if elem.split("\t").size==6) {
      filter_product.+=( elem.split("\t")(4))
    }
    val distinct_province = filter_product.distinct
    val no_shichang = province.diff(distinct_province)

    println(no_shichang)

//    for(word<-province.getLines()){
//      province_list0.+=(word)
//    }
//
//    for(word<-product.getLines()){
//      val product_list2 = word.split("/t")
//      product_list0.++=(product_list2)
//    }
//
//    val province_list1 = province_list0.toList
//    val product_list1 = product_list0.toList


  }
}
