package com.example.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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

	@Transactional
	public void save(Project p) {
		getSession().saveOrUpdate(p);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Project> findAll() {

		Session s = sessionFactory.openSession();
		List<Project> list = s.createQuery("from Project p order by p.createdAt desc").list();

		for (Project p : list) {
			Hibernate.initialize(p.getOwner());
			Hibernate.initialize(p.getAllowedUsers());
		}

		s.close();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Project> findByOwnerId(int ownerId) {

		Session s = sessionFactory.openSession();
		List<Project> list = s.createQuery("from Project p where p.owner.id = :oid order by p.createdAt desc")
				.setParameter("oid", ownerId).list();

		for (Project p : list) {
			Hibernate.initialize(p.getOwner());
			Hibernate.initialize(p.getAllowedUsers());
		}

		s.close();
		return list;
	}

	@Transactional
	public Project getById(int id) {

		Project p = (Project) getSession().get(Project.class, id);

		if (p != null) {
			Hibernate.initialize(p.getOwner());
			Hibernate.initialize(p.getAllowedUsers());
		}

		return p;
	}

	@Transactional
	public void delete(int id) {
		Project p = getById(id);
		if (p != null)
			getSession().delete(p);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Project> findSharedWithUser(int employeeId) {
		String sql = "SELECT p.* FROM project p " + "JOIN project_user_access a ON p.id = a.project_id "
				+ "WHERE a.employee_id = :eid " + "ORDER BY p.createdat DESC";

		List<Project> list = sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Project.class)
				.setParameter("eid", employeeId).list();

// Force load lazy fields
		for (Project p : list) {
			Hibernate.initialize(p.getOwner());
			Hibernate.initialize(p.getAllowedUsers());
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Integer> findAssignedEmployeeIds(int projectId) {

		String sql = "SELECT employee_id FROM project_user_access WHERE project_id = :pid";

		SQLQuery q = getSession().createSQLQuery(sql);
		q.setInteger("pid", projectId);

		return q.list();
	}

	@Transactional
	public void replaceAssignedEmployeeIds(int projectId, List<Integer> employeeIds) {

		// DELETE OLD
		String del = "DELETE FROM project_user_access WHERE project_id = :pid";
		SQLQuery q1 = getSession().createSQLQuery(del);
		q1.setInteger("pid", projectId);
		q1.executeUpdate();

		// INSERT NEW
		if (employeeIds != null) {
			for (Integer uid : employeeIds) {
				String ins = "INSERT INTO project_user_access (project_id, employee_id) VALUES (:pid, :uid)";

				SQLQuery q2 = getSession().createSQLQuery(ins);
				q2.setInteger("pid", projectId);
				q2.setInteger("uid", uid);
				q2.executeUpdate();
			}
		}
	}

	@Transactional
	public void removeAssignment(int projectId, int employeeId) {

		Session s = sessionFactory.openSession();
		Transaction tx = s.beginTransaction();

		s.createSQLQuery("DELETE FROM project_user_access WHERE project_id = :pid AND employee_id = :eid")
				.setParameter("pid", projectId).setParameter("eid", employeeId).executeUpdate();

		tx.commit();
		s.close();
	}
}
