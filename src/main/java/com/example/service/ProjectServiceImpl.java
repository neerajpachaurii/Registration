package com.example.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.dao.ProjectDAO;
import com.example.model.Project;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDAO dao;

    @Override
    @Transactional
    public void save(Project p) { dao.save(p); }

    @Override
    @Transactional
    public List<Project> findAll() { return dao.findAll(); }

    @Override
    @Transactional
    public List<Project> findByOwnerId(int ownerId) { return dao.findByOwnerId(ownerId); }

    @Override
    @Transactional
    public Project getById(int id) { return dao.getById(id); }

    @Override
    @Transactional
    public void delete(int id) { dao.delete(id); }
}
