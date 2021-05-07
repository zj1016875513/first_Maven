package com.atguigu.chapter07



import scala.io.Source

object $19_Test {

  /**
    * 1、获取哪些省份没有农产品市场
    * 2、获取农产品种类最多的三个省份
    * 3、获取每个省份农产品种类最多的三个农贸市场
    *
    */
  def main(args: Array[String]): Unit = {

    //读取数据
    val allprovince = Source.fromFile("First_Scala/datas/allprovince.txt").getLines().toList
    val product = Source.fromFile("First_Scala/datas/product.txt").getLines().toList

//    test1(product,allprovince)

    //test2(product)

    test3(product)

  }

  /**
    * 3、获取每个省份农产品种类最多的三个农贸市场
    *
    * 省份 前三农贸市场
    */
  def test3(products:List[String]): Unit ={

    //1、过滤非法数据
    products.filter(line=> line.split("\t").size==6)
    //2、列裁剪 [省份、农贸市场、菜名]
      .map(line=>{
      val arr = line.split("\t")
      (arr(4),arr(3),arr(0))
    })
    //3、去重
      .distinct
    //4、按照省份、农贸市场分组，统计每个省份每个农贸市场菜的种类数
      .groupBy(x=> (x._1,x._2) )

    /**
      * Map(
      *     (广东省,A农贸市场) -> List( (广东省,A农贸市场,大白菜)，(广东省,A农贸市场,西红柿)，(广东省,A农贸市场,上海青),...)
      *     (广东省,B农贸市场) -> List( (广东省,A农贸市场,大白菜)，(广东省,A农贸市场,西红柿)，(广东省,A农贸市场,猪肉),...)
      *     (湖南省,C农贸市场) -> List( (湖南省,C农贸市场,大白菜)，(湖南省,C农贸市场,西红柿)，(湖南省,C农贸市场,猪肉),...)
      * )
      */
      .map(x=>{
      // x = (广东省,A农贸市场) -> List( (广东省,A农贸市场,大白菜)，(广东省,A农贸市场,西红柿)，(广东省,A农贸市场,上海青),...)
      (x._1,x._2.size)
    })

    /**
      *Map (
      *     (广东省,A农贸市场) ->  10
      *     (广东省,B农贸市场) ->  15
      *     (湖南省,C农贸市场) ->  9
      *      ....
      * )
      */
    //5、按照省份分组
      .groupBy(x=>x._1._1)

    /**
      * Map(
      *     广东省 -> List( (广东省,A农贸市场) ->  10, (广东省,B农贸市场) ->  15,..)
      *     ...
      * )
      */

    //6、对每个省份所有农贸市场菜的种类数排序取前三
      .map(x=>{
      //x = 广东省 -> List( (广东省,A农贸市场) ->  10, (广东省,B农贸市场) ->  15,..)
        //从省份中挑选前三的农贸市场
      val take3 = x._2.toList.sortBy(_._2).reverse.take(3).map(y=>(y._1._2,y._2))
      (x._1,take3)
    })
    //7、结果展示
      .foreach(println(_))
  }

  /**
    * 获取农产品种类最多的三个省份
    */
  def test2(products:List[String]) = {

    //1、过滤非法数据
    products.filter(line=>line.split("\t").size==6)
    //2、列裁剪[省份、菜名]
      .map(line=>{
      val arr = line.split("\t")
      val province = arr(4)
      val name = arr(0)
      (province,name)
    })
    //List( (广东省,西红柿) , (广东省,西红柿) , (广东省,大白菜)，(湖南省,大白菜),(湖南省,大白菜),(湖南省,西蓝花))
    //3、去重[一个省份有多个同名菜]
      .distinct
    ////List( (广东省,西红柿)  , (广东省,大白菜)，(湖南省,大白菜),(湖南省,西蓝花))
    //4、按照省份分组，统计每个省份农产品种类数
      //.groupBy(x=> x._1)
      .groupBy(_._1)
    //Map(
    //    广东省 -> List((广东省,西红柿)  , (广东省,大白菜),.. )
    //    湖南省 -> List((湖南省,西蓝花)  , (湖南省,大白菜),.. )
    //    广西省 ->...
    //    ...
    // )
      .map(x=>(x._1,x._2.size))
    //5、排序取前三
      .toList
      .sortBy( x => x._2 )
      .reverse
      .take(3)
    //6、结果展示
      .foreach(println(_))
  }

  /**
    *  1、获取哪些省份没有农产品市场
    * @param products
    * @param allprovince
    */
  def test1(products:List[String],allprovince:List[String])  = {

    //0、过滤非法数据 [过滤、去重、列裁剪]
    val filterProduct = products.filter(line=>line.split("\t").size == 6)
    //1、切割农产品数据获取省份字段
    val productProvinces = filterProduct.map(line=> {
      line.split("\t")(4)
    })
    //2、去重
    val distinctProvinces = productProvinces.distinct
    //3、全国所有省份与有农产品市场的省份取差集
    allprovince.diff(distinctProvinces)
    //4、结果展示
      .foreach(println(_))
  }
}
