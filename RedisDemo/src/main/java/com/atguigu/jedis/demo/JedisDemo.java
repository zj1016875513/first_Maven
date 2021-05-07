package com.atguigu.jedis.demo;

import redis.clients.jedis.Jedis;

/**
 * Created by VULCAN on 2021/4/19
 */
public class JedisDemo {

    //   redis-cli  -h hadoop103 -p 6379
    public static void main(String[] args) {

        // 要写redisserver配置文件中bind后的主机名
        Jedis jedis = new Jedis("hadoop102", 6379);

        String pong = jedis.ping();

        System.out.println(pong);

        String result = jedis.set("jedis", "jedis");

        System.out.println(result);

        System.out.println(jedis.get("jedis"));

        jedis.close();

    }

}
