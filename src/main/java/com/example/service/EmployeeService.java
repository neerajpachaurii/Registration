//package com.example.service;
//
//import java.util.List;
//
//import com.example.model.Employee;
//
//public interface EmployeeService {
//    void save(Employee e);
//    List<Employee> getAll();
//    Employee getById(int id);
//    void update(Employee e);
//    void delete(int id);
//}

package com.example.service;

import java.util.List;
import com.example.model.Employee;

public interface EmployeeService {
    void save(Employee e);
    List<Employee> getAll();
    Employee getById(int id);
    void update(Employee e);
    void delete(int id);

    Employee findByUsername(String username);
    boolean authenticate(String username, String password);
    boolean employeeExists(String username);
    Employee login(String username, String password);
}

