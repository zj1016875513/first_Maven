package com.atguigu.demo.service;

import com.atguigu.demo.beans.Employee;
import com.atguigu.demo.mappers.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceimpl implements EmployeeService{

    @Autowired
    private EmployeeMapper empDao;

    @Override
    public Employee getEmployee(Integer id) {
        Employee employee = empDao.getEmployee(id);
        return employee;
    }

    @Override
    public void deleteEmployee(Integer id) {
        empDao.deleteEmployee(id);
    }

    @Override
    public void insertEmployee(Employee employee) {
        empDao.insertEmployee(employee);
    }

    @Override
    public void updateEmployee(Employee employee) {
        empDao.updateEmployee(employee);
    }

    @Override
    public List<Employee> getAll() {
        return empDao.getAll();
    }
}
