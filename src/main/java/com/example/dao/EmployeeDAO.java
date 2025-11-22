package com.example.dao;

import java.util.List;
import com.example.model.Employee;

public interface EmployeeDAO {
    void save(Employee e);
    List<Employee> getAll();
    Employee getById(int id);
    void update(Employee e);
    void delete(int id);

    Employee findByUsername(String username);
    boolean employeeExists(String username);
}
