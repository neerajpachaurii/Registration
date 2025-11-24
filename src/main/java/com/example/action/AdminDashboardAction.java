package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import com.example.model.Employee;
import com.example.model.Project;
import com.example.service.EmployeeService;
import com.example.service.ProjectService;
import com.opensymphony.xwork2.ActionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminDashboardAction extends ActionSupport {

	private EmployeeService employeeService;
	private ProjectService projectService;

	private List<Employee> employees;
	private List<Project> projects;

	// SEARCH FIELDS
	private String searchTitle;
	private String searchOwner;
	private String searchDept;
	private String searchEmail;

	// PAGINATION
	private int page = 1;
	private int pageSize = 10;
	private int totalPages;

	public void setEmployeeService(EmployeeService s) {
		this.employeeService = s;
	}

	public void setProjectService(ProjectService p) {
		this.projectService = p;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int p) {
		this.pageSize = p;
	}

	public int getTotalPages() {
		return totalPages;
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

	@Override
	public String execute() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		Employee admin = (Employee) session.get("loggedEmployee");

		if (admin == null)
			return LOGIN;
		if (!"ADMIN".equalsIgnoreCase(admin.getRole()))
			return "accessDenied";

		List<Project> allProjects = projectService.getAllProjects();
		List<Employee> allEmployees = employeeService.getAll();
		projects = new ArrayList<Project>();
		for (Project p : allProjects) {

			boolean ok = true;

			if (searchTitle != null && searchTitle.trim().length() > 0) {
				if (p.getTitle() == null || !p.getTitle().toLowerCase().contains(searchTitle.toLowerCase())) {
					ok = false;
				}
			}

			if (ok && searchOwner != null && searchOwner.trim().length() > 0) {
				if (p.getOwner() == null || p.getOwner().getName() == null
						|| !p.getOwner().getName().toLowerCase().contains(searchOwner.toLowerCase())) {
					ok = false;
				}
			}

			if (ok) {
				projects.add(p);
			}
		}
		employees = new ArrayList<Employee>();
		for (Employee e : allEmployees) {

			boolean ok = true;

			if (searchDept != null && searchDept.trim().length() > 0) {
				if (e.getDepartment() == null || !e.getDepartment().toLowerCase().contains(searchDept.toLowerCase())) {
					ok = false;
				}
			}

			if (ok && searchEmail != null && searchEmail.trim().length() > 0) {
				if (e.getEmail() == null || !e.getEmail().toLowerCase().contains(searchEmail.toLowerCase())) {
					ok = false;
				}
			}

			if (ok) {
				employees.add(e);
			}
		}
		int total = projects.size();
		totalPages = (int) Math.ceil((double) total / pageSize);

		if (totalPages == 0)
			totalPages = 1;

		if (page < 1)
			page = 1;
		if (page > totalPages)
			page = totalPages;

		int start = (page - 1) * pageSize;
		int end = start + pageSize;

		if (end > total)
			end = total;

		List<Project> paginated = new ArrayList<Project>();
		for (int i = start; i < end; i++) {
			paginated.add(projects.get(i));
		}

		projects = paginated;

		return SUCCESS;
	}
}
