package com.atguigu.demo.mappers;

import com.atguigu.demo.beans.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmployeeMapper {

    Employee getEmployee(Integer id);
    void deleteEmployee(Integer id);
    void insertEmployee(Employee employee);
    void updateEmployee(Employee employee);
    List<Employee> getAll();
}
