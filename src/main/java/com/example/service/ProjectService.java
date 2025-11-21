package com.example.service;

import java.util.List;
import com.example.model.Project;

public interface ProjectService {
    void save(Project p);
    List<Project> findAll();
    List<Project> findByOwnerId(int ownerId);
    Project getById(int id);
    void delete(int id);
}
