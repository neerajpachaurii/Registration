package com.example.service;

import com.example.model.Project;
import com.example.model.Employee;

import java.util.List;

public interface ProjectService {

    void save(Project p);

    List<Project> getAllProjects();

    List<Project> findByOwnerId(int ownerId);

    Project getById(int id);

    void delete(int id);

    List<Project> getAccessibleProjects(Employee emp);

    List<Project> getProjectsOwnedBy(int employeeId);

    List<Project> getProjectsSharedWith(int employeeId);

    List<Integer> getAssignedEmployeeIds(int projectId);

    void replaceAssignedEmployeeIds(int projectId, List<Integer> employeeIds);

    void assignUsersToProject(int projectId, List<Integer> employeeIds);

    void removeAssignment(int projectId, int employeeId);
}
