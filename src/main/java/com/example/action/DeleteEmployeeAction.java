package com.example.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.service.EmployeeService;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteEmployeeAction extends ActionSupport {

    @Autowired
    private EmployeeService service;

    private int id;

    public String execute() {
        service.delete(id);
        return SUCCESS;
    }

    public void setId(int id) { this.id = id; }
}

