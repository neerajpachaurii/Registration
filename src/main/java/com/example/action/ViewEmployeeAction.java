package com.example.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.model.Employee;
import com.example.service.EmployeeService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ViewEmployeeAction extends ActionSupport {

    // Prefer setter injection for actions (Struts+Spring) — @Autowired kept as well
    @Autowired
    private EmployeeService service;

    private List<Employee> employees;

    public ViewEmployeeAction() {}

    // Setter so Spring XML can inject if annotation doesn't run for actions
    public void setService(EmployeeService service) {
        this.service = service;
    }

    @Override
    public String execute() {
        // Defensive: ensure service injected
        if (service == null) {
            addActionError("Internal error: EmployeeService not available. Check Spring config.");
            return ERROR;
        }

        Map<String, Object> session = ActionContext.getContext().getSession();
        if (session == null) {
            addActionError("Session unavailable. Please login.");
            return LOGIN;
        }

        Employee logged = (Employee) session.get("loggedEmployee");
        if (logged == null) {
            addActionError("Please login first.");
            return LOGIN;
        }

        // Debugging help (temporary): uncomment to log role to server logs
        // System.out.println("DEBUG: logged role = '" + logged.getRole() + "'; username = " + logged.getUsername());

        // ADMIN -> show all employees, USER -> only own record
        if ("ADMIN".equalsIgnoreCase(logged.getRole())) {
            employees = service.getAll();
            if (employees == null) employees = new ArrayList<>();
        } else {
            employees = new ArrayList<>();
            Employee me = service.getById(logged.getId());
            if (me != null) employees.add(me);
        }

        return SUCCESS;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
