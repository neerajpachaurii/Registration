package com.example.action;

import com.example.model.Employee;
import com.example.service.EmployeeService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

public class UpdateEmployeeStatusAction extends ActionSupport {

	private EmployeeService employeeService;

	private int id;
	private String status;

	public void setEmployeeService(EmployeeService s) {
		this.employeeService = s;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String execute() throws Exception {

		Object logged = ServletActionContext.getRequest().getSession().getAttribute("loggedEmployee");
		if (logged == null || !(logged instanceof Employee) || !"ADMIN".equals(((Employee) logged).getRole())) {
			addActionError("Access denied");
			return "accessDenied";
		}

		try {

			Employee emp = employeeService.getById(id);
			if (emp == null) {
				addActionError("Employee not found (id=" + id + ")");
				return ERROR;
			}

			String newStatus = (status == null) ? "INACTIVE" : status.trim().toUpperCase();
			if (!"ACTIVE".equals(newStatus) && !"INACTIVE".equals(newStatus)) {
				addActionError("Invalid status value");
				return ERROR;
			}
			employeeService.updateStatus(id, newStatus);

			addActionMessage("Employee status updated to " + newStatus + " for user: " + emp.getName());
			return SUCCESS;

		} catch (Exception ex) {
			ex.printStackTrace();
			addActionError("Error updating status: " + ex.getMessage());
			return ERROR;
		}
	}
}
