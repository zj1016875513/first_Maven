package com.atguigu.test

object T12 {
  def main(args: Array[String]): Unit = {
    val list1=List[Int](1,2,3,4,5,6,7,8,9,10)

    val list2 = list1.filter(x => x % 2 == 0)
    println(list2)
    //List(2, 4, 6, 8, 10)

    val list3 = List("Hello", "Are","You","ok","fine")
    val list4 = list3.map(x => x.charAt(0))
    println(list4)
    //List(H, A, Y, o, f)

    val list5 = List(List(List("Hello world Hello","Good morning")), List(List("Hello scala Hello","Good afternoon")))
    //一次flatten就解除一层集合，最后大集合里的每一个最小元素都会变为统一的一个大集合
    println(list5.flatten)
    //List(List(Hello world Hello, Good morning), List(Hello scala Hello, Good afternoon))
    println(list5.flatten.flatten)
    //List(Hello world Hello, Good morning, Hello scala Hello, Good afternoon)

    val list51 = List(List(List("Hello world Hello"),List("Good morning")), List(List("Hello scala Hello"),List("Good afternoon")))
    println(list51.flatten.flatten)
    //List51=List(Hello world Hello, Good morning, Hello scala Hello, Good afternoon)

    val list6 = list5.flatten.flatten.flatMap(x=>x.split(" "))
    println(list6)
    //List(Hello, world, Hello, Good, morning, Hello, scala, Hello, Good, afternoon)

    val list7 = list6.groupBy(x => x).map(x=>(x._1,x._2.size)).toList
    println(list7)
    //List((world,1), (Good,2), (Hello,4), (scala,1), (afternoon,1), (morning,1))

    val resault1 = list1.reduce((x, y) => x + y)
    println(resault1)

    val resault2 = list1.fold(1000)((x, y) => {
      println(s"x=${x} y=${y}")
      x + y})
    println(resault2)



    val datas = List(("Hello Scala Spark World", 4), ("Hello Scala Spark", 3), ("Hello Scala", 2), ("Hello", 1))
    datas.flatMap(x=>{
      val words = x._1.split(" ")
      words.map(y=>(y,x._2))
    }).groupBy(x=>x._1).map(x=>(x._1,x._2.map(x=>x._2).sum)).foreach(println(_))

  }
}
