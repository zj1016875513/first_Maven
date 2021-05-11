package com.atguigu.chapter07

object $13_CollectionHightFunction_reduce_map {

  def main(args: Array[String]): Unit = {

    val list = List[Int](10,3,5,7,2,18)

    //filter(func: 集合元素类型 => Boolean ): 过滤
    //filter中的函数针对集合每个元素操作
    //filter保留的是函数返回值为true的数据
    val list2 = list.filter( x => x%2==0 )
    val list22 = list.filter( x => {
        val aaa=x%2==0
        !aaa})
    println(list2)
    println(list22)
    //map(func: 集合元素类型 => B ): 映射
    //map中的函数针对集合每个元素操作
    //map中的函数没执行一次肯定会返回一条数据，所以 val B = A.map ，B集合长度 = A集合长度
    //map使用场景: 一对一【进行数据转换】
    val list3 = List("spark","scala","hello")

    val list4 = list3.map(x=> x.charAt(0))
    val list41=list3.map(x=>x.length).sum
    println(list4)
    println(list41) //sum

    //foreach(func: 集合元素类型 => Unit ):Unit
    //foreach与map的区别: map有返回值,foreach没有返回值
    //foreach中的函数针对集合每个元素操作
    //foreach使用场景: 只是单纯对集合每个元素进行操作,不需要返回数据

    list3.foreach(x=> println(x))
    //x
    list3.foreach(println(_))

    //flatten: 压平
    //flatten使用场景: 一对多。集合嵌套集合，将第二层集合去掉
    val list5: List[List[String]] = List(List("spark","hello"),List("scala","spark"))
    val list6 = list5.flatten
    println(list6)

    val list7 = List[List[List[String]]](
      List(
        List("aa","bb"),
        List("dd","dd")
      ),
      List(
        List("ee","ff"),
        List("tt","yy")
      ),
    )
    val list8 = list7.flatten
    //会发现这三层List只去掉了一层List
    println("list8="+list8)

    //List(spark,hello,scala,spark)

    //flatMap(func: 集合元素类型 => 集合) = map + flatten
    //flatMap里面的函数针对集合每个元素操作
    //flatMap的应用场景: 一对多。在数据转换之后在压平
    val list9 = List("hello spark hello java","hello python java","java spark hello")

    val list10 = list9.map(x=> x.split(" ")).flatten
//    val list99 = list9.flatten
//    println("List99="+list99)
    println("List10="+list10)

    val list11 = list9.flatMap(x=> x.split(" "))
    println("List11="+list11)
    //List(hello,spark,hello,java,hello,python,java,java,spark,hello)

    //groupBy(func: 集合元素类型 => B): 分组
    //groupBy里面的函数针对的也是集合每个元素
    //groupBy里面函数的返回值就是分组的key
    //groupBy的结果是Map,Map的key就是函数返回值，value是key在原集合中对应的所有数据
    //groupBy场景: 多对一
    val list12 = List( ("zhangsan","man","beijing"),("lisi","woman","shenzhen"),("zhaoliu","man","shenzhen") )

    val list13 = list12.groupBy(_._2)

    println("List13="+list13)
    println(list13.toList)


    println("    reduce   "+"*"*100)
    //reduce(func: (集合元素类型，集合元素类型) => 集合元素类型 ): 从左向右聚合
    println(list)
    val result = list.reduce((agg,curr)=> {
      println(s"agg=${agg}  curr=${curr}")
      agg+curr
    })
    //agg: 上一次的聚合结果，第一次聚合的时候agg值 = 集合第一个元素
    //curr: 当前要聚合的元素
    println(result)


    println("    reduceRight   "+"*"*100)
    println(list)
    //reduceRight(func: (集合元素类型，集合元素类型) => 集合元素类型): 从右向左聚合
    val result2 = list.reduceRight((curr,agg)=>{
      println(s"agg=${agg}  curr=${curr}")
      curr+agg
    })
    println(result2)
    //fold(初始值)(func: (集合元素类型，集合元素类型) => 集合元素类型) : 从左向右聚合
    println("    fold   "+"*"*100)
    println(list)
    val result3 = list.fold(100)((agg,curr)=>{
      println(s"agg=${agg}  curr=${curr}")
      agg+curr
    })
    println(result3)

    //foldRight(初始值)(func: (集合元素类型，集合元素类型) => 集合元素类型) :从右向左聚合
    println("    foldRight   "+"*"*100)
    println(list)
    val result4 = list.foldRight(1000)((curr,agg)=>{
      println(s"agg=${agg}  curr=${curr}")
      curr+agg
    })
    println(result4)




  }
}
/*
filter
map
flatmap
flatten
groupby
reduce
reduceRight
fold
foldRight
 */