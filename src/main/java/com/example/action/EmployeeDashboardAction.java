package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import com.example.model.Employee;
import com.example.model.Project;
import com.example.service.ProjectService;
import org.apache.struts2.ServletActionContext;

import java.util.List;

public class EmployeeDashboardAction extends ActionSupport {

    private ProjectService projectService;
    private List<Project> projects;

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public List<Project> getProjects() {
        return projects;
    }

    @Override
    public String execute() throws Exception {

        Employee emp = (Employee) ServletActionContext.getRequest()
                .getSession().getAttribute("loggedEmployee");

        if (emp == null) {
            return LOGIN;
        }

        // THIS loads assigned + owned projects
        projects = projectService.getAccessibleProjects(emp);

        return SUCCESS;
    }
}
