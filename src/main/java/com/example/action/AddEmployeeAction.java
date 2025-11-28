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

//    public void validate() {
//
//        if (employee.getUsername() == null || employee.getUsername().trim().equals("")) {
//            addFieldError("username", "Username required");
//        }
//
//        if (service.employeeExists(employee.getUsername())) {
//            addFieldError("username", "Username already exists");
//        }
//    } 
//    islye htaya h kyuki ye jab run horha tha tb addEmployee.action se page crash horha tha but addEmployee.jsp likne
//        se chlra tha index.jsp mein
    
    public void validate() {

        if (employee == null) {
            addActionError("Employee object is null");
            return;
        }

        String username = employee.getUsername();

        if (username == null || username.trim().isEmpty()) {
            addFieldError("username", "Username required");
            return;   // already null hai
        }

        if (service.employeeExists(username)) {
            addFieldError("username", "Username already exists");
        }
    }

}
