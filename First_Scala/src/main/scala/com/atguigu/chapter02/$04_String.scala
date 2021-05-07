package com.atguigu.chapter02

object $04_String {

  /**
    * java中如何获取字符串:
    *     1、通过new的方式:new String("...")
    *     2、通过 "" 包裹:  "..."
    *     3、通过连接符+:  "aa"+"cc"
    *     4、通过一些方法获取: toString、subString....
    * scala中获取字符串:
    *     1、通过new的方式:new String("...")
    *     2、通过 "" 包裹:  "..."
    *     3、通过连接符+:  "aa"+"cc"
    *     4、通过插值表达式: s"aa ${外部变量/表达式}"
    *     5、通过三引号的方式: """...""" [后续主要用来写sql语句]
    *     6、通过一些方法获取: toString、subString....
    * @param args
    */
  def main(args: Array[String]): Unit = {

    //通过new的方式:new String("...")
    val name = new String("zhangsan")
    println(name)

    //通过 "" 方式
    val name2 = "lisi"
    println(name2)

    //通过连接符+:  "aa"+"cc"
    val table = "person"
    val name3 = "select * from"+ table
    println(name3)

    //4、通过插值表达式: s"aa ${外部变量/表达式}"
    val sql1 = s"select * from ${table} where id = ${1+1}"

    println(sql1)

    //5、三引号
    val sql2 = "select * from （" +
      "select a.name,b.sex from(" +
          ".....)"

    val sql3 =
      s"""
        |select * from (
        |   select a.name,b.sex from
        |     (select id,name,age from ${table} where id>10)a
        |       join
        |     (select id,sex,address from student) b
        |     on a.id=b.id
        | where b.address='shenzhen')
        |
      """.stripMargin
    println(sql3)

    val json = """{"name":"age"}"""
    println(json)

    //6、通过一些方法获取: toString、subString....
    val age = 20
    val ageString = age.toString
    println(ageString)

    // %s : 是字符串的占位符
    // %d : 是整数的占位符
    // %f: 是浮点型的占位符
    val host = "hadoop102"
    val url = "http://%s:%d/%s/bb"
    println(url.format(host,9820,"xx"))
  }
}
