
package com.example.action;

import java.util.ArrayList;
import java.util.List;

import com.example.model.Employee;
import com.example.model.Project;
import com.example.service.ProjectService;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

public class ViewProjectsAction extends ActionSupport {

	private ProjectService projectService;
	private List<Project> projects;

	// ====== SEARCH + PAGINATION ======
	private String searchText;
	private int page = 1;
	private int pageSize = 5;
	private int totalPages;
	private int totalRecords;

	// ====== setters/getters ======
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
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

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	@Override
	public String execute() throws Exception {

		Employee emp = (Employee) ServletActionContext.getRequest().getSession().getAttribute("loggedEmployee");

		if (emp == null) {
			return LOGIN;
		}

		List<Project> accessible = projectService.getAccessibleProjects(emp);

		// SAFETY FIX: ensure shared projects loaded
		if (accessible == null || accessible.isEmpty()) {
			accessible = new ArrayList<>();

			// Owned
			List<Project> owned = projectService.getProjectsOwnedBy(emp.getId());
			if (owned != null)
				accessible.addAll(owned);

			// Shared
			List<Project> shared = projectService.getProjectsSharedWith(emp.getId());
			if (shared != null)
				accessible.addAll(shared);
		}

		if (accessible == null) {
			accessible = new ArrayList<>();
		}

		List<Project> filtered = new ArrayList<>();

		String q = (searchText != null ? searchText.trim().toLowerCase() : null);

		if (q == null || q.length() == 0) {
			filtered = accessible;
		} else {
			for (Project p : accessible) {
				String title = p.getTitle() != null ? p.getTitle().toLowerCase() : "";
				String desc = p.getDescription() != null ? p.getDescription().toLowerCase() : "";
				String owner = (p.getOwner() != null && p.getOwner().getName() != null)
						? p.getOwner().getName().toLowerCase()
						: "";

				if (title.contains(q) || desc.contains(q) || owner.contains(q)) {
					filtered.add(p);
				}
			}
		}

		totalRecords = filtered.size();

		if (totalRecords == 0) {
			projects = filtered;
			totalPages = 0;
			page = 1;
			return SUCCESS;
		}

		totalPages = (totalRecords + pageSize - 1) / pageSize;

		if (page < 1)
			page = 1;
		if (page > totalPages)
			page = totalPages;

		int fromIndex = (page - 1) * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, totalRecords);

		projects = filtered.subList(fromIndex, toIndex);

		// For debugging:
		System.out.println("ViewProjectsAction => user: " + emp.getUsername() + ", searchText=" + searchText + ", page="
				+ page + "/" + totalPages + ", totalRecords=" + totalRecords + ", pageRecords=" + projects.size());

		return SUCCESS;
	}
}
