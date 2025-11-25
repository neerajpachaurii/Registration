package com.example.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.example.model.Employee;
import com.example.model.Project;
import com.example.service.EmployeeService;
import com.example.service.ProjectService;
import com.opensymphony.xwork2.ActionSupport;

public class AdminDashboardAction extends ActionSupport {

	private EmployeeService employeeService;
	private ProjectService projectService;

	public void setEmployeeService(EmployeeService s) {
		this.employeeService = s;
	}

	public void setProjectService(ProjectService s) {
		this.projectService = s;
	}

	// OUTPUT LISTS
	private List<Employee> employees;
	private List<Project> projects;

	// SEARCH INPUTS
	private String searchTitle;
	private String searchOwner;
	private String searchDept;
	private String searchEmail;

	// PAGINATION
	private int page = 1;
	private int pageSize = 5;
	private int totalPages;

	// Stream for Excel export (Struts stream result will use this)
	private InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setSearchTitle(String s) {
		this.searchTitle = s;
	}

	public void setSearchOwner(String s) {
		this.searchOwner = s;
	}

	public void setSearchDept(String s) {
		this.searchDept = s;
	}

	public void setSearchEmail(String s) {
		this.searchEmail = s;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPage() {
		return page;
	}

	public int getTotalPages() {
		return totalPages;
	}

	@Override
	public String execute() throws Exception {

		Employee admin = (Employee) ServletActionContext.getRequest().getSession().getAttribute("loggedEmployee");

		if (admin == null || !"ADMIN".equals(admin.getRole())) {
			return "login";
		}

		// LOAD ALL
		List<Project> allProjects = projectService.getAllProjects();
		List<Employee> allEmployees = employeeService.getAll();

		if (allProjects == null)
			allProjects = new ArrayList<Project>();
		if (allEmployees == null)
			allEmployees = new ArrayList<Employee>();

		// FILTER PROJECTS
		List<Project> filteredProjects = new ArrayList<Project>();
		for (Project p : allProjects) {
			boolean match = true;

			if (searchTitle != null && !"".equals(searchTitle.trim())) {
				if (p.getTitle() == null || !p.getTitle().toLowerCase().contains(searchTitle.toLowerCase())) {
					match = false;
				}
			}

			if (searchOwner != null && !"".equals(searchOwner.trim())) {
				String owner = (p.getOwner() != null) ? p.getOwner().getName() : "";
				if (owner == null || !owner.toLowerCase().contains(searchOwner.toLowerCase())) {
					match = false;
				}
			}

			if (match) {
				filteredProjects.add(p);
			}
		}

		// FILTER EMPLOYEES
		List<Employee> filteredEmployees = new ArrayList<Employee>();
		for (Employee e : allEmployees) {
			boolean match = true;

			if (searchDept != null && !"".equals(searchDept.trim())) {
				if (e.getDepartment() == null || !e.getDepartment().toLowerCase().contains(searchDept.toLowerCase())) {
					match = false;
				}
			}

			if (searchEmail != null && !"".equals(searchEmail.trim())) {
				if (e.getEmail() == null || !e.getEmail().toLowerCase().contains(searchEmail.toLowerCase())) {
					match = false;
				}
			}

			if (match) {
				filteredEmployees.add(e);
			}
		}

		// PAGINATION
		int total = filteredProjects.size();
		totalPages = (total + pageSize - 1) / pageSize;
		if (totalPages == 0)
			totalPages = 1;
		if (page < 1)
			page = 1;
		if (page > totalPages)
			page = totalPages;

		int start = (page - 1) * pageSize;
		int end = Math.min(start + pageSize, total);

		// handle case when filteredProjects is empty or start==end
		if (start >= 0 && end >= start && !filteredProjects.isEmpty()) {
			projects = filteredProjects.subList(start, end);
		} else {
			projects = new ArrayList<Project>();
		}

		employees = filteredEmployees;

		return SUCCESS;
	}

	public String exportToExcel() {
		try {
			// Session check
			Employee admin = (Employee) ServletActionContext.getRequest().getSession().getAttribute("loggedEmployee");

			if (admin == null || !"ADMIN".equals(admin.getRole())) {
				addActionError("Access Denied");
				return ERROR;
			}

			// Load all employees from service
			employees = employeeService.getAll();

			if (employees == null || employees.isEmpty()) {
				addActionError("No Employee Data Found to Export");
				return ERROR;
			}

			StringBuilder sb = new StringBuilder();

			// Header
			sb.append("ID\tName\tEmail\tDepartment\tSalary\tStatus\tRole\n");

			for (Employee e : employees) {
				sb.append(nullSafeString(e.getId())).append("\t").append(nullSafeString(e.getName())).append("\t")
						.append(nullSafeString(e.getEmail())).append("\t").append(nullSafeString(e.getDepartment()))
						.append("\t").append(nullSafeString(e.getSalary())).append("\t")
						.append(nullSafeString(e.getStatus())).append("\t").append(nullSafeString(e.getRole()))
						.append("\n");
			}

			byte[] bytes = sb.toString().getBytes("UTF-8");
			inputStream = new ByteArrayInputStream(bytes);

			// Struts stream result will use getInputStream()
			return "excel";

		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Error exporting data: " + e.getMessage());
			return ERROR;
		}
	}

	// helper: avoid NPE while appending
	private String nullSafeString(Object o) {
		return (o == null) ? "" : o.toString();
	}
}
