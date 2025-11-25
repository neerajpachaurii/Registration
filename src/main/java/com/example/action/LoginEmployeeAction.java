
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

	public void setUsername(String u) {
		this.username = u;
	}

	public void setPassword(String p) {
		this.password = p;
	}

	public void setEmployeeService(EmployeeService s) {
		this.employeeService = s;
	}

	@Override
	public String execute() throws Exception {
		Employee emp = employeeService.login(username, password);
		if (emp == null) {
			addActionError("Invalid username or password");
			return INPUT;
		}

		// Check status: only ACTIVE allowed
		if (emp.getStatus() == null || !"ACTIVE".equalsIgnoreCase(emp.getStatus())) {
			addActionError("Access denied: your account has been deactivated by the administrator.");
			return INPUT;
		}

		HttpSession session = ServletActionContext.getRequest().getSession();
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
