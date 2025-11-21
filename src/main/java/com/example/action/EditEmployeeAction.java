package com.example.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.model.Employee;
import com.example.service.EmployeeService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import java.util.Map;

public class EditEmployeeAction extends ActionSupport {

    @Autowired
    private EmployeeService service;

    private int id;
    private Employee employee;

    public String execute() {

        // get logged-in employee from session
        Map<String, Object> session = ActionContext.getContext().getSession();
        Employee logged = (Employee) session.get("loggedEmployee");

        if (logged == null) {
            // not logged in
            addActionError("Please login first.");
            return "login"; // make sure your struts.xml maps 'login' result (or change to appropriate)
        }

        // if not admin and trying to edit someone else's record -> deny
        if (!"ADMIN".equalsIgnoreCase(logged.getRole()) && logged.getId() != id) {
            addActionError("You are not authorized to edit other users!");
            return "accessDenied"; // map this result (see note below)
        }

        // otherwise load the requested employee for editing
        employee = service.getById(id);
        if (employee == null) {
            addActionError("Employee not found.");
            return "input";
        }

        return SUCCESS;
    }

    // GETTER SETTER
    public void setId(int id) { this.id = id; }
    public Employee getEmployee() { return employee; }

    // optionally provide setter for service if not using @Autowired via component-scan
    public void setService(EmployeeService service) { this.service = service; }
}
