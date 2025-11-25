

package com.example.dao;

import java.util.List;
import com.example.model.Project;

public interface ProjectDAO {
    void save(Project p);
    List<Project> findAll();
    List<Project> findByOwnerId(int ownerId);
    Project getById(int id);
    void delete(int id);
    List<Project> findSharedWithUser(int userId);
    List<Integer> findAssignedEmployeeIds(int projectId);
    void replaceAssignedEmployeeIds(int projectId, java.util.List<Integer> employeeIds);
    void removeAssignment(int projectId, int employeeId);
}
