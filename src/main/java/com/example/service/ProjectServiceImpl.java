package com.example.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.example.dao.ProjectDAO;
import com.example.dao.EmployeeDAO;
import com.example.model.Employee;
import com.example.model.Project;

public class ProjectServiceImpl implements ProjectService {

    private ProjectDAO dao;
    private EmployeeDAO employeeDAO;

    public void setDao(ProjectDAO dao) { this.dao = dao; }
    public void setEmployeeDAO(EmployeeDAO employeeDAO) { this.employeeDAO = employeeDAO; }

    @Transactional
    public void save(Project p) {
        dao.save(p);
    }

    @Transactional
    public Project findById(int id) {
        return dao.getById(id);
    }

    @Transactional
    public List<Project> getAllProjects() {
        return dao.findAll();
    }

    // MAIN ACCESS METHOD
    @Transactional
    public List<Project> getAccessibleProjects(Employee emp) {

        if (emp == null) return new ArrayList<Project>();

        // ADMIN sees ALL
        if ("ADMIN".equalsIgnoreCase(emp.getRole())) {
            return dao.findAll();
        }

        // USER → owned + shared
        List<Project> owned = dao.findByOwnerId(emp.getId());
        List<Project> shared = dao.findSharedWithUser(emp.getId());

        Set<Project> set = new HashSet<Project>();
        if (owned != null) set.addAll(owned);
        if (shared != null) set.addAll(shared);

        return new ArrayList<Project>(set);
    }

    @Transactional
    public void removeAssignment(int projectId, int employeeId) {
        dao.removeAssignment(projectId, employeeId);
    }

    @Transactional
    public List<Project> findAll() {
        return dao.findAll();
    }

    @Transactional
    public List<Project> findByOwnerId(int ownerId) {
        return dao.findByOwnerId(ownerId);
    }

    @Transactional
    public Project getById(int id) {
        return dao.getById(id);
    }

    @Transactional
    public void delete(int id) {
        dao.delete(id);
    }

    @Transactional
    public List<Integer> getAssignedEmployeeIds(int projectId) {
        return dao.findAssignedEmployeeIds(projectId);
    }

    @Transactional
    public void assignUsersToProject(int projectId, List<Integer> employeeIds) {
        dao.replaceAssignedEmployeeIds(projectId, employeeIds);
    }
}
