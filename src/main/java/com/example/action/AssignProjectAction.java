package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.service.ProjectService;
import com.example.service.EmployeeService;
import com.example.model.Employee;
import com.example.model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AssignProjectAction extends ActionSupport {

	// ----- input/output fields -----
	private Integer projectId; // incoming project id
	private Integer[] userIds; // selected checkboxes (multiple)
	private Integer removeEmployeeId; // remove single assignment (optional)

	private List<Employee> allEmployees; // for rendering
	private List<Integer> allowedIds; // currently assigned ids
	private String projectTitle;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private EmployeeService employeeService;

	// --- setters/getters for Struts to populate / read ---
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setUserIds(Integer[] userIds) {
		this.userIds = userIds;
	}

	public Integer[] getUserIds() {
		return userIds;
	}

	public void setRemoveEmployeeId(Integer removeEmployeeId) {
		this.removeEmployeeId = removeEmployeeId;
	}

	public Integer getRemoveEmployeeId() {
		return removeEmployeeId;
	}

	public List<Employee> getAllEmployees() {
		return allEmployees;
	}

	public List<Integer> getAllowedIds() {
		return allowedIds;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public String input() throws Exception {

		Map<String, Object> session = ActionContext.getContext().getSession();
		Employee admin = (Employee) session.get("loggedEmployee");
		if (admin == null || !"ADMIN".equalsIgnoreCase(admin.getRole())) {
			addActionError("Access denied");
			return "accessDenied";
		}

		if (projectId == null) {
			addActionError("Project id required");
			return "accessDenied";
		}

		Project p = projectService.getById(projectId);
		if (p == null) {
			addActionError("Project not found");
			return "accessDenied";
		}

		// load page data
		allEmployees = employeeService.getAll();
		allowedIds = projectService.getAssignedEmployeeIds(projectId);
		if (allowedIds == null)
			allowedIds = new ArrayList<Integer>();
		projectTitle = p.getTitle();

		return INPUT;
	}

	@Override
	public String execute() throws Exception {

		Map<String, Object> session = ActionContext.getContext().getSession();
		Employee admin = (Employee) session.get("loggedEmployee");
		if (admin == null || !"ADMIN".equalsIgnoreCase(admin.getRole())) {
			addActionError("Access denied");
			return "accessDenied";
		}

		if (projectId == null) {
			addActionError("Project id required");
			return ERROR;
		}

		try {

			if (removeEmployeeId != null) {
				projectService.removeAssignment(projectId.intValue(), removeEmployeeId.intValue());
				return SUCCESS;
			}

			List<Integer> ids = new ArrayList<Integer>();
			if (userIds != null) {
				for (Integer i : userIds) {
					if (i != null)
						ids.add(i);
				}
			}

			projectService.replaceAssignedEmployeeIds(projectId.intValue(), ids);

			return SUCCESS;

		} catch (Exception ex) {
			ex.printStackTrace();
			addActionError("Failed to update assignments: " + ex.getMessage());
			return ERROR;
		}
	}
}
