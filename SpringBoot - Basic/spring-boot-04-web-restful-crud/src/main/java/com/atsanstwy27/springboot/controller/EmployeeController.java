package com.atkjs927.springboot.controller;

import com.atkjs927.springboot.dao.DepartmentDao;
import com.atkjs927.springboot.dao.EmployeeDao;
import com.atkjs927.springboot.entities.Department;
import com.atkjs927.springboot.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    DepartmentDao departmentDao;

    // Query Employees and Redirect to List Page
    @GetMapping("/emps")
    public String list(Model model){
        Collection<Employee> employees = employeeDao.getAll();

        // Pass to Template(thymeleaf)
        model.addAttribute("emps", employees);
        // classpath:/templates/xxxx.html
        return "emp/list";
    }

    // to Add Page
    @GetMapping("/emp")
    public String toAddPage(Model model){
        // Generate Dept. List
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        return "emp/add";
    }

    // Add Employee
    // SpringMVC auto-binding, if req-params name == javaBean name
    @PostMapping("/emp")
    public String addEmp(Employee employee){
        System.out.println("Save Emp: "+employee);
        employeeDao.save(employee);
        // redirect: isn't associated with the original request (to avoid committing again)
        // forward: associated with the original request
        return "redirect:/emps";
    }

    // to Edit Page
    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id, Model model){
        Employee employee = employeeDao.get(id);
        model.addAttribute("emp",employee);

        // Generate Dept. List
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        // to Add Page
        return "emp/add";
    }

    // Edit Emp
    @PutMapping("/emp")
    public String updateEmployee(Employee employee){
        System.out.println("Edit Emp: " + employee);
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    // Delete Emp
    @DeleteMapping("/emp/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id){
        employeeDao.delete(id);
        return "redirect:/emps";
    }
}
