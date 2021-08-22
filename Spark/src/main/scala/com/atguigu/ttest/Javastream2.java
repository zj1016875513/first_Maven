package com.atguigu.ttest;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
@AllArgsConstructor
class Goods{
    private int GoodNO;
    private String GoodName;
    private String GoodType;
    private Double GoodPrice;
    private Double GoodPriceAll;
    private int GoodNum;
}

interface GoodTypeEnum{
    String DIGITAL="DIGITAL";
    String APPAREL="APPAREL";
    String BOOKS="BOOKS";
    String SPORTS="SPORTS";
}

public class Javastream2 {
    /**
     * 模拟测试数据
     */
    private static List<Goods> goodsList= new ArrayList<Goods>(){
        {
            add(new Goods(111,"无人机", GoodTypeEnum.DIGITAL,10000.00, 10000.00,1));
            add(new Goods(112,"VR一体机", GoodTypeEnum.DIGITAL,13000.00, 13000.00,1));
            add(new Goods(113,"衬衫", GoodTypeEnum.APPAREL,100.00, 300.00,3));
            add(new Goods(114,"牛仔裤", GoodTypeEnum.APPAREL,120.00, 120.00,1));
            add(new Goods(115,"Java编程思想", GoodTypeEnum.BOOKS,80.00, 80.00,1));
            add(new Goods(116,"Java核心技术", GoodTypeEnum.BOOKS,90.00, 90.00,1));
            add(new Goods(117,"算法", GoodTypeEnum.BOOKS,60.00, 60.00,1));
            add(new Goods(118,"跑步机", GoodTypeEnum.SPORTS,3600.00, 3600.00,1));

        }
    };
    /**
     * forEach 来迭代流中的每个元素
     */
    @Test
    public void forEachTest(){
        goodsList.stream()
                .forEach(goods->System.out.println(JSON.toJSONString(goods)));
    }

    /**
     * filter 通过设置的条件过滤出元素
     */
    @Test
    public void filterTest(){
        goodsList.stream()
                // 过滤出商品中的图书类
                .filter(goods-> GoodTypeEnum.BOOKS.equals(goods.getGoodType()))
                .forEach(goods->System.out.println(JSON.toJSONString(goods)));
    }
    /**
     * map 映射每个元素到对应的结果
     */
    @Test
    public void mapTest(){
        goodsList.stream()
                // 将商品中名字映射到结果中
                .map(goods-> goods.getGoodName())
                .forEach(goods->System.out.println(JSON.toJSONString(goods,true)));
    }

    /**
     * flatMap 将一个对象转换成流
     */
    @Test
    public void flatMapTest(){
        goodsList.stream()
                // 将商品中名字转换成流
                .flatMap(goods-> Arrays.stream(goods.getGoodName().split("")))
                .forEach(goods->System.out.println(JSON.toJSONString(goods,true)));
    }
    /**
     * peek 来迭代流中的每个元素，与forEach相似,但不会销毁流元素
     */
    @Test
    public void peekTest(){
        goodsList.stream()
                // 迭代商品中的商品名字
                .peek(goods-> System.out.println(goods.getGoodName()))
                .forEach(goods->System.out.println(JSON.toJSONString(goods,true)));
    }

    /**
     * sorted 对流中的元素进行排序 可选择自然排序或者排序规则
     */
    @Test
    public void sortedTest(){
        goodsList.stream()
                // 按商品的价格进行排序
                .sorted(Comparator.comparing(goods -> goods.getGoodPrice()))
                .forEach(goods->System.out.println(JSON.toJSONString(goods)));
    }

    /**
     * distinct 对流中的元素去重
     */
    @Test
    public void distinctTest(){
        goodsList.stream()
                // 将商品类型映射到结果中
                .map(goods ->goods.getGoodType())
                //去重
                .distinct()
                .forEach(goods->System.out.println(JSON.toJSONString(goods)));
    }

    /**
     * skip 跳过前N条元素
     */
    @Test
    public void skipTest(){
        goodsList.stream()
                // 按商品的价格进行排序,
                .sorted(Comparator.comparing(goods -> goods.getGoodPrice()))
                // 跳过前两条
                .skip(2)
                .forEach(goods->System.out.println(JSON.toJSONString(goods)));
    }

    /**
     * limit 截断前N条元素
     */
    @Test
    public void limitTest(){
        goodsList.stream()
                // 按商品的价格进行排序,
                .sorted(Comparator.comparing(goods -> goods.getGoodPrice()))
                // 截断前两条
                .limit(2)
                .forEach(goods->System.out.println(JSON.toJSONString(goods)));
    }


    /***********************************************/


