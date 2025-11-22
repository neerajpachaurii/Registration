//package com.example.service;
//
//import java.util.List;
//import com.example.model.Employee;
//import com.example.model.Project;
//
//public interface ProjectService {
//
//    void save(Project p);
//
//    Project findById(int id);
//
//    List<Project> getAllProjects();
//
//    List<Project> getAccessibleProjects(Employee emp);
//
//    void assignUsersToProject(int projectId, List<Integer> employeeIds);
//
//    void removeAssignment(int projectId, int employeeId);
//
//    List<Project> findAll();
//
//    List<Project> findByOwnerId(int ownerId);
//
//    Project getById(int id);
//
//    void delete(int id);
//
//    List<Integer> getAssignedEmployeeIds(int projectId);
//}

package com.example.service;

import java.util.List;
import com.example.model.Employee;
import com.example.model.Project;

public interface ProjectService {
    void save(Project p);
    Project findById(int id);
    List<Project> getAllProjects();
    List<Project> getAccessibleProjects(Employee emp);
    void assignUsersToProject(int projectId, List<Integer> employeeIds);
    void removeAssignment(int projectId, int employeeId);
    List<Project> findAll();
    List<Project> findByOwnerId(int ownerId);
    Project getById(int id);
    void delete(int id);
    List<Integer> getAssignedEmployeeIds(int projectId);
}
