package com.example.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import com.example.model.Employee;
import com.example.service.EmployeeService;

import java.util.Map;

public class EditEmployeeAction extends ActionSupport {

    @Autowired
    private EmployeeService service;

    private int id;
    private Employee employee;

    public void setId(int id){ this.id = id; }
    public Employee getEmployee(){ return employee; }

    public String execute() {

        Map session = ActionContext.getContext().getSession();
        Employee logged = (Employee) session.get("loggedEmployee");

        if (logged == null) return "login";

        if (!"ADMIN".equals(logged.getRole()) && logged.getId() != id) {
            return "accessDenied";
        }

        employee = service.getById(id);
        if (employee == null) return INPUT;

        return SUCCESS;
    }
}
