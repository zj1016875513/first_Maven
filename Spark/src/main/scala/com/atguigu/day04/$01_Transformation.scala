package com.atguigu.day04

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, Partitioner, SparkConf, SparkContext}
import org.junit.Test

class $01_Transformation  extends Serializable {
/*
  java 的transient关键字的作用是需要实现Serilizable接口，
  将不需要序列化的属性前添加关键字transient，
  序列化对象的时候，这个属性就不会序列化到指定的目的地中。
  transient使用小结
  1）一旦变量被transient修饰，变量将不再是对象持久化的一部分，该变量内容在序列化后无法获得访问。
  2）transient关键字只能修饰变量，而不能修饰方法和类。注意，本地变量是不能被transient关键字修饰的。
  变量如果是用户自定义类变量，则该类需要实现Serializable接口。
  3）被transient关键字修饰的变量不再能被序列化，一个静态变量不管是否被transient修饰，均不能被序列化。
  */
  @transient
  val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("text"))

  /**
    * 根据指定分区规则重新分区
    */
  @Test
  def partitionBy():Unit = {

    val rdd = sc.parallelize(List("zhangsan"->20,"lisi"->30,"wangwu"->40,"zhangsan"->10,"lisi"->50),2)

    /**
      * index=1 datas=List((wangwu,40), (zhangsan,10), (lisi,50))
      * index=0 datas=List((zhangsan,20), (lisi,30))
      */
  /*  rdd.mapPartitionsWithIndex((index,it)=>{
      println(s"index=${index} datas=${it.toList}")
      it
    }).collect()*/
    //hashPartitioner分区规则: Utils.nonNegativeMod(key.hashCode, numPartitions)
  //        val rawMod = key.hashCode % numPartitions
  //        rawMod + (if (rawMod < 0) numPartitions else 0)
    val rdd2 = rdd.partitionBy(new HashPartitioner(2))

    /**
      * index=1 datas=List((lisi,30), (wangwu,40), (lisi,50))
      * index=0 datas=List((zhangsan,20), (zhangsan,10))
      */
    rdd2.mapPartitionsWithIndex((index,it)=>{
      println(s"index=${index} datas=${it.toList}")
      it
    }).collect()
  }

  @Test
  def myPartitioner():Unit = {
    val rdd = sc.parallelize(List("hello java spark hello","spark java python","python hello spark","spark java hello"),4)
    val rdd2 = rdd.flatMap(_.split(" "))
    val rdd3 = rdd2.groupBy((x:String)=>x,new MyPartitioner(3))
    rdd3.mapPartitionsWithIndex((index,it)=>{
      println(s"index=${index} datas=${it.toList}")
      it
    }).collect()
  }

  /**
    * 分组聚合:根据key分组,然后对每个key进行聚合
    *
    */
  @Test
  def reduceByKey():Unit = {
    val rdd = sc.parallelize(List("hello java spark hello","hello spark java python","python hello spark","spark java hello"),2)

    val rdd2 = rdd.flatMap(_.split(" ")).map((_,1))

    rdd2.mapPartitionsWithIndex((index, it) => {
      println(s"index=${index} datas=${it.toList}")
      it
    }).collect()
//    index=0 datas=List((hello,1), (java,1), (spark,1), (hello,1), (hello,1), (spark,1), (java,1), (python,1))
//    index=1 datas=List((python,1), (hello,1), (spark,1), (spark,1), (java,1), (hello,1))

    val rdd3 = rdd2.reduceByKey((agg,curr)=>{//预聚合和聚合,详细步骤见图片和视频
      println(s"agg=${agg} curr=${curr}")
      agg+curr
    })

    println(rdd3.collect().toList)


  }

  /**
    * 根据key分组
    *     groupByKey与reduceByKey的区别:
    *         1、groupByKey只是分组没有聚合，reduceByKey既有分组也有聚合
    *         2、groupByKey没有预聚合的操作，reduceByKey有预聚合
    *     reduceByKey的性能要比groupByKey性能更高
    *     在工作中推荐使用reduceByKey这种高性能shuffle算子,尽量少用groupByKey这种低性能shuffle算子
    */
  @Test
  def groupByKey():Unit = {
    val rdd = sc.parallelize(List("hello java spark hello","spark java python","python hello spark","spark java hello"),2)

    val rdd2 = rdd.flatMap(_.split(" ")).map((_,1)).groupByKey()
    val rdd3: RDD[(String, Int)] = rdd2.map(x => (x._1, x._2.sum))

    val rdd4: RDD[(String, Int)] = rdd.flatMap(x => x.split(" ")).map(x => (x, 1)).reduceByKey((agg,curr)=>{agg+curr}) //.reduceByKey(_ + _)

//    val rdd5: RDD[(String, Int)] = rdd.flatMap(x => x.split(" ")).map(x => (x, 1)).reduce()


    println(rdd2.collect().toList)
    println(rdd3.collect().toList)
    println(rdd4.collect().toList)

  }

  /**
    * createCombiner:在预聚合的时候对每个分区每个组的第一个value值进行转换
    * mergeValue: 在预聚合的时候的统计逻辑
    * mergeCombiners: 对预聚合完成之后的结果进行统计的逻辑
    */
  @Test
  def combineByKey():Unit = {

    val rdd = sc.parallelize(List(("语文",90),("语文",70),("数学",100),("英语",70),("语文",80),("数学",60),("数学",100),("英语",75),("英语",65)),2)

    //统计没门学科的平均分
    /*val rdd2 = rdd.groupByKey()

    val rdd3 = rdd2.map {
      case (name,list) =>
        (name,list.sum/list.size)
    }
    //List((英语,70), (数学,86), (语文,80))
    println(rdd3.collect().toList)*/

//     rdd.mapPartitionsWithIndex((index,it)=>{
//        println(s"index=${index} datas=${it.toList}")
//        it
//      }).collect()

//    index=0 datas=List((语文,90), (语文,70), (数学,100), (英语,70))
//    index=1 datas=List((语文,80), (数学,60), (数学,100), (英语,75), (英语,65))

    //方法参数内元素是否可以理解为根据key分组之后对value进行操作，方法中的x即代表value
    val rdd3 = rdd.combineByKey(x=>(x,1),
      //将每个分区中有多个元素的组的第一个元素变为(x,1)  如 第一个分区中有 (语文,90), (语文,70)  则将其变为 ((90,1),70)

//      80   即为list里的value值
//      90
//      60
      (agg:(Int,Int),curr)=>{
      println(s"预聚合: agg=${agg} curr=${curr}")
      ( agg._1+curr,agg._2+1)    //元组中第一个值加上下一个值，元组中第一个值赋的1在加上1实现自增实现数量统计
        //第一个分区的语文成绩聚合： ((90,1),70) --> ((90+70),(1+1)) --> (160,2)

    },  //对每个分区每个分组里的所有元素进行累加，对每个分区所有元素个数进行统计   这一步是将每个分区里的每个组完成统计，即预聚合，聚合的是组里的内容
//          预聚合: agg=(90,1) curr=70
//          预聚合: agg=(60,1) curr=100
//          预聚合: agg=(75,1) curr=65

      (agg:(Int,Int),curr:(Int,Int)) => {
      println(s"再次聚合:  agg=${agg}  curr=${curr}")
      (agg._1+curr._1,agg._2+curr._2)
        //这一步是将不同分区的同分组内容进行聚合， ((分区1语文成绩总和+分区2语文成绩总和)，(分区1语文成绩人数+分区2语文成绩人数))

//                  分区1         分区2
//        再次聚合:  agg=(100,1)  curr=(160,2)
//        再次聚合:  agg=(70,1)  curr=(140,2)
//        再次聚合:  agg=(160,2)  curr=(80,1)
    })

    val rdd4 = rdd3.map {
      case (name,(value,num)) => (name,value/num)
    }

    println(rdd4.collect().toList)
//    println(rdd3.collect().toList)
// RDD3的内容-->   List((数学,(260,3)), (英语,(210,3)), (语文,(240,3)))
  }

  /**
    * zeroValue:  在预聚合的时候对每个分区每组key第一次聚合的时候agg的初始值
    * seqOp: , 预聚合的统计逻辑
    * combOp:  对预聚合结果进行再次聚合的时候统计逻辑
    */
  @Test
  def aggregateByKey():Unit = {
    val rdd = sc.parallelize(List(("语文",90),("语文",70),("数学",100),("英语",70),("语文",80),("数学",60),("数学",100),("英语",75),("英语",65)),2)

    //求每个成绩平均分
    val rdd2 = rdd.aggregateByKey( (0,0) )( (agg:(Int,Int),curr:Int)=>{
      println(s"预聚合: agg=${agg} curr=${curr}")
      (agg._1+curr,agg._2+1)
    } ,
      (agg:(Int,Int),curr:(Int,Int)) =>{
        println(s"再次聚合:  agg=${agg}  curr=${curr}")
        (agg._1+curr._1,agg._2+curr._2)
      }
    )

    val rdd4 = rdd2.map {
      case (name,(value,num)) => (name,value/num)
    }

    println(rdd4.collect().toList)
  }

  /**
    * foldByKey(zeroValue)(func)
    *     zeroValue: 是每个分区中进行预聚合的时候每个组第一次计算的agg的初始值
    *     func: 是预聚合、预聚合之后再次统计的逻辑
    *
    * reduceByKey、foldByKey、combineByKey、aggregateByKey的区别:
    *     reduceByKey、foldByKey与combineByKey、aggregateByKey的区别:
    *         reduceByKey、foldByKey: 预聚合<分区内计算>与之后再次统计<分区间>的计算逻辑是一样的
    *         combineByKey、aggregateByKey:  分区内计算和分区间的计算逻辑可以不一样
    *     reduceByKey、combineByKey与foldByKey、aggregateByKey的区别:
    *         reduceByKey、combineByKey在分区内计算的时候,agg的初始值为每个key分组之后第一个value值
    *         reduceByKey、combineByKey在分区内计算的时候,agg的初始值指定的初始值
    *
    *
    */
  @Test
  def foldByKey():Unit = {

    val rdd = sc.parallelize(List(("语文",90),("语文",70),("数学",100),("英语",70),("语文",80),("数学",60),("数学",100),("英语",75),("英语",65)),2)

    //求每个学科平均成绩
    val rdd2 = rdd.map{
      case (name,value) => (name,(value,1))
    }

    val rdd3 = rdd2.foldByKey( (0,0) ) ( (agg:(Int,Int),curr:(Int,Int))=> (agg._1+curr._1,agg._2+curr._2) )

    val rdd4 = rdd3.map{
      case (name,(value,num)) => (name,value/num)
    }

    println(rdd4.collect().toList)
  }

  /**
    * 根据key排序
    */
  @Test
  def sortByKey():Unit = {

    val rdd = sc.parallelize(List( "aa"->1,"zz"->3,"cc"->2,"oo"->10,"tt"->20,"dd"->24 ))

    val rdd2 = rdd.sortByKey(false)

    val rdd3: RDD[(String, Int)] = rdd.sortBy(x => x._2,true)
    //rdd.sortBy(x => x._1)

    println(rdd2.collect().toList)
    println(rdd3.collect().toList)

  }

