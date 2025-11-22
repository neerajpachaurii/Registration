package com.example.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.example.model.Employee;
import com.example.service.EmployeeService;

public class UpdateEmployeeAction extends ActionSupport {

    @Autowired
    private EmployeeService service;

    private Employee employee;

    public void setEmployee(Employee e){ this.employee = e; }
    public Employee getEmployee(){ return employee; }

    public String execute() {
        service.update(employee);
        return SUCCESS;
    }
}
