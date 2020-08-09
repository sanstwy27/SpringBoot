package com.atkjs927.springboot.mapper;

import com.atkjs927.springboot.bean.Employee;
import org.springframework.stereotype.Repository;

@Repository
//Use @Mapper or @MapperScan to IoC Container
public interface EmployeeMapper {

    public Employee getEmpById(Integer id);

    public void insertEmp(Employee employee);
}
