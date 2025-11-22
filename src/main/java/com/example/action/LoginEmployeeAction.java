//package com.example.action;
//
//import com.opensymphony.xwork2.ActionSupport;
//import com.opensymphony.xwork2.ModelDriven;
//
//import com.example.model.Employee;
//import com.example.service.EmployeeService;
//
//import com.opensymphony.xwork2.ActionContext;
//
//import java.util.Map;
//
//public class LoginEmployeeAction extends ActionSupport implements ModelDriven<Employee> {
//
//    private Employee employee = new Employee();
//    private EmployeeService employeeService;
//
//    public void setEmployeeService(EmployeeService s) {
//        this.employeeService = s;
//    }
//
//    public Employee getModel() {
//        return employee;
//    }
//
//    public String execute() throws Exception {
//
//        if (!employeeService.employeeExists(employee.getUsername())) {
//            addActionError("Employee not found. PLEASE REGISTER FIRST.");
//            return "register_first";
//        }
//
//        Employee empObj = employeeService.login(employee.getUsername(), employee.getPassword());
//
//        if (empObj == null) {
//            addActionError("Invalid username or password");
//            return INPUT;
//        }
//
//        Map session = ActionContext.getContext().getSession();
//        session.put("loggedEmployee", empObj);
//
//        if ("ADMIN".equals(empObj.getRole())) {
//            return "adminHome";
//        }
//
//        return "employeeHome";
//    }
//
//    public void validate() {
//
//        if (employee.getUsername() == null || employee.getUsername().trim().equals("")) {
//            addFieldError("username", "Username required");
//        }
//
//        if (employee.getPassword() == null || employee.getPassword().trim().equals("")) {
//            addFieldError("password", "Password required");
//        }
//    }
//}

package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import com.example.service.EmployeeService;
import com.example.model.Employee;

import javax.servlet.http.HttpSession;

public class LoginEmployeeAction extends ActionSupport {
    private String username;
    private String password;
    private EmployeeService employeeService;

    public void setUsername(String u){ this.username = u; }
    public void setPassword(String p){ this.password = p; }
    public void setEmployeeService(EmployeeService s){ this.employeeService = s; }

    @Override
    public String execute() throws Exception {
        Employee emp = employeeService.login(username, password);
        if (emp == null) {
            addActionError("Invalid credentials");
            return INPUT;
        }

        HttpSession session = ServletActionContext.getRequest().getSession();
        // IMPORTANT: use "loggedEmployee" everywhere
        session.setAttribute("loggedEmployee", emp);
        session.setAttribute("loginRole", emp.getRole());
        session.setAttribute("department", emp.getDepartment());

        if ("ADMIN".equalsIgnoreCase(emp.getRole())) {
            return "adminHome";
        } else {
            return "employeeHome";
        }
    }
}

