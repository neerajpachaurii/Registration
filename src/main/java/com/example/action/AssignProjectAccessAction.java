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

public class AssignProjectAccessAction extends ActionSupport {

    // incoming/ outgoing
    private Integer projectId;
    private Integer[] userIds; // form checkboxes -> integer array

    // for page rendering
    private List<Employee> allEmployees;
    private List<Integer> allowedIds; // ids selected already
    private String projectTitle;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EmployeeService employeeService;

    public void setProjectId(Integer projectId){ this.projectId = projectId; }
    public Integer getProjectId(){ return projectId; }

    public void setUserIds(Integer[] u){ this.userIds = u; }
    public Integer[] getUserIds(){ return userIds; }

    public List<Employee> getAllEmployees(){ return allEmployees; }
    public List<Integer> getAllowedIds(){ return allowedIds; }
    public String getProjectTitle(){ return projectTitle; }

    /**
     * Display page (method called by struts: method="input")
     */
    public String input() throws Exception {
        Map<String,Object> session = ActionContext.getContext().getSession();
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

        // load page data
        allEmployees = employeeService.getAll();
        // get currently assigned employee ids
        allowedIds = projectService.getAssignedEmployeeIds(projectId);

        if (allowedIds == null) allowedIds = new ArrayList<Integer>();
        projectTitle = p.getTitle();

        return INPUT; // mapped to manage_access.jsp
    }

    /**
     * Save selected checkboxes (method execute)
     */
    @Override
    public String execute() throws Exception {
        Map<String,Object> session = ActionContext.getContext().getSession();
        Employee admin = (Employee) session.get("loggedEmployee");
        if (admin == null || !"ADMIN".equals(admin.getRole())) {
            addActionError("Access denied");
            return "accessDenied";
        }

        // convert Integer[] to List<Integer>
        List<Integer> ids = new ArrayList<Integer>();
        if (userIds != null) {
            for (int i=0; i<userIds.length; i++) ids.add(userIds[i]);
        }

        projectService.assignUsersToProject(projectId, ids);

        return SUCCESS; // redirect configured in struts.xml
    }
}
