package com.atguigu.day08

import org.apache.spark.sql.SparkSession

import scala.collection.mutable

object Test2 {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().master("local[4]").appName("test")
      //.config("spark.sql.autoBroadcastJoinThreshold","-1")
      .getOrCreate()
    //-----------------------------------------------加载数据---------------------------------------------
    //加载用户行为数据,进行列裁剪、过滤非点击数据
    val userVisitActionRdd = spark.sparkContext.textFile("sqldatas/user_visit_action.txt")
      //列裁剪[只需要点击产品id,城市id]
      .map(line=>{
        val arr = line.split("\t")
        //点击产品id
        val click_product_id = arr(7)
        //城市id
        val city_id = arr.last
        (click_product_id,city_id)
      })
      //过滤非点击数据[如果该数据不是点击数据，点击产品id是-1]
      .filter(_._1!="-1")

    //加载城市数据，进行列裁剪
    val cityRdd = spark.sparkContext.textFile("sqldatas/city_info.txt")
      .map(line=>{
        val arr = line.split("\t")
        //城市id
        val cityid = arr.head
        //城市名称
        val cityName = arr(1)
        //城市所在区域
        val area = arr.last
        (cityid,(cityName,area))
      })

    //加载产品数据,进行列裁剪
    val productRdd = spark.sparkContext.textFile("sqldatas/product_info.txt")
      .map(line=>{
        val arr = line.split("\t")
        //产品id
        val productid = arr.head
        //产品名称
        val productName = arr(1)
        (productid,productName)
      })

    //广播城市数据与产品数据
    val cityBc = spark.sparkContext.broadcast(cityRdd.collect().toMap)
    val productBc = spark.sparkContext.broadcast(productRdd.collect().toMap)

    //转换用户行为数据为 ((区域,产品名称),城市)
    val mapRdd = userVisitActionRdd.map{
      case (click_product_id,city_id) =>
        //取出产品id对应的名称
        val productName = productBc.value.getOrElse(click_product_id,"")
        //取出城市id对应的城市名称与区域
        val (city,area) = cityBc.value.getOrElse(city_id,("",""))

        //因为后续需要统计每个区域每个产品的点击次数，所以转换为KV键值对,K为区域+产品  V为城市,因为后续需要统计每个城市的占比
        ((area,productName),city)
    }
   //------------------------------------------聚合---------------------------------------------
    //预聚合逻辑【就是UDAF的reduce方法】
    val reduce = (agg: ProductBuff, curr: String) => {
      //更新每个区域每个商品出现的城市数据
      //判断城市是否已经出现过,如果出现过,则该城市点击次数 = 之前统计的点击次数 + 1
      if(agg.cityInfo.contains(curr)){
        agg.cityInfo.put(curr,agg.cityInfo.get(curr).get+1)
      }
      //否则，该城市点击次数 = 1
      else{
        agg.cityInfo.put(curr,1)
      }
      //更新总次数
      agg.count = agg.count+1

      agg
    }

    //对预聚合之后的结果再次聚合逻辑[就是UDAF的merge方法]
    val merge = (agg:ProductBuff,curr:ProductBuff) => {
      //更新每个区域每个商品出现的城市数据
      //合并城市数据
      val totalCityInfo = agg.cityInfo.toList ++ curr.cityInfo.toList
      //因为两个task统计的时候里面是可能有相同城市的，所以需要把相同城市次数累加起来
      val cityTotalNum = totalCityInfo.groupBy(_._1)
        .map(x=>{
          //x= (深圳，List( (深圳,5)，(深圳,6)))
          (x._1, x._2.map(_._2).sum)
          //(深圳,11)
        })
      //更新城市数据
      agg.cityInfo.++=(cityTotalNum)

      //更新总次数
      agg.count = agg.count+curr.count

      agg
    }
    //对应UDAF zero方法
    def zero: ProductBuff = ProductBuff(mutable.Map[String,Int](),0)
    //统计每个区域每个商品的所有城市的统计次数
    val aggRdd = mapRdd.aggregateByKey( zero ) (reduce,merge)
    //当前aggRdd = [ ((华北,商品A),ProductBuff(cityInfo=Map(深圳->10,上海->20,广州->12,杭州->5),count=47)]
    //----------------------------------------最终结果--------------------------------------------
    //转化为 区域、商品、点击总次数、城市备注,【此处map里面的函数就是UDAF函数中的finish方法】
    val result = aggRdd.map{
      case ((area,city),productBuf) =>
        //对每个区域、每个商品点击数排序，降序
        val sortedCityList = productBuf.cityInfo.toList.sortBy(_._2).reverse
        //取出每个区域、每个商品点击数最高的两个城市
        val top2City = sortedCityList.take(2)
        //将list数据类型由原来的元组 (城市,城市点击总次数) 转换为字符串 "城市: 城市占比%"
        val cityLvList = top2City.map{
          case (city,num) =>
            //求得每个城市的点击在区域中的占比 = 城市点击总次数 / 区域点击总次数 ，保留2位小数点
            val cityLv = (num.toDouble/productBuf.count * 100).formatted("%.2f")
            s"${city}:${cityLv}%"
        }
        //将前两个城市占比拼接起来[因为最终结果的城市备注字段要求是字符串形式: 城市1:占比%,城市2:占比%,其他:占比%]
        val top2Lv = cityLvList.mkString(",")

        //获取除开点击数最高的两个城市的其他所有城市的点击总次数
        val otherCityNum = sortedCityList.drop(2).map(_._2).sum
        //获取其他城市的占比,保留两位小数点
        val otherLv = (otherCityNum.toDouble/productBuf.count * 100).formatted("%.2f")

        //将前两个城市占比字符串与其他所有城市占比拼接
        val cityMarket = s"${top2Lv},其他:${otherLv}%"
        (area,city,productBuf.count,cityMarket)
    }

    result.foreach(println(_))
  }
}
