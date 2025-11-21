package com.example.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.example.model.User;

public class UserDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }

    public boolean userExists(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("FROM User WHERE username = :uname");
        q.setParameter("uname", username);
        return q.uniqueResult() != null;
    }
    public User findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("FROM User WHERE username = :uname");
        q.setParameter("uname", username);
        return (User) q.uniqueResult();
    }
}
