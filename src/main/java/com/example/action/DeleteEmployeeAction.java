package com.example.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.example.service.EmployeeService;

public class DeleteEmployeeAction extends ActionSupport {

    @Autowired
    private EmployeeService service;

    private int id;

    public void setId(int id){ this.id = id; }

    public String execute() {
        service.delete(id);
        return SUCCESS;
    }
}
