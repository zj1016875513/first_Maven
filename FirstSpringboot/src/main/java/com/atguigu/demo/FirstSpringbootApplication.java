package com.atguigu.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages="com.atguigu.demo.mappers")
public class FirstSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstSpringbootApplication.class, args);
    }

}
