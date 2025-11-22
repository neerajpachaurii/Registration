package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.EmployeeDAO;
import com.example.model.Employee;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    // DAO - will be injected either by XML (<property name="dao".../>) or by Spring
    private EmployeeDAO dao;

    // setter required for XML property injection: <property name="dao" ref="employeeDAO"/>
    public void setDao(EmployeeDAO dao) {
        this.dao = dao;
    }

    @Transactional
    public void save(Employee e) {
        dao.save(e);
    }

    @Transactional
    public List<Employee> getAll() {
        return dao.getAll();
    }

    @Transactional
    public Employee getById(int id) {
        return dao.getById(id);
    }

    @Transactional
    public void update(Employee e) {
        dao.update(e);
    }

    @Transactional
    public void delete(int id) {
        dao.delete(id);
    }

    @Transactional
    public Employee findByUsername(String username) {
        return dao.findByUsername(username);
    }

    @Transactional
    public boolean authenticate(String username, String password) {
        Employee e = dao.findByUsername(username);
        return e != null && e.getPassword().equals(password);
    }

    @Transactional
    public boolean employeeExists(String username) {
        return dao.employeeExists(username);
    }

    @Transactional
    public Employee login(String username, String password) {
        Employee emp = dao.findByUsername(username);
        if (emp == null) return null;
        if (emp.getPassword().equals(password)) return emp;
        return null;
    }
}
