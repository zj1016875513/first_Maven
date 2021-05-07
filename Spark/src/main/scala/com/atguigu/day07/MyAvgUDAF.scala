package com.atguigu.day07

import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._

/**
  * 弱类型
  */
class MyAvgUDAF extends UserDefinedAggregateFunction{

  //定义UDAF入参的类型
  override def inputSchema: StructType = {
    /*val arr = Array(StructField("input",IntegerType))
    StructType(arr)*/
    new StructType()
      .add("input",IntegerType)
  }

  //定义中间变量类型 [sum,count]
  override def bufferSchema: StructType = {
    new StructType()
      .add("sum",IntegerType)
      .add("count",IntegerType)
  }

  //定义udaf函数最终结果类型
  override def dataType: DataType = DoubleType

  //一致性
  override def deterministic: Boolean = true

  //给中间变量赋予初始值
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    //给sum初始值
    buffer.update(0,0)
    //给count初始值
    buffer.update(1,0)
  }

  //每个传入一个数据，更新中间变量
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    //获取之前的sum
    val sum = buffer.getAs[Int](0)
    //获取之前的count
    val count = buffer.getAs[Int](1)
    //更新sum
    buffer.update(0, sum+ input.getAs[Int](0) )

    //更新count
    buffer.update(1,count+1)
  }

  //合并每个task的中间变量的结果值
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    //合并sum
    buffer1.update(0, buffer1.getAs[Int](0) + buffer2.getAs[Int](0))

    //合并count
    buffer1.update(1, buffer1.getAs[Int](1) + buffer2.getAs[Int](1))
  }
  //获取最终结果
  override def evaluate(buffer: Row): Any = {
    buffer.getAs[Int](0).toDouble / buffer.getAs[Int](1)
  }
}

