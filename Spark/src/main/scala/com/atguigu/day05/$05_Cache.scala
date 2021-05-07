package com.atguigu.day05

import org.apache.spark.{SparkConf, SparkContext}

object $05_Cache {
  /**
    * RDD持久化: 将RDD的数据保存起来，便于后续job使用
    *     缓存[cache]:
    *            cache: 数据只保存在内存中,底层就是使用的persist
    *            persist: 数据保存的位置可以自由设置
    *           存储级别:
    *               NONE: 不做任何处理
    *               DISK_ONLY: 只保存在磁盘中
    *               DISK_ONLY_2: 只保存在磁盘中,保存两份
    *               MEMORY_ONLY: 只保存在内存中 <工作常用，一般适用于小数据量场景>
    *               MEMORY_ONLY_2: 只保存在内存中,保存两份
    *               MEMORY_ONLY_SER: 只保存在内存中,以序列化的形式存储
    *               MEMORY_ONLY_SER_2 : 只保存在内存中,以序列化的形式存储,保存两份
    *               MEMORY_AND_DISK: 保存数据到内存中,如果内存不足会保存在磁盘 <工作常用，一般用于大数据量场景>
    *               MEMORY_AND_DISK_2: 保存数据到内存中,如果内存不足会保存在磁盘，保存两份
    *               MEMORY_AND_DISK_SER: 保存数据到内存中,如果内存不足会保存在磁盘,以序列化的形式存储
    *               MEMORY_AND_DISK_SER_2:保存数据到内存中,如果内存不足会保存在磁盘,以序列化的形式存储，保存两份
    *               OFF_HEAP:  数据保存在堆外内存中
    *           使用:  val rdd2 = rdd1.cache()
    *           场景:  rdd重复使用的时候将rdd的数据缓存起来,后续的job就可以直接使用该缓存数据
    *           优势:  可以重用rdd,提高效率
    *         shuffle操作数据会落盘，相当于自动将数据持久化了。
    *     checkpoint: 将数据持久化到可靠的存储介质[HDFS]中。
    *
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("TEST"))

    val rdd = sc.parallelize(List("hello java hello scala","hello java scala python"))

    val rdd2 = rdd.flatMap(_.split(" "))

    val rdd3 = rdd2.map(_.charAt(0))

    val rdd6 = rdd3.cache() //默认的是MEMORY_ONLY
//    rdd3.persist(StorageLevel.MEMORY_AND_DISK)

    println(rdd6.toDebugString)
    println("-----------------------------------")
    println(rdd6.dependencies.toList)
    val rdd4 = rdd6.mapPartitions(it=>{
      it.filter(x=>x=='h')
    })

    println(rdd4.collect().toList)

    val rdd5 = rdd6.map(x=>x.hashCode())
    println(rdd5.collect().toList)

    shuffle(sc)
    Thread.sleep(1000000)
  }
  //两个job有共用的stage的时候,该stage只会执行一次
  def shuffle( sc:SparkContext)={
    val rdd = sc.parallelize(List("hello java hello scala","hello java scala python"))

    val rdd2 = rdd.flatMap(_.split(" "))

    val rdd3 = rdd2.map((_,1))

    val rdd4 = rdd3.reduceByKey(_+_)

    //按照次数排序,取次数最多的三个单词
    val rdd5 = rdd4.sortBy(_._2,false)  //spark的sortBy既是转换算子又是行动算子

    val rdd7 = rdd5.mapValues(x=>x*x)
    val rdd8 = rdd5.mapValues(x=>x+x)

    println(rdd7.collect().toList)
    println(rdd8.collect().toList)
    //println(rdd5.collect().toList)
    //按照次数排序,取次数最少的三个单词
    //val rdd6 = rdd4.sortBy(_._2)
    //println(rdd6.collect().toList)

  }
}

//Spark会产生shuffle的算子
//去重
//def distinct()
//def distinct(numPartitions: Int)
//聚合
//def reduceByKey(func: (V, V) => V, numPartitions: Int): RDD[(K, V)]
//def reduceByKey(partitioner: Partitioner, func: (V, V) => V): RDD[(K, V)]
//def groupBy[K](f: T => K, p: Partitioner):RDD[(K, Iterable[V])]
//def groupByKey(partitioner: Partitioner):RDD[(K, Iterable[V])]
//def aggregateByKey[U: ClassTag](zeroValue: U, partitioner: Partitioner): RDD[(K, U)]
//def aggregateByKey[U: ClassTag](zeroValue: U, numPartitions: Int): RDD[(K, U)]
//def combineByKey[C](createCombiner: V => C, mergeValue: (C, V) => C, mergeCombiners: (C, C) => C): RDD[(K, C)]
//def combineByKey[C](createCombiner: V => C, mergeValue: (C, V) => C, mergeCombiners: (C, C) => C, numPartitions: Int): RDD[(K, C)]
//def combineByKey[C](createCombiner: V => C, mergeValue: (C, V) => C, mergeCombiners: (C, C) => C, partitioner: Partitioner, mapSideCombine: Boolean = true, serializer: Serializer = null): RDD[(K, C)]
//排序
//def sortByKey(ascending: Boolean = true, numPartitions: Int = self.partitions.length): RDD[(K, V)]
//def sortBy[K](f: (T) => K, ascending: Boolean = true, numPartitions: Int = this.partitions.length)(implicit ord: Ordering[K], ctag: ClassTag[K]): RDD[T]
//重分区
//def coalesce(numPartitions: Int, shuffle: Boolean = false, partitionCoalescer: Option[PartitionCoalescer] = Option.empty)
//def repartition(numPartitions: Int)(implicit ord: Ordering[T] = null)
//集合或者表操作
//def intersection(other: RDD[T]): RDD[T]
//def intersection(other: RDD[T], partitioner: Partitioner)(implicit ord: Ordering[T] = null): RDD[T]
//def intersection(other: RDD[T], numPartitions: Int): RDD[T]
//def subtract(other: RDD[T], numPartitions: Int): RDD[T]
//def subtract(other: RDD[T], p: Partitioner)(implicit ord: Ordering[T] = null): RDD[T]
//def subtractByKey[W: ClassTag](other: RDD[(K, W)]): RDD[(K, V)]
//def subtractByKey[W: ClassTag](other: RDD[(K, W)], numPartitions: Int): RDD[(K, V)]
//def subtractByKey[W: ClassTag](other: RDD[(K, W)], p: Partitioner): RDD[(K, V)]
//def join[W](other: RDD[(K, W)], partitioner: Partitioner): RDD[(K, (V, W))]
//def join[W](other: RDD[(K, W)]): RDD[(K, (V, W))]
//def join[W](other: RDD[(K, W)], numPartitions: Int): RDD[(K, (V, W))]
//def leftOuterJoin[W](other: RDD[(K, W)]): RDD[(K, (V, Option[W]))]

