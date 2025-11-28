//package com.example.action;
//
//import com.example.model.Employee;
//import com.example.model.Project;
//import com.example.service.EmployeeService;
//import com.example.service.ProjectService;
//import com.opensymphony.xwork2.ActionSupport;
//
//import java.util.List;
//
//public class FetchDataAction extends ActionSupport {
//
//    private EmployeeService employeeService;
//    private ProjectService projectService;
//
//    private List<Employee> employees;
//    private List<Project> projects;
//
//    public String execute() {
//        employees = employeeService.getAll();
//        projects = projectService.getAllProjects();
//        return SUCCESS;
//    }
//
//    public List<Employee> getEmployees() {
//        return employees;
//    }
//
//    public List<Project> getProjects() {
//        return projects;
//    }
//
//    public void setEmployeeService(EmployeeService employeeService) {
//        this.employeeService = employeeService;
//    }
//
//    public void setProjectService(ProjectService projectService) {
//        this.projectService = projectService;
//    }
//}
