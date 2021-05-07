package com.atguigu.demo.controller;

import com.atguigu.demo.beans.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Hellocontroller {

    @RequestMapping(value ="/hello2")
    public String handle2(){
        System.out.println("处理了hello2请求");
        return "double";
    }

    @ResponseBody
    @RequestMapping(value ="/hello3")
    public String handle3(){
        System.out.println("处理了hello3请求");
        return "three";
    }

    @ResponseBody
    @RequestMapping(value ="/hello4")
    public Employee handle4(){
        Employee employee = new Employee(1, "TOM", "TOM@qq.com", "male");
        System.out.println("处理了hello4请求");
        return employee;
    }

    @RequestMapping(value ="/hello5")
    public String handle5(String name,int age){
        System.out.println("处理了hello5请求,name="+name+";age="+age);
        return "double";
    }



}


