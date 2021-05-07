package com.atguigu.test

object wordcount_high {
  val datas = List(("Hello Scala Spark World", 4), ("Hello Scala Spark", 3), ("Hello Scala", 2), ("Hello", 1))

  def main(args: Array[String]): Unit = {

    val words: List[(String, Int)] = datas.flatMap(x => {
      val aa: Array[String] = x._1.split(" ")
      aa.map(y => (y, x._2))
    })

    val goruped: Map[String, List[(String, Int)]] = words.groupBy(x => x._1)

    val result: Map[String, Int] = goruped.map(x => {
      val num: Int = x._2.map(y => y._2).sum
      (x._1, num)
    })

    result.foreach(println(_))
  }
}
