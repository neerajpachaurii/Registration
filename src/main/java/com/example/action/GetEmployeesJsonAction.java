//package com.example.action;
//
//import com.example.model.Employee;
//import com.example.service.EmployeeService;
//import com.opensymphony.xwork2.ActionSupport;
//import org.apache.struts2.ServletActionContext;
//
//import java.util.*;
//
//public class GetEmployeesJsonAction extends ActionSupport {
//
//    private EmployeeService employeeService;
//
//    private List<Map<String, Object>> rows;
//
//    public void setEmployeeService(EmployeeService employeeService) {
//        this.employeeService = employeeService;
//    }
//
//    public List<Map<String, Object>> getRows() {
//        return rows;
//    }
//
//    @Override
//    public String execute() {
//        Employee logged = (Employee) ServletActionContext.getRequest()
//                .getSession().getAttribute("loggedEmployee");
//
//        if (logged == null) {
//            return ERROR;
//        }
//
//        List<Employee> list = employeeService.getAll();
//        rows = new ArrayList<>();
//
//        for (Employee e : list) {
//            Map<String, Object> m = new HashMap<>();
//            m.put("id", e.getId());
//            m.put("name", e.getName());
//            m.put("email", e.getEmail());
//            m.put("department", e.getDepartment());
//            m.put("salary", e.getSalary());
//            m.put("status", e.getStatus());
//            rows.add(m);
//        }
//
//        return SUCCESS;
//    }
//}


package com.example.action;

import com.example.model.Employee;
import com.example.service.EmployeeService;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.struts2.ServletActionContext;

import java.util.*;

public class GetEmployeesJsonAction extends ActionSupport {
    private EmployeeService employeeService;

    private int start = 0;
    private int limit = 10;

    private String name;
    private String department;
    private String email;

    private Map<String,Object> result = new HashMap<String,Object>();

    @Autowired(required = false)
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void setStart(int start) { this.start = start; }
    public void setLimit(int limit) { this.limit = limit; }
    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setEmail(String email) { this.email = email; }

    public Map<String,Object> getResult(){ return result; }

    @Override
    public String execute() throws Exception {
        // session guard (optional)
        Employee logged = (Employee) ServletActionContext.getRequest().getSession().getAttribute("loggedEmployee");
        if (logged == null) {
            result.put("rows", Collections.emptyList());
            result.put("totalCount", 0);
            return SUCCESS;
        }

        List<Employee> all = employeeService.getAll();
        if (all == null) all = new ArrayList<Employee>();

        String n = name != null ? name.trim().toLowerCase() : null;
        String d = department != null ? department.trim().toLowerCase() : null;
        String e = email != null ? email.trim().toLowerCase() : null;

        List<Employee> filtered = new ArrayList<>();
        for (Employee emp : all) {
            boolean keep = true;
            if (n != null && !n.isEmpty()) {
                String val = emp.getName() == null ? "" : emp.getName().toLowerCase();
                String mail = emp.getEmail() == null ? "" : emp.getEmail().toLowerCase();
                if (!(val.contains(n) || mail.contains(n))) keep = false;
            }
            if (keep && d != null && !d.isEmpty()) {
                String val = emp.getDepartment() == null ? "" : emp.getDepartment().toLowerCase();
                if (!val.contains(d)) keep = false;
            }
            if (keep && e != null && !e.isEmpty()) {
                String mail = emp.getEmail() == null ? "" : emp.getEmail().toLowerCase();
                if (!mail.contains(e)) keep = false;
            }
            if (keep) filtered.add(emp);
        }

        int totalCount = filtered.size();

        if (limit <= 0) limit = 10;
        if (start < 0) start = 0;
        int from = Math.min(start, totalCount);
        int to = Math.min(start + limit, totalCount);
        List<Employee> page = (from < to) ? filtered.subList(from, to) : new ArrayList<Employee>();

        List<Map<String,Object>> rows = new ArrayList<>();
        for (Employee emp : page) {
            Map<String,Object> m = new HashMap<>();
            m.put("id", emp.getId());
            m.put("name", emp.getName());
            m.put("email", emp.getEmail());
            m.put("department", emp.getDepartment());
            m.put("salary", emp.getSalary());
            m.put("status", emp.getStatus());
            rows.add(m);
        }

        Map<String,Object> wrapper = new HashMap<>();
        wrapper.put("rows", rows);
        wrapper.put("totalCount", totalCount);
        this.result = wrapper;

        return SUCCESS;
    }
}
