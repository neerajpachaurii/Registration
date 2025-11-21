package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.example.model.Employee;
import com.example.service.EmployeeService;
import com.opensymphony.xwork2.ActionContext;

import java.util.Map;

public class LoginEmployeeAction extends ActionSupport implements ModelDriven<Employee> {

    private Employee employee = new Employee();
    private EmployeeService employeeService;

    // If you use Spring bean injection via XML, setter is needed:
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public Employee getModel() {
        return employee;
    }

    @Override
    public String execute() throws Exception {

        String username = employee.getUsername();
        String password = employee.getPassword();
        String selectedRole = employee.getRole(); // from form select

        if (username == null || username.trim().isEmpty()) {
            addActionError("Username required");
            return INPUT;
        }

        if (!employeeService.employeeExists(username)) {
            addActionError("Employee not found!...PLEASE REGISTER FIRST");
            return "register_first";
        }

        // Authenticate credentials
        if (!employeeService.authenticate(username, password)) {
            addActionError("Invalid username or password");
            return INPUT;
        }

        // Get persisted employee (with DB data)
        Employee persisted = employeeService.findByUsername(username);
        if (persisted == null) {
            addActionError("Internal error: cannot load user");
            return ERROR;
        }

        // --- ROLE HANDLING STRATEGY ---
        // Option A (recommended): Respect DB role (secure)
        // Option B (what you asked): Allow user to select role at login time (less secure)
        //
        // I'll implement Option B but ALSO attach DB role to session so you can decide policy.
        //
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("loggedEmployee", persisted);

        // store both: selectedRole (what user chose now) and realRole (from DB)
        session.put("loginRole", selectedRole != null ? selectedRole : persisted.getRole());
        session.put("realRole", persisted.getRole());

        // If you want to enforce DB-only role, use persisted.getRole() below.
        // Here we use selectedRole (if provided) to decide destination:
        String effectiveRole = (selectedRole != null && !selectedRole.trim().isEmpty()) ? selectedRole : persisted.getRole();

        if ("ADMIN".equalsIgnoreCase(effectiveRole)) {
            return "adminHome";      // map in struts.xml
        } else {
            return "employeeHome";   // map in struts.xml
        }
    }

    @Override
    public void validate() {
        if (employee.getUsername() == null || employee.getUsername().trim().isEmpty()) {
            addFieldError("username", "Username required");
        }
        if (employee.getPassword() == null || employee.getPassword().trim().isEmpty()) {
            addFieldError("password", "Password required");
        }
    }
}
