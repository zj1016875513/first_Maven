package com.atguigu.demo.service;

import com.atguigu.demo.beans.Employee;

import java.util.List;

public interface EmployeeService {
    Employee getEmployee(Integer id);
    void deleteEmployee(Integer id);
    void insertEmployee(Employee employee);
    void updateEmployee(Employee employee);
    List<Employee> getAll();
}
