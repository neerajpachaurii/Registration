package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;
import com.example.service.ProjectService;
import com.example.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;

public class DeleteProjectAction extends ActionSupport {

    private int id;   

    @Autowired
    private ProjectService projectService;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String execute() throws Exception {

        Employee admin = (Employee) ActionContext.getContext()
                .getSession().get("loggedEmployee");

        // Only ADMIN can delete
        if (admin == null || !"ADMIN".equalsIgnoreCase(admin.getRole())) {
            return "accessDenied";
        }

        projectService.delete(id);

        return SUCCESS;
    }
}
