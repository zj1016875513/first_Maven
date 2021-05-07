package com.atguigu.demo.controller;

import com.atguigu.demo.beans.Employee;
import com.atguigu.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService empService;


    @RequestMapping(value = "/getAll")
    public List<Employee> handle(){
        List<Employee> emps = empService.getAll();
        return emps;
    }


    @RequestMapping(value = "/emp")
    public Object handleEmp(String op,String lastName,int id,String gender,String email){
        Employee emp = new Employee(id, lastName, email, gender);
        switch (op){
            case "select":
                Employee employee = empService.getEmployee(id);
                if(employee==null)
                    return "查无此人";
                else
                    return employee;

            case "insert":
                empService.insertEmployee(emp);
                return "success";

            case "update":
                empService.updateEmployee(emp);
                return "success";

            case "delete":
                empService.deleteEmployee(id);
                return "success";

            default: return "输入操作有误";
        }
    }

}
