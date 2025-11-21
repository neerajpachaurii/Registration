package com.example.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.struts2.ServletActionContext;
import com.example.model.Employee;
import com.example.service.EmployeeService;
import com.opensymphony.xwork2.ActionSupport;

public class AddEmployeeAction extends ActionSupport {

    @Autowired
    private EmployeeService service;

    private Employee employee;

    public String execute() {

        // 👇👇 DEFAULT ROLE SETTING
        if (employee.getRole() == null || employee.getRole().trim().isEmpty()) {
            employee.setRole("USER");
        }

        service.save(employee);

        // Store employee object to show name on success page
        ServletActionContext.getRequest().setAttribute("employee", employee);

        return SUCCESS;
    }

    public Employee getEmployee() { 
        return employee; 
    }

    public void setEmployee(Employee employee) { 
        this.employee = employee; 
    }

    @Override
    public void validate() {

        if (employee.getUsername() == null || employee.getUsername().trim().isEmpty()) {
            addFieldError("username", "Username required");
        }

        if (employee.getPassword() == null || employee.getPassword().length() < 4) {
            addFieldError("password", "Password must be at least 4 chars");
        }

        if (employee.getEmail() == null || !employee.getEmail().matches("^.+@.+\\..+$")) {
            addFieldError("email", "Valid email required");
        }

        if (service != null && service.employeeExists(employee.getUsername())) {
            addFieldError("username", "Username already taken");
        }
    }
}
