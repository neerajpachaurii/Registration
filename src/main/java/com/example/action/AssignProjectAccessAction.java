package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.service.ProjectService;
import com.example.service.EmployeeService;
import com.example.model.Employee;
import com.example.model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AssignProjectAccessAction extends ActionSupport {

    // incoming fields
    private Integer projectId;
    private Integer[] userIds;

    // output fields
    private List<Employee> allEmployees;
    private List<Integer> allowedIds;
    private String projectTitle;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EmployeeService employeeService;


    // ===== SETTERS / GETTERS =====
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

    public List<Employee> getAllEmployees() {
        return allEmployees;
    }

    public List<Integer> getAllowedIds() {
        return allowedIds;
    }

    public String getProjectTitle() {
        return projectTitle;
    }


    @Override
    public String input() throws Exception {

        Map<String, Object> session = ActionContext.getContext().getSession();
        Employee admin = (Employee) session.get("loggedEmployee");

        if (admin == null || !"ADMIN".equals(admin.getRole())) {
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

        // Load all employees
        allEmployees = employeeService.getAll();

        // Load assigned users
        allowedIds = projectService.getAssignedEmployeeIds(projectId);
        if (allowedIds == null) {
            allowedIds = new ArrayList<Integer>();
        }

        projectTitle = p.getTitle();

        return INPUT; // Goes to manage_access.jsp
    }


    @Override
    public String execute() throws Exception {

        Map<String, Object> session = ActionContext.getContext().getSession();
        Employee admin = (Employee) session.get("loggedEmployee");

        if (admin == null || !"ADMIN".equals(admin.getRole())) {
            addActionError("Access denied");
            return "accessDenied";
        }

        List<Integer> ids = new ArrayList<Integer>();

        if (userIds != null) {
            for (Integer id : userIds) {
                if (id != null) {
                    ids.add(id);
                }
            }
        }

        projectService.assignUsersToProject(projectId, ids);

        return SUCCESS;
    }
}
