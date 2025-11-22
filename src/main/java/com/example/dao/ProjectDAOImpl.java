package com.example.dao;

import java.util.List;
import java.util.Iterator;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Project;

public class ProjectDAOImpl implements ProjectDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    // =====================================================
    // SAVE OR UPDATE PROJECT
    // =====================================================
    @Transactional
    public void save(Project p) {
        getSession().saveOrUpdate(p);
    }

    // =====================================================
    // ALL PROJECTS - FOR ADMIN
    // =====================================================
    @SuppressWarnings("unchecked")
    @Transactional
    public List<Project> findAll() {
        return getSession()
                .createQuery("from Project p order by p.createdAt desc")
                .list();
    }

    // =====================================================
    // PROJECTS OWNED BY USER
    // =====================================================
    @SuppressWarnings("unchecked")
    @Transactional
    public List<Project> findByOwnerId(int ownerId) {
        Query q = getSession()
                .createQuery("from Project p where p.owner.id = :oid order by p.createdAt desc");
        q.setInteger("oid", ownerId);
        return q.list();
    }

    // =====================================================
    // GET BY ID
    // =====================================================
    @Transactional
    public Project getById(int id) {
        return (Project) getSession().get(Project.class, id);
    }

    // =====================================================
    // DELETE
    // =====================================================
    @Transactional
    public void delete(int id) {
        Project p = getById(id);
        if (p != null) {
            getSession().delete(p);
        }
    }

    // =====================================================
    // SHARED PROJECTS FOR EMPLOYEE
    // =====================================================
    @SuppressWarnings("unchecked")
    @Transactional
    public List<Project> findSharedWithUser(int employeeId) {

        String sql = "SELECT p.* FROM project p "
                   + "JOIN project_user_access a ON p.id = a.project_id "
                   + "WHERE a.employee_id = :eid "
                   + "ORDER BY p.createdat DESC";

        SQLQuery q = getSession().createSQLQuery(sql);
        q.addEntity(Project.class);
        q.setInteger("eid", employeeId);

        return q.list();
    }

    // =====================================================
    // GET EMPLOYEES ASSIGNED TO A PROJECT
    // =====================================================
    @SuppressWarnings("unchecked")
    @Transactional
    public List<Integer> findAssignedEmployeeIds(int projectId) {

        String sql = "SELECT employee_id FROM project_user_access WHERE project_id = :pid";

        SQLQuery q = getSession().createSQLQuery(sql);
        q.setInteger("pid", projectId);

        return q.list();
    }

    // =====================================================
    // MAIN FIX: REPLACE ASSIGNMENT LIST
    // =====================================================
    @Transactional
    public void replaceAssignedEmployeeIds(int projectId, List<Integer> employeeIds) {

        // DELETE existing rows
        String del = "DELETE FROM project_user_access WHERE project_id = :pid";
        SQLQuery q1 = getSession().createSQLQuery(del);
        q1.setInteger("pid", projectId);
        q1.executeUpdate();

        // INSERT NEW
        if (employeeIds != null) {

            for (Integer uid : employeeIds) {

                String ins = "INSERT INTO project_user_access (project_id, employee_id) "
                           + "VALUES (:pid, :uid)";

                SQLQuery q2 = getSession().createSQLQuery(ins);
                q2.setInteger("pid", projectId);
                q2.setInteger("uid", uid);
                q2.executeUpdate();
            }
        }
    }

    // =====================================================
    // REMOVE SINGLE ASSIGNMENT
    // =====================================================
    @Transactional
    public void removeAssignment(int projectId, int employeeId) {

        String del = "DELETE FROM project_user_access "
                   + "WHERE project_id = :pid AND employee_id = :eid";

        SQLQuery q = getSession().createSQLQuery(del);
        q.setInteger("pid", projectId);
        q.setInteger("eid", employeeId);

        q.executeUpdate();
    }
}
