package com.atguigu.chapter07



import scala.math.Ordering

object $12_CollectionLowFunction {

  def main(args: Array[String]): Unit = {

    val list = List(10,3,6,1,8,20)

    //获取最大值
    println(list.max)
    //获取最小值
    println(list.min)
    //求和
    println(list.sum)
    println("*"*100)
    //maxBy是根据指定条件获取最大元素
    //maxBy中需要传入一个函数,该函数是只有一个参数，参数类型=集合元素类型
    // maxBy中的函数是针对集合每个元素操作
    //根据指定条件获取最大值
    val list2 = List( ("zhangsan",20,4000),("lisi",25,3000),("wangwu",18,2500),("wangwu",25,4000),("aa",30,1000) )
      //根据年龄获取最大元素
      val func = (x: (String,Int,Int)) => {
        println(x)
        x._2
      }
    println(list2.maxBy(func))
    println("%"*100)

    //直接传递函数的值
    println(list2.maxBy((x:(String,Int,Int))=>x._2))
    //省略函数参数类型
    println(list2.maxBy((x)=>x._2))
    //参数个数只有一个,可以省略()
    println(list2.maxBy(x=>x._2))
    //参数只在函数体中使用了一次,用_代替
    println(list2.maxBy(_._2))
    //根据指定条件获取最小值
    //list2.minBy( x => x._3)
    println(list2.minBy(_._3))

    println("$"*100)
    //排序
    //sorted: 直接按照集合元素进行排序[默认升序]
    println(list2.sorted)
    println(list.sorted)

    //sortBy: 指定条件排序，默认升序
    //sortBy中的函数只有一个参数,参数类型 = 集合元素类型
    //sortBy中的函数针对的是集合每个元素
    val list3 = list2.sortBy(x=> x._2)
    println(list3)

    //按照年龄排序，如果年龄相同，按照薪资排序
    //自定义排序规则
    val ordering = new Ordering[(Int,Int)]{
      //比较规则
      override def compare(x: (Int, Int), y: (Int, Int)): Int = {
        if(x._1 < y._1) -1
        else if(x._1==y._1) {
          //比较薪资
          if(x._2 < y._2) 1
          else if(x._2 == y._2) 0
          else -1
        }else 1
      }

    }
    //先按年龄从小到大排序，如果年龄相同则再按工资从多到少排序
    val list4 = list2.sortBy(x=> (x._2,x._3) )(ordering)
    println(list4)
    //降序 即升序反转
    println(list4.reverse)

    //sortWith
    //sortWith里面的函数有两个参数
    //sortWith里面的函数如果是第一个参数<第二个参数,升序
    //sortWith里面的函数如果是第一个参数>第二个参数,降序
    val list5 = list.sortWith((x,y)=> x>y)
    println(list5)
  }
}
