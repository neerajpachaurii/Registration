package com.example.action;

import java.util.*;
import com.example.model.Employee;
import com.example.service.EmployeeService;

public class EmployeeDwr {

    private EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    public EmployeeService getEmployeeService() {
        return this.employeeService;
    }

    public List<Map<String,Object>> getEmployees() {
        List<Employee> list = employeeService.getAll();
        List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>();
        if (list == null) return rows;
        for (Employee e : list) {
            Map<String,Object> m = new HashMap<String,Object>();
            m.put("id", e.getId());
            m.put("name", e.getName());
            m.put("email", e.getEmail());
            m.put("department", e.getDepartment());
            m.put("salary", e.getSalary());
            m.put("status", e.getStatus());
            rows.add(m);
        }
        return rows;
    }
}
