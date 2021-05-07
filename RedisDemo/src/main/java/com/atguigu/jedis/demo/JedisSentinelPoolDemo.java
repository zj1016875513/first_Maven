package com.atguigu.jedis.demo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;

/**
 * Created by VULCAN on 2021/4/19
 */
public class JedisSentinelPoolDemo {

    public static void main(String[] args) {

        //先定义连接池的配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        //设置池中的最大的连接数
        jedisPoolConfig.setMaxTotal(50);
        //设置池中的最大的空闲连接数
        jedisPoolConfig.setMaxIdle(30);

        //线程从池中获取连接时，是否进行一次ping的测试，保证连接是好使的
        jedisPoolConfig.setTestOnBorrow(true);

        // 客户端最大的等待时间，时间到后，如果依然无法获取到资源，就抛异常
        jedisPoolConfig.setMaxWaitMillis(10000);

        // 当池中连接耗尽时，是否阻塞客户端，让客户端等一会，如果为false，直接抛出异常
        jedisPoolConfig.setBlockWhenExhausted(true);

        //先创建一个支持哨兵模式的连接池
        /*
        public JedisSentinelPool(String masterName, Set<String> sentinels,
      final GenericObjectPoolConfig poolConfig)
                masterName: 监控的主机的别名
                 Set<String> sentinels : 哨兵绑定的主机名和端口号的集合
                 GenericObjectPoolConfig poolConfig: 连接池配置
         */

        HashSet<String> sentinels = new HashSet<String>();

        sentinels.add("hadoop102:26379");

        JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster", sentinels, jedisPoolConfig);

        //从池中获取一个连接
        Jedis jedis = sentinelPool.getResource();

        jedis.set("jedispool21", "jedispool1");

        System.out.println(jedis.get("jedispool1"));

        // 用完后归还到池中
        jedis.close();


    }
}
