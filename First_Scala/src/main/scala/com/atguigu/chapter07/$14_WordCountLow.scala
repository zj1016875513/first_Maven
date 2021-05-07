package com.atguigu.chapter07

object $14_WordCountLow {

  def main(args: Array[String]): Unit = {
    val datas = List("Hello Scala Hbase kafka", "Hello Scala Hbase", "Hello Scala", "Hello")

    //1、切割句子、压平
    //val words = datas.flatMap(x=> x.split(" "))
    val words = datas.flatMap(_.split(" "))
    //List(Hello,Scala,Hbase,Kafka,Hello,Scala,Hbase,Hello,Scala,Hello)
    //2、按照单词分组
    val groupedWords = words.groupBy(x=>x)
    //Map[
    //      Hello -> List( Hello,Hello,Hello，Hello)
    //      Scala -> List( Scala,Scala,Scala)
    //      Hbase -> List( Hbase,Hbase)
    //      Kafka -> List( Kafka)
    // ]
    //3、对每个单词统计次数
    val result = groupedWords.map(x=>{
      //x = Hello -> List( Hello,Hello,Hello，Hello)
      (x._1,x._2.size)
    })

    //4、结果展示
    result.foreach(println(_))
    println("*"*100)
    datas.flatMap(_.split(" ")).groupBy(x=>x).map(x=>(x._1,x._2.size)).foreach(println(_))

    println(datas.flatMap(x => x.split(" ")))

  }
}
