package com.atguigu.day03

import java.sql.{Connection, DriverManager, PreparedStatement}

import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

import scala.util.Random

/**
  * spark中算子分为两大类:
  *     1、Transformation算子: 只是对数据进行转换、统计聚合，不会触发任务的计算
  *     2、Action算子: 触发任务计算
  */
class $01_Transformation {

  val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))
  /**
    *  map(func: RDD中元素类型 => B)
    *       map中的函数是针对RDD中每个元素进行操作
    *       map中的函数针对每个元素操作之后都会返回一个结果,所以通过map生成的rdd的元素个数 = 父rdd元素的个数
    *       map计算的时候再每个分区中是串行,分区间是并行的
    */
  @Test
  def map():Unit = {

    val rdd = sc.parallelize(List("hello","java","scala","python","hello","java","scala","python","hello","java","scala","python","hello","java","scala","python"))

    val rdd2 = rdd.map(x=> {
      println(Thread.currentThread().getName+"----"+x)
      x.length
    })

    println(rdd2.collect().toList)
  }

  /**
    * mapPartitions(func: 迭代器[RDD元素类型] => 迭代器[B])
    *     mapPartitions中的函数针对的是一个分区的所有数据
    *     mapPargigions中的函数调用的时候是一个分区调用一次
    *
    * map与mapPartitions区别:
    *     1、map中的函数是针对每个元素进行操作，mapPartitions里面的函数是针对一个分区操作
    *     2、map中的函数每个操作一个元素之后，会返回一个结果, map生成的rdd的元素的个数 = 父RDD元素个数
    *        mapPartitions中的函数是操作一个分区所有数据,要求返回一个迭代器,但是返回的迭代器中元素个数没有规定,所以 mapPartitions生成的rdd的元素个数可能不等于父RDD元素个数
    *     3、map里面的函数是针对每个元素操作,元素操作完成之后就会立即释放内存
    *       mapPartitions里面函数是针对一个分区所有数据操作,如果这一个分区的所有数据没有处理完成,内存不会释放，所以如果一个分区中数据量特别大的时候,有可能出现内存溢出。如果出现内存溢出可以用map代替。
    *
    */
  @Test
  def mapPartitions():Unit = {
    //数据中是user 的id
    val rdd = sc.parallelize(List(10,2,5,3,7))
    //需求: 根据用户的id从mysql获取用户的详细信息



    rdd.map(x=> {
      var connection:Connection = null
      var statement:PreparedStatement = null
      try{

        connection = DriverManager.getConnection("jdbc:mysql://hadoop102:3306/test","root","root123")

        statement = connection.prepareStatement("select * from user where id=?")
        statement.setInt(1,x)   //将rdd中的x传到第一个占位符这里

        val result = statement.executeQuery()

        var name:String = null
        var age:Int = 0
        var address:String = null

        while (result.next()){
          name = result.getString("name")
          age = result.getInt("age")
          address = result.getString("address")
        }

        (x,name,age,address)
      }catch {
        case e:Exception => (x,null,0,null)
      }finally {
        if(statement!=null)
          statement.close()
        if(connection!=null)
          connection.close()
      }
    })//.collect()


    val rdd3 = rdd.mapPartitions(it => {
      println("-------------------------------------------")
      it.filter(_%2==0)
      /*var result = List[(Int,String,Int,String)]()
      var connection:Connection = null
      var statement:PreparedStatement = null
      try{

      connection = DriverManager.getConnection("jdbc:mysql://hadoop102:3306/test","root","root123")

      statement = connection.prepareStatement("select * from user where id=?")

      it.foreach(x=> {
        statement.setInt(1,x)
        val resultSet = statement.executeQuery()
        var name:String = null
        var age:Int = 0
        var address:String = null
        while (resultSet.next()){
          name = resultSet.getString("name")
          age = resultSet.getInt("age")
          address = resultSet.getString("address")

          result = (x,name,age,address) :: result
        }
      })
      }catch {
        case e:Exception =>
      }finally {
        if(statement!=null)
          statement.close()
        if(connection!=null)
          connection.close()
      }

      result.toIterator*/
    })

    println(rdd3.collect().toList)
  }

  /**
    * mapPartitionsWithIndex( func: (分区号:Int,分区对应的所有数据: 迭代器[RDD元素类型]) => 迭代器)
    *
    *   mapPartitionsWithIndex与mapPartitions的区别: 在于mapPartitionsWithIndex函数中参数多个分区号
    */
  @Test
  def mapPartitionsWithIndex():Unit = {
    val rdd = sc.parallelize(List(10,2,5,3,7))

    val rdd2 = rdd.mapPartitionsWithIndex((index,it)=>{
      println(s"index:${index}  datas:${it.toList}")
      it
    }).collect()

    println(rdd.collect().toList)
    println(rdd2.toList)
//    List(10, 2, 5, 3, 7)
//    List()

  }

  /**
    * flatMap(func: RDD元素类型 => 集合): 对数据转换+压平
    *     flatMap里面的函数针对的是RDD每个元素进行操作
    *     RDD中有多少个元素，函数就会调用多少次
    */
  @Test
  def flatMap():Unit = {

    val rdd1 = sc.parallelize(List("hello java","hello scala java"))

    val rdd2 = rdd1.flatMap(x=>x.split(" "))
    println(rdd2.collect().toList)
  }

  /**
    * 将一个分区所有元素转成数组
    *   glom之后生成的rdd中元素个数 = 分区数
    */
  @Test
  def glom():Unit = {
    val rdd1 = sc.parallelize(List("hello one","hello two","hello three","hello four","hello five","hello six","hello seven","hello eight","hello nine"),2)

    val rdd2 = rdd1.glom()

    val list: List[Array[String]] = rdd2.collect().toList

    list.foreach(x=> println(Thread.currentThread().getName+"---"+x.toList))
  }

  /**
    * 按照指定规则进行分组
    *   groupBy( func: RDD元素类型 => K ): 后续会按照函数中返回值进行分组
    *     groupBy里面的函数也是针对每个元素进行操作
    * MR 过程:  data -> InputFormat -> map方法操作数据 -> 环形缓冲区 -> 分组、排序 -> 溢写磁盘 -> 合并小文件件[分组、排序] -> reduce拉取数据 -> 归并排序 -> reduce方法操作数据 -> 写入磁盘
    *
    * MR shuffle: 环形缓冲区 -> 分组、排序 -> 溢写磁盘 -> 合并小文件件[分组、排序]
    * spark shuffle: 环形缓冲区 -> 分组 -> 溢写磁盘 -> 合并小文件件
    *
    */
  @Test
  def groupBy():Unit = {

    val rdd = sc.parallelize(List("hello java hello","java scala python","scala java","java scala python"))

    val rdd2 = rdd.flatMap(_.split(" "))

    val rdd3 = rdd2.groupBy(x=> x)

    rdd3.collect().toList.foreach( x => {
      println(x._1,x._2.toList)
    })



    val rdd4 = sc.parallelize(List(10,30,2,5,6,2,10,20,30))

    val rdd5 = rdd4.groupBy(x=>x)

    rdd5.mapPartitionsWithIndex((index,it)=>{
      println(s"index=${index}  datas=${it.toList}")
      it
    }).collect()
  }

  /**
    * 统计每个省份菜的种类数
    */
  @Test
  def wordCount():Unit = {

    //1、读取数据
    val rdd = sc.textFile("datas/product.txt")
    //2、过滤、去重、列裁剪
    //过滤非法数据
    val rdd2 = rdd.filter(x=> x.split("\t").length==6)

    //列裁剪
    val rdd3 = rdd2.map(line=>{
      val arr = line.split("\t")
      (arr(4),arr.head)
    })

//List((山西,香菜), (湖南,大葱), (湖北,葱头), (河南,大蒜), (北京,蒜苔), (上海,韭菜), (山东,青椒))

    //去重
    val rdd4 = rdd3.distinct()
    //3、按照省份分组
    val rdd5 = rdd4.groupBy(_._1)

    //4、统计种类数
    val rdd6 = rdd5.map(x=>{

      (x._1,x._2.size)
    })

    //5、结果展示
    println(rdd6.collect().toList)

    Thread.sleep(100000)
  }

  /**
    * filter(func: RDD元素类型 => Boolean): 过滤
    *   filter里面的函数针对的也是每个元素
    *   filter保留的是函数返回值为true
    */
  @Test
  def filter():Unit = {

    val rdd = sc.parallelize(List(1,3,5,2,6,8,10,9))

    val rdd2 = rdd.filter(x=> x%2==0)

    println(rdd2.collect().toList)
  }

  /**
    *
    * sample(withReplacement,fraction,seed):采样
    * withReplacement:  是否采样之后返回，true代表放回，后续采样的时候还可能采到同一个数据 ,false代表不放回,代表同一个数据最多只能被采样一次【工作中一般设置为false】
    * fraction:
    *     withReplacement=true,此时fraction代表每个元素期望被采样的次数
    *     withReplacement=false,此时fraction代表每个元素被采样的概率 【工作中一般是设置为0.1/0.2】
    *
    * seed: Long = Utils.random.nextLong
    *
    * sample算子一般在工作中用于数据倾斜场景。
    */
  @Test
  def sample():Unit = {

    val rdd = sc.parallelize(List("hello","hello","world","java","scala","python","flume","kafka","hadoop,fine"))

    val rdd2 = rdd.sample(true,5)
    val rdd3 = rdd.sample(false,0.2)
    //[(spark,1),(hello,1),(spark,1),(java,1),(spark,1),(spark,1),(hadoop,1),(spark,1),(spark,1),(spark,1)，(spark,1)，(spark,1)]
    //读取文件统计单词个数报错,采样[(spark,1),(hello,1),(spark,1),(java,1),(spark,1),(spark,1),(hadoop,1),(spark,1),(spark,1),(spark,1)，(spark,1)，(spark,1)]
    println(rdd3.collect().toList)

  }

  /**
    * 去重
    * distinct有shuffle操作
    */
  @Test
  def distinct():Unit = {
    val rdd = sc.parallelize(List("hello","hello","world","java","scala","python","flume","kafka","hadoop"))
    val rdd2 = rdd.distinct()
    println(rdd2.collect().toList)

    val rdd3 = rdd.groupBy(x=>x)
    val rdd4 = rdd3.map(x=>x._1)
    println(rdd4.collect().toList)
  }

  /**
    * 合并分区
    *   coalesce 默认只能减少分区，不会有shuffle操作
    *   要想通过coalesce增大分区，需要设置shuffle参数 = true,此时有shuffle操作
    */
  @Test
  def coalesce():Unit = {
    val rdd = sc.parallelize(List("hello","hello","world","java","scala","python","flume","kafka","hadoop"),6)

    /**
      * index:0 datas:List(hello)   =>List( (6,hello))
      * index:1 datas:List(hello, world) => List( (2,hello),(3,world))
      * index:2 datas:List(java)
      * index:3 datas:List(scala, python) => List( (7,scala),(8,python))
      * index:4 datas:List(flume)
      * index:5 datas:List(kafka, hadoop)
      */
/*    rdd.mapPartitionsWithIndex((index,datas)=>{
      println(s"index:${index} datas:${datas.toList}")
      datas
    }).collect()*/

    // rdd->mapPartitionsWithIndexInternal[RDD]->ShuffleRdd->CalesceRdd[RDD]
    val rdd2 = rdd.coalesce(8,true)
/*    def byteswap32(v: Int): Int = {
      var hc = v * 0x9e3775cd
      hc = java.lang.Integer.reverseBytes(hc)
      hc * 0x9e3775cd
    }
    for(i<- 0 until 6){
      println(s"index ${i}:"+new Random(byteswap32(i)).nextInt(8))

    }*/

    /**
      * index 0:5
      * index 1:1
      * index 2:6
      * index 3:6
      * index 4:2
      * index 5:1
      */

    /**
      * index:0 datas:List(hello)
      * index:1 datas:List(hello, world, java)
      * index:2 datas:List(scala, python)
      * index:3 datas:List(flume, kafka, hadoop)
      */
    rdd2.mapPartitionsWithIndex((index,datas)=>{
      println(s"index:${index} datas:${datas.toList}")
      datas
    }).collect()

    /**
      * index:0 datas:List(python)
      * index:1 datas:List()
      * index:2 datas:List(hello, kafka)
      * index:3 datas:List(world, flume, hadoop)
      * index:4 datas:List()
      * index:5 datas:List()
      * index:6 datas:List(hello)
      * index:7 datas:List(java, scala)
      */
    println(rdd.partitions.length)
    println(rdd2.partitions.length)
    Thread.sleep(1000000)

  }


  /**
    * 重新分区
    *     repartition既可以增大分区也可以减少分区
    *     repartition不管是增大分区还是减少分区都有shuffle,底层就是使用的coalease(shuffle=true)
    *
    * coalesce与repartition的区别:
    *     coalesce默认只能减少分区,默认减少分区没有shuffle，增大分区需要设置shuffle参数为true,有shuffle操作
    *     repartition不管是增大分区还是减少分区都有shuffle
    *
    * 如果减少分区推荐使用coalesce,因为没有shuffle效率更高。如果需要增大分区,推荐使用repartition，因为使用更加简单。
    * coalesce在工作用一般和filter结合使用。
    *
    */
  @Test
  def repartition():Unit = {
    val rdd = sc.parallelize(List("hello","hello","world","java","scala","python","flume","kafka","hadoop"),6)

    val rdd2 = rdd.repartition(8)
    println(rdd2.partitions.length)
    val rdd3 = rdd.repartition(4)
    println(rdd3.partitions.length)
    //rdd2.collect().toList
    //rdd3.collect().toList

    val rdd4 = rdd.coalesce(4)
    rdd4.collect().toList

    println(rdd4.partitions.length)

    Thread.sleep(100000)

  }

  /**
    *
    * sortBy(func: RDD元素类型 => K,ascding:Boolean)
    *     sortBy里面的函数是针对每个元素操作，后续会根据函数返回值进行排序
    *     ascding=true代表升序
    *     ascding=false代表降序
    */
  @Test
  def sortBy():Unit = {

    val rdd = sc.parallelize(List(10,2,4,6,3,11),3)

    val rdd2 = rdd.sortBy(x=>x,false)

    rdd2.mapPartitionsWithIndex((index,it)=>{
      println(s"index:${index}  datas=${it.toList}")
      it
    }).collect()

    println(rdd2.collect().toList)

    //println(rdd2.collect().toList)

    Thread.sleep(1000000)

  }

  /**
    * pipe(脚本路径)
    *     spark可以通过pipe调用脚本，在调用的时候是每个分区调用一次脚本
    *     在脚本中可以通过echo 返回数据作为新的rdd的数据
    */
  @Test
  def pipe():Unit = {

  }

  /**
    * 交集
    */
  @Test
  def intersection():Unit = {

    val rdd = sc.parallelize(List(1,2,3,4,5))
    val rdd2 = sc.parallelize(List(4,5,6,7,8))

    val rdd3 = rdd.intersection(rdd2)
    println(rdd3.collect().toBuffer)
  }

  /**
    * 并集
    *   没有shuffle操作
    */
  @Test
  def union():Unit = {

    val rdd = sc.parallelize(List(1,2,3,4,5))
    val rdd2 = sc.parallelize(List(4,5,6,7,8))

    val rdd3 = rdd.union(rdd2)
    println(rdd3.partitions.length)

    println(rdd3.collect().toBuffer)
    println(rdd3.collect().toList)
  }

  /**
    * 差集
    */
  @Test
  def subtract():Unit = {
    val rdd = sc.parallelize(List(1,2,3,4,5))
    val rdd2 = sc.parallelize(List(4,5,6,7,8))

    val rdd3 = rdd.subtract(rdd2)
    println(rdd3.collect().toList)
  }

  /**
    * 拉链
    *     zip拉链的时候要求两个RDD数据量、分区数也要一样
    */
  @Test
  def zip():Unit = {
    val rdd = sc.parallelize(List(1,2,3,4,5),2)
    val rdd2 = sc.parallelize(List(4,5,6,7,5),2)

    val rdd3 = rdd.zip(rdd2)

    println(rdd3.collect().toList)


  }


}
