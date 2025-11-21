package com.example.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.model.Employee;
import com.example.service.EmployeeService;
import com.opensymphony.xwork2.ActionSupport;

public class UpdateEmployeeAction extends ActionSupport {

    @Autowired
    private EmployeeService service;

    private Employee employee;

    public String execute() {
        service.update(employee);
        return SUCCESS;
    }
    

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
}
