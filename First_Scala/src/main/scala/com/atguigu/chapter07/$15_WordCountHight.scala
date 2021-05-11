package com.atguigu.chapter07

import scala.Console.println

object $15_WordCountHight {

  def main(args: Array[String]): Unit = {
    val datas = List(("Hello Scala Spark World", 4), ("Hello Scala Spark", 3), ("Hello Scala", 2), ("Hello", 1))

    //1、切割、压平，给单词一个初始次数
    val words = datas.flatMap(line=>{
      //切割
    val arr = line._1.split(" ")

    arr.map(x=>(x,line._2))
    })
    //List( (Hello,4),(Scala,4),(Spark,4),(World,4),(Hello,3),(Scala,3),(Spark,3),(Hello,2),(Scala,2),(Hello,1) )

    //2、按照单词进行分组
    //words.groupBy( x=> x._1)
    val groupedWords = words.groupBy(_._1)
    //Map(
    //     Hello -> List ( (Hello,4),(Hello,3),(Hello,2),(Hello,1) )
    //     Scala -> List ( (Scala,4),(Scala,3),(Scala,2))
    //     Spark -> List ( (Spark,4),(Spark,3) )
    //     World -> List ( (World,4) )
    // )

    //3、统计单词总次数
    val result = groupedWords.map(x=>{
//      x = Hello -> List ( (Hello,4),(Hello,3),(Hello,2),(Hello,1) )


      //解释 ： agg._1只是放在那里，真正做数量统计计算的是后面的 agg._2+curr._2
      val num = x._2.reduce((agg,curr)=> (agg._1,agg._2+curr._2) )
      (x._1,num._2,num._1)

//    val num = x._2.map(_._2).sum
//      (x._1,num)

    })

    //4、结果展示

    result.foreach(println(_))
//    result.foreach(x=>println(x))   //一样的

  }
}
