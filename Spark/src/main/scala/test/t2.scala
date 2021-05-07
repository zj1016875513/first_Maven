package test

object t2 {
  def main(args: Array[String]): Unit = {
//    ceshi("wangwu".hashCode,2)
    ceshi1(1,2,3,4,5,6,7,8,9)
  }
  def ceshi(a:Int,num:Int):Int={
    val b = a % num
    val c= b + (if (b < 0) num else 0)
    println(c)
    println(b)
    println("wangwu".hashCode%2)
    println(-1%2)
    c
  }

  def ceshi1(x:Int,y:Int*)={
    println(x)
    for (elem <- y) {
      print(elem)
    }
  }

}
