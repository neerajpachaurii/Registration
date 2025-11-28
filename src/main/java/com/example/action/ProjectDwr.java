package com.example.action;

import java.util.*;
import com.example.model.Project;
import com.example.model.Employee;
import com.example.service.ProjectService;


public class ProjectDwr {

    private ProjectService projectService;

    // required by Spring XML property injection
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }
    public ProjectService getProjectService() {
        return this.projectService;
    }

    // simple non-filtered list (used if client calls without filters)
    public List<Map<String,Object>> getProjects() {
        return getProjectsFiltered(null, null, null, null);
    }

    /**
     * Returns a list of simple maps (id,title,description,owner,allowedUsers).
     */
    public List<Map<String,Object>> getProjectsFiltered(String title, String ownerName, String dept, String email) {
        List<Project> list = projectService.getAllProjects();
        List<Map<String,Object>> output = new ArrayList<Map<String,Object>>();
        if (list == null) return output;

        for (Project p : list) {
            // server-side filtering
            if (title != null && title.trim().length() > 0) {
                String pt = (p.getTitle() == null ? "" : p.getTitle()).toLowerCase();
                if (pt.indexOf(title.trim().toLowerCase()) == -1) continue;
            }
            if (ownerName != null && ownerName.trim().length() > 0) {
                if (p.getOwner() == null || p.getOwner().getName() == null ||
                    p.getOwner().getName().toLowerCase().indexOf(ownerName.trim().toLowerCase()) == -1) continue;
            }
            if ((dept != null && dept.trim().length() > 0) || (email != null && email.trim().length() > 0)) {
                boolean matched = false;
                if (p.getAllowedUsers() != null) {
                    for (Employee u : p.getAllowedUsers()) {
                        if (dept != null && dept.trim().length() > 0 && u.getDepartment() != null &&
                            u.getDepartment().toLowerCase().indexOf(dept.trim().toLowerCase()) != -1) {
                            matched = true;
                        }
                        if (email != null && email.trim().length() > 0 && u.getEmail() != null &&
                            u.getEmail().toLowerCase().indexOf(email.trim().toLowerCase()) != -1) {
                            matched = true;
                        }
                    }
                }
                if (!matched) continue;
            }

            Map<String,Object> m = new HashMap<String,Object>();
            m.put("id", p.getId());
            m.put("title", p.getTitle());
            m.put("description", p.getDescription());
            if (p.getOwner() != null) {
                Map<String,Object> owner = new HashMap<String,Object>();
                owner.put("id", p.getOwner().getId());
                owner.put("name", p.getOwner().getName());
                m.put("owner", owner);
            } else {
                m.put("owner", null);
            }

            List<Map<String,Object>> users = new ArrayList<Map<String,Object>>();
            if (p.getAllowedUsers() != null) {
                for (Employee u : p.getAllowedUsers()) {
                    Map<String,Object> u1 = new HashMap<String,Object>();
                    u1.put("id", u.getId());
                    u1.put("name", u.getName());
                    u1.put("email", u.getEmail());
                    u1.put("department", u.getDepartment());
                    users.add(u1);
                }
            }
            m.put("allowedUsers", users);

            output.add(m);
        }

        return output;
    }
}
