package test

import java.text.SimpleDateFormat
import java.util.Date

object D3 {
  def main(args: Array[String]): Unit = {

    val a = System.currentTimeMillis()
    val b = new Date()
    val c = new Date(a)
    val geshi = new SimpleDateFormat("yyyyMMdd")
    val d = geshi.format(b)//将当前时间转换为geshi对象的格式

    val geshi1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val e="2021-03-01 12:10:10"
    val str = geshi1.parse(e)//.getTime  getTime是获取时间戳
    println(a)
    println(b)
    println(c)
    println(d)
    println(str)
  }

}
