package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import com.example.model.Project;
import com.example.model.Employee;
import com.example.service.ProjectService;
import com.opensymphony.xwork2.ActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class ViewProjectsAction extends ActionSupport {

    @Autowired
    private ProjectService projectService;

    private List<Project> projects;

    public String execute() {
        Map<String,Object> session = ActionContext.getContext().getSession();
        Employee e = (Employee) session.get("loggedEmployee");
        if (e == null) return LOGIN;

        if ("ADMIN".equalsIgnoreCase(e.getRole())) {
            projects = projectService.findAll();
        } else {
            projects = projectService.findByOwnerId(e.getId());
        }
        return SUCCESS;
    }

    public List<Project> getProjects() { return projects; }
}