//对value进行map操作？
  @Test
  def mapValues():Unit = {
    val rdd = sc.parallelize(List( "aa"->1,"zz"->3,"cc"->2,"oo"->10,"tt"->20,"dd"->24 ))

    val rdd2 = rdd.mapValues(x=> x * x)

    val rdd3 = rdd.map(x=> (x._1,x._2 * x._2) )

    println(rdd2.collect().toList)
    println(rdd3.collect().toList)
  }

  /**
    * rdd1.join(rdd2) : 结果类型 RDD[(join的key,( rdd1对应的value值,rdd2对应的value值))]
    *   join只能连接key相同的数据
    */
  @Test
  def join():Unit = {

    val rdd1 = sc.parallelize( List("1001"->"大数据开发部","1002"->"java开发部","1003"->"C开发部","1005"->"酱油部") )
    val rdd2 = sc.parallelize( List( (1,"zhangsan","1001"),(2,"lisi","1002"),(3,"wangwu","1003"),(4,"zhaoliu","1004"),(5,"wanglaoliu","1002"),(6,"hanmeimei","1001") ) )

    //想要获取每个员工详细信息和部门名称
    val rdd3 = rdd2.map{
      case (id,name,deptid) => (deptid,(id,name) )
    }
    //List(
    //    (1001,((1,zhangsan),大数据开发部)),
    //    (1001,((6,hanmeimei),大数据开发部)),
    //    (1002,((2,lisi),java开发部)),
    //    (1002,((5,wanglaoliu),java开发部)),
    //    (1003,((3,wangwu),C开发部)))
    val rdd4 = rdd3.join(rdd1)
    //List(
    //  (1001,((1,zhangsan),Some(大数据开发部))),
    //  (1001,((6,hanmeimei),Some(大数据开发部))),
    //  (1002,((2,lisi),Some(java开发部))),
    //  (1002,((5,wanglaoliu),Some(java开发部))),
    //  (1003,((3,wangwu),Some(C开发部))),
    //  (1004,((4,zhaoliu),None)))
    val rdd5 = rdd3.leftOuterJoin(rdd1)
//      List((1005,(None,酱油部)),
//      (1001,(Some((1,zhangsan)),大数据开发部)),
//      (1001,(Some((6,hanmeimei)),大数据开发部)),
//      (1002,(Some((2,lisi)),java开发部)),
//      (1002,(Some((5,wanglaoliu)),java开发部)),
//      (1003,(Some((3,wangwu)),C开发部)))
    val rdd6 = rdd3.rightOuterJoin(rdd1)

    println(rdd4.collect().toList)
    println(rdd5.collect().toList)
    println(rdd6.collect().toList)
  }

  @Test
  def cogroup():Unit = {
    val rdd1 = sc.parallelize( List("1001"->"大数据开发部","1002"->"java开发部","1003"->"C开发部","1005"->"酱油部") )
    val rdd2 = sc.parallelize( List( (1,"zhangsan","1001"),(2,"lisi","1002"),(3,"wangwu","1003"),(4,"zhaoliu","1004"),(5,"wanglaoliu","1002"),(6,"hanmeimei","1001") ) )
    val rdd3 = rdd2.map{
      case (id,name,deptid) => (deptid,(id,name) )
    }
    val rdd4: RDD[(String, (Iterable[(Int, String)], Iterable[String]))] = rdd3.cogroup(rdd1)

    rdd4.collect().foreach(x=> {
      println(x)
    })
//    (1005,(CompactBuffer(),CompactBuffer(酱油部)))
//    (1001,(CompactBuffer((1,zhangsan), (6,hanmeimei)),CompactBuffer(大数据开发部)))
//    (1002,(CompactBuffer((2,lisi), (5,wanglaoliu)),CompactBuffer(java开发部)))
//    (1003,(CompactBuffer((3,wangwu)),CompactBuffer(C开发部)))
//    (1004,(CompactBuffer((4,zhaoliu)),CompactBuffer()))
  }



  class MyPartitioner(partitions:Int) extends Partitioner{
    //获取分区数
    override def numPartitions: Int = {
      if(partitions<4) 4
      else partitions
    }
    //根据key获取分区号
    override def getPartition(key: Any): Int = key match{
      case null => 0
      case "spark" =>1
      case "java" => 2
      case "python" => 3
      case "hello" => 0
    }
  }
}




