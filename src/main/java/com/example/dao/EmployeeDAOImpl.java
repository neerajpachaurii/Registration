//package com.example.dao;
//
//import java.util.List;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.example.model.Employee;
//
//@Repository
//public class EmployeeDAOImpl implements EmployeeDAO {
//
//    @Autowired
//    private SessionFactory sessionFactory;
//
//    private Session getSession() {
//        return sessionFactory.getCurrentSession();
//    }
//
//    @Transactional
//    public void save(Employee e) {
//        getSession().save(e);
//    }
//
//    @Transactional
//    public List<Employee> getAll() {
//        return getSession().createQuery("from Employee").list();
//    }
//
//    @Transactional
//    public Employee getById(int id) {
//        return (Employee) getSession().get(Employee.class, id);
//    }
//
//    @Transactional
//    public void update(Employee e) {
//        getSession().update(e);
//    }
//
//    @Transactional
//    public void delete(int id) {
//        Employee e = getById(id);
//        if(e != null) getSession().delete(e);
//    }
//}

package com.example.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Employee;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public void save(Employee e) {
        getSession().save(e);
    }

    @Transactional
    public List<Employee> getAll() {
        return getSession().createQuery("from Employee").list();
    }

    @Transactional
    public Employee getById(int id) {
        return (Employee) getSession().get(Employee.class, id);
    }

    @Transactional
    public void update(Employee e) {
        getSession().update(e);
    }

    @Transactional
    public void delete(int id) {
        Employee e = getById(id);
        if (e != null) getSession().delete(e);
    }

    @Transactional
    public Employee findByUsername(String username) {
        Query q = getSession().createQuery("FROM Employee WHERE username = :uname");
        q.setParameter("uname", username);
        return (Employee) q.uniqueResult();
    }

    @Transactional
    public boolean employeeExists(String username) {
        return findByUsername(username) != null;
    }
}
