package com.atguigu.demo.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by VULCAN on 2021/4/19
 *
 * ORM映射：
 *      数据库的一张表对应一个类
 *         表中的每列，对应类的属性
 *
 *  标准的JavaBean
 *              ①空参构造器
 *              ②为私有属性，提供getter ,setter方法
 */
@Data  //为当前类提供所有私有属性的get,set方法
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

    private Integer id;
    private String lastName;
    private String email;
    private String gender;

    public static void main(String[] args) {

        Employee employee = new Employee(1, "jack", "jack@qq.com", "male");

        Employee emp2 = new Employee();

        emp2.setId(2);

        System.out.println(employee);
    }


}
