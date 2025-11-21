package com.example.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.example.model.Project;

@Repository
public class ProjectDAOImpl implements ProjectDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() { return sessionFactory.getCurrentSession(); }

    @Override
    @Transactional
    public void save(Project p) { getSession().saveOrUpdate(p); }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Project> findAll() {
        return getSession().createQuery("from Project p order by p.createdAt desc").list();
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Project> findByOwnerId(int ownerId) {
        Query q = getSession().createQuery("from Project p where p.owner.id = :oid order by p.createdAt desc");
        q.setParameter("oid", ownerId);
        return q.list();
    }

    @Override
    @Transactional
    public Project getById(int id) {
        return (Project) getSession().get(Project.class, id);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Project p = getById(id);
        if (p != null) getSession().delete(p);
    }
}
