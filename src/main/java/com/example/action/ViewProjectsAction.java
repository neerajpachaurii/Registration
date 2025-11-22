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

    // ====== NEW FIELDS for search + pagination ======
    private String searchText;   // dashboard search text
    private int page = 1;        // current page (1-based)
    private int pageSize = 5;    // as you said: 5 records per page
    private int totalPages;      // total number of pages
    private int totalRecords;    // total projects (after search filter)

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
    // agar future me change karna ho:
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

        // Session se logged-in employee
        Employee emp = (Employee) ServletActionContext.getRequest()
                .getSession().getAttribute("loggedEmployee");

        if (emp == null) {
            return LOGIN;
        }

        // 1) Pehle saare accessible projects laao
        List<Project> accessible = projectService.getAccessibleProjects(emp);
        if (accessible == null) {
            accessible = new ArrayList<Project>();
        }

        // 2) SEARCH apply karo (title / description / owner name) – case-insensitive
        List<Project> filtered = new ArrayList<Project>();

        String q = null;
        if (searchText != null) {
            q = searchText.trim().toLowerCase();
        }

        if (q == null || q.length() == 0) {
            // no search => sabhi
            filtered = accessible;
        } else {
            for (int i = 0; i < accessible.size(); i++) {
                Project p = accessible.get(i);

                String title = (p.getTitle() == null) ? "" : p.getTitle().toLowerCase();
                String desc  = (p.getDescription() == null) ? "" : p.getDescription().toLowerCase();
                String owner = "";
                if (p.getOwner() != null && p.getOwner().getName() != null) {
                    owner = p.getOwner().getName().toLowerCase();
                }

                if (title.contains(q) || desc.contains(q) || owner.contains(q)) {
                    filtered.add(p);
                }
            }
        }

        // 3) Pagination ke liye total count
        totalRecords = filtered.size();

        if (totalRecords == 0) {
            // koi project nahi mila
            totalPages = 0;
            page = 1;
            projects = filtered;   // empty list
            return SUCCESS;
        }

        // 4) totalPages nikaalo
        totalPages = (totalRecords + pageSize - 1) / pageSize; // ceil

        // 5) Page validation (yahi "Validate dashboard page (pagination)" hai)
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages) {
            page = totalPages;
        }

        // 6) Sublist indices
        int fromIndex = (page - 1) * pageSize;
        int toIndex = fromIndex + pageSize;
        if (toIndex > totalRecords) {
            toIndex = totalRecords;
        }

        projects = filtered.subList(fromIndex, toIndex);

        // Debug logs (console me check karne ke liye)
        System.out.println("ViewProjectsAction => user: " + emp.getUsername()
                + ", searchText=" + searchText
                + ", page=" + page + "/" + totalPages
                + ", totalRecords=" + totalRecords
                + ", pageRecords=" + projects.size());

        return SUCCESS;
    }
}
