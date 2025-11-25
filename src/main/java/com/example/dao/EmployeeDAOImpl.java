package com.example.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Employee;

public class EmployeeDAOImpl implements EmployeeDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void save(Employee e) {
        getSession().save(e);
    }

    public List<Employee> getAll() {
        return getSession().createQuery("from Employee").list();
    }

    public Employee getById(int id) {
        return (Employee) getSession().get(Employee.class, id);
    }

    public void update(Employee e) {
        getSession().update(e);
    }

    public void delete(int id) {
        Employee e = getById(id);
        if (e != null) getSession().delete(e);
    }

    public Employee findByUsername(String username) {
        return (Employee) getSession()
                .createQuery("from Employee where username = :u")
                .setParameter("u", username)
                .uniqueResult();
    }

    public boolean employeeExists(String username) {
        return findByUsername(username) != null;
    }
    @Override
    public void updateStatus(int id, String status) {
        Session s = sessionFactory.getCurrentSession();
        Employee e = (Employee) s.get(Employee.class, id);
        if (e != null) {
            e.setStatus(status);
            s.update(e);
        }
    }

}
