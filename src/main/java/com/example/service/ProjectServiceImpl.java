package com.example.service;

import com.example.dao.ProjectDAO;
import com.example.model.Project;
import com.example.model.Employee;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ProjectServiceImpl implements ProjectService {

    private ProjectDAO dao;

    // Spring XML will inject projectDAO via this setter
    public void setDao(ProjectDAO dao) {
        this.dao = dao;
    }

    @Override
    public void save(Project p) {
        dao.save(p);
    }

    @Override
    public List<Project> getAllProjects() {
        return dao.findAll();
    }

    @Override
    public List<Project> findByOwnerId(int ownerId) {
        return dao.findByOwnerId(ownerId);
    }

    @Override
    public Project getById(int id) {
        return dao.getById(id);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }

    @Override
    public List<Project> getAccessibleProjects(Employee emp) {
        if (emp == null) {
            return new ArrayList<Project>();
        }

        // ADMIN → return all projects
        if ("ADMIN".equalsIgnoreCase(emp.getRole())) {
            return dao.findAll();
        }

        List<Project> result = new ArrayList<Project>();

        // Owned
        List<Project> owned = dao.findByOwnerId(emp.getId());
        if (owned != null) {
            result.addAll(owned);
        }

        // Shared
        List<Project> shared = dao.findSharedWithUser(emp.getId());
        if (shared != null) {
            result.addAll(shared);
        }

        // remove duplicates
        return new ArrayList<Project>(new HashSet<Project>(result));
    }

    @Override
    public List<Project> getProjectsOwnedBy(int employeeId) {
        return dao.findByOwnerId(employeeId);
    }

    @Override
    public List<Project> getProjectsSharedWith(int employeeId) {
        return dao.findSharedWithUser(employeeId);
    }

    @Override
    public List<Integer> getAssignedEmployeeIds(int projectId) {
        return dao.findAssignedEmployeeIds(projectId);
    }

    @Override
    public void replaceAssignedEmployeeIds(int projectId, List<Integer> employeeIds) {
        dao.replaceAssignedEmployeeIds(projectId, employeeIds);
    }

    @Override
    public void assignUsersToProject(int projectId, List<Integer> employeeIds) {
        replaceAssignedEmployeeIds(projectId, employeeIds);
    }

    @Override
    public void removeAssignment(int projectId, int employeeId) {
        dao.removeAssignment(projectId, employeeId);
    }
}
