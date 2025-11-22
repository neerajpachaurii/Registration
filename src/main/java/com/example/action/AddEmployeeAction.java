package com.example.action;

import org.springframework.beans.factory.annotation.Autowired;
import com.opensymphony.xwork2.ActionSupport;

import com.example.model.Employee;
import com.example.service.EmployeeService;

public class AddEmployeeAction extends ActionSupport {

    @Autowired
    private EmployeeService service;

    private Employee employee;

    public Employee getEmployee(){ return employee; }
    public void setEmployee(Employee e){ this.employee = e; }

    public String execute() {

        if (employee.getRole() == null || "".equals(employee.getRole().trim())) {
            employee.setRole("USER");
        }

        service.save(employee);

        return SUCCESS;
    }

    public void validate() {

        if (employee.getUsername() == null || employee.getUsername().trim().equals("")) {
            addFieldError("username", "Username required");
        }

        if (service.employeeExists(employee.getUsername())) {
            addFieldError("username", "Username already exists");
        }
    }
}