    /**
     *allMatch  必须全部都满足才会返回true
     */
    @Test
    public void allMatchTest(){
        boolean allMatch =goodsList.stream()
                .peek(goods->System.out.println(JSON.toJSONString(goods)))
                // 商品单价大于500
                .allMatch(goods -> goods.getGoodPrice()>500);
        System.out.println(allMatch);
    }


    /**
     *anyMatch  只要有一个条件满足即返回true
     */
    @Test
    public void anyMatchTest(){
        boolean allMatch =goodsList.stream()
                .peek(goods->System.out.println(JSON.toJSONString(goods)))
                // 商品单价大于1000
                .anyMatch(goods -> goods.getGoodPrice()>1000);
        System.out.println(allMatch);
    }


    /**
     *noneMatch  全都不满足才会返回true
     */
    @Test
    public void noneMatchTest(){
        boolean allMatch =goodsList.stream()
                .peek(goods->System.out.println(JSON.toJSONString(goods)))
                // 商品单价大于10000
                .noneMatch(goods -> goods.getGoodPrice()>10000);
        System.out.println(allMatch);
    }


    /**
     *findFirst  找到第一个元素
     */
    @Test
    public void findFirstTest(){
        Optional optional =goodsList.stream()
                .findFirst();
        System.out.println(JSON.toJSONString(optional.get()));
    }

    /**
     *findAny  找到任意一个元素
     */
    @Test
    public void findAnyTest(){
        for (int i = 0; i < 20; i++) {
            Optional optional =goodsList.stream()
                    .findAny();
            System.out.println(JSON.toJSONString(optional.get()));
        }

    }

    /**
     * mapToInt/mapToLong/mapToDouble  主要用于int、double、long等基本类型上，进行统计结果
     */
    @Test
    public void mapToXXTest(){
        DoubleSummaryStatistics stats = goodsList.stream()
                // 将商品价格映射到流中
                .map(goods ->goods.getGoodPrice())
                .mapToDouble((x)-> x).summaryStatistics();
        System.out.println("商品中价格最贵的商品 : " + stats.getMax());
        System.out.println("商品中价格最便宜的商品 : " + stats.getMin());
        System.out.println("所有商品的价格之和 : " + stats.getSum());
        System.out.println("商品的平均数 : " + stats.getAverage());
    }

    /**
     * @author fangliu
     * @date 2020-02-14
     * @description 流的四种构建形式
     */
    public class StreamConstructor {

        /**
         * 由值创建流
         */
        @Test
        public void streamFromValue(){
            Stream stream = Stream.of(1, 2, 3, 4, 5, 6);
            stream.forEach(System.out::println);
        }
        /**
         * 由数组创建流
         */
        @Test
        public void streamFromArrays(){
            int[] numbers = {1, 2, 3, 4, 5, 6};
            IntStream stream = Arrays.stream(numbers);
            stream.forEach(System.out::println);
        }
        /**
         * 由文件创建流
         */
        @Test
        public void streamFromFiles() throws IOException {
            String path = "/Users/fangliu/data/workspace/study/src/test/java/com/example/demo/stream/StreamConstructor.java";
            Stream stream = Files.lines(Paths.get(path));
            stream.forEach(System.out::println);
        }
        /**
         * 由函数生产流(无限流)
         */
        @Test
        public void streamFromValue1(){
            //Stream stream = Stream.iterate(0,n->n+2);
            Stream stream = Stream.generate(Math::random);
            stream.limit(100).forEach(System.out::println);
        }

    }


    /**
     *  collect Collectors类实现了很多归约操作，例如将流转换成集合和聚合元素。Collectors 可用于返回列表或字符串
     */
    @Test
    public void collectTest(){
        // 将商品价格大于1500的转换成商品集合
        List<Goods> list=goodsList.stream()
                .filter(goods -> goods.getGoodPrice()>1500)
                .collect(Collectors.toList());
        System.out.println(list);

        //将商品名用逗号拼接成字符串
        String str=goodsList.stream()
                .map(goods -> goods.getGoodName())
                .collect(Collectors.joining(","));
        System.out.println(str);

        // Map<分组条件，结果集合>
        Map<Object,List<Goods>> group=goodsList.stream()
                //按照商品类型分组
                .collect(Collectors.groupingBy(goods -> goods.getGoodType()));
        System.out.println(JSON.toJSONString(group,true));

        // Map<分区条件，结果集合> 分区是分组的一个特例
        Map<Boolean,List<Goods>> partition=goodsList.stream()
                //商品大于1000的商品归为true 其余false
                .collect(Collectors.partitioningBy(goods -> goods.getGoodPrice()>1000));
        System.out.println(JSON.toJSONString(partition,true));
    }

}
