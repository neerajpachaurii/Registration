//package com.example.action;
//
//import com.example.model.Project;
//import com.example.model.Employee;
//import com.example.service.ProjectService;
//import com.opensymphony.xwork2.ActionSupport;
//import org.apache.struts2.ServletActionContext;
//
//import java.util.*;
//
//public class GetProjectsJsonAction extends ActionSupport {
//
//    private ProjectService projectService;
//
//    private List<Map<String, Object>> rows;
//
//    public void setProjectService(ProjectService projectService) {
//        this.projectService = projectService;
//    }
//
//    public List<Map<String, Object>> getRows() {
//        return rows;
//    }
//
//    @Override
//    public String execute() {
//        Employee logged = (Employee) ServletActionContext.getRequest()
//                .getSession().getAttribute("loggedEmployee");
//
//        if (logged == null) {
//            return ERROR;
//        }
//
//        List<Project> list = projectService.getAllProjects();
//        rows = new ArrayList<>();
//
//        for (Project p : list) {
//            Map<String, Object> m = new HashMap<>();
//            m.put("id", p.getId());
//            m.put("title", p.getTitle());
//            m.put("description", p.getDescription());
//
//            // Owner
//            if (p.getOwner() != null) {
//                Map<String, Object> owner = new HashMap<>();
//                owner.put("id", p.getOwner().getId());
//                owner.put("name", p.getOwner().getName());
//                m.put("owner", owner);
//            } else {
//                m.put("owner", null);
//            }
//
//            // Assigned Users
//            List<Map<String, Object>> users = new ArrayList<>();
//            if (p.getAllowedUsers() != null) {
//                for (Employee u : p.getAllowedUsers()) {
//                    Map<String, Object> u1 = new HashMap<>();
//                    u1.put("id", u.getId());
//                    u1.put("name", u.getName());
//                    users.add(u1);
//                }
//            }
//            m.put("allowedUsers", users);
//
//            rows.add(m);
//        }
//
//        return SUCCESS;
//    }
//}


package com.example.action;

import com.example.model.Project;
import com.example.model.Employee;
import com.example.service.ProjectService;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.struts2.ServletActionContext;

import java.util.*;

public class GetProjectsJsonAction extends ActionSupport {
    private ProjectService projectService;

    // paging params (set by Struts)
    private int start = 0;
    private int limit = 10;

    // filters (optional)
    private String title;
    private String owner; // owner name or email
    private String dept;
    private String email;

    // result wrapper expected by Struts JSON config
    private Map<String,Object> result = new HashMap<String,Object>();

    @Autowired(required = false)
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    // setters for pagination + filters
    public void setStart(int start) { this.start = start; }
    public void setLimit(int limit) { this.limit = limit; }
    public void setTitle(String title) { this.title = title; }
    public void setOwner(String owner) { this.owner = owner; }
    public void setDept(String dept) { this.dept = dept; }
    public void setEmail(String email) { this.email = email; }

    // getter for JSON result
    public Map<String,Object> getResult(){ return result; }

    @Override
    public String execute() throws Exception {
        // ensure session user is present (optional)
        Employee logged = (Employee) ServletActionContext.getRequest().getSession().getAttribute("loggedEmployee");
        if (logged == null) {
            // return SUCCESS with empty result to keep client happy or return ERROR to force login
            result.put("rows", Collections.emptyList());
            result.put("totalCount", 0);
            return SUCCESS;
        }

        List<Project> all = projectService.getAllProjects();
        if (all == null) all = new ArrayList<Project>();

        // Filter in-memory (case-insensitive contains)
        String t = title != null ? title.trim().toLowerCase() : null;
        String o = owner != null ? owner.trim().toLowerCase() : null;
        String d = dept != null ? dept.trim().toLowerCase() : null;
        String e = email != null ? email.trim().toLowerCase() : null;

        List<Project> filtered = new ArrayList<Project>();
        for (Project p : all) {
            boolean keep = true;
            if (t != null && !t.isEmpty()) {
                String v = p.getTitle() == null ? "" : p.getTitle().toLowerCase();
                if (!v.contains(t)) { keep = false; }
            }
            if (keep && o != null && !o.isEmpty()) {
                Employee ownerEmp = p.getOwner();
                String ownerName = ownerEmp != null ? (ownerEmp.getName()==null? "":ownerEmp.getName()).toLowerCase() : "";
                String ownerEmail = ownerEmp != null ? (ownerEmp.getEmail()==null? "":ownerEmp.getEmail()).toLowerCase() : "";
                if (!(ownerName.contains(o) || ownerEmail.contains(o))) keep = false;
            }
            if (keep && d != null && !d.isEmpty()) {
                // filter by owner department (if available)
                Employee ownerEmp = p.getOwner();
                String ownerDept = ownerEmp != null ? (ownerEmp.getDepartment()==null? "":ownerEmp.getDepartment()).toLowerCase() : "";
                if (!ownerDept.contains(d)) keep = false;
            }
            if (keep && e != null && !e.isEmpty()) {
                Employee ownerEmp = p.getOwner();
                String ownerEmail = ownerEmp != null ? (ownerEmp.getEmail()==null? "":ownerEmp.getEmail()).toLowerCase() : "";
                if (!ownerEmail.contains(e)) keep = false;
            }

            if (keep) filtered.add(p);
        }

        int totalCount = filtered.size();

        // sanitize page params
        if (limit <= 0) limit = 10;
        if (start < 0) start = 0;
        int from = Math.min(start, totalCount);
        int to = Math.min(start + limit, totalCount);

        List<Project> page = (from < to) ? filtered.subList(from, to) : new ArrayList<Project>();

        // transform to serializable maps
        List<Map<String,Object>> rows = new ArrayList<>();
        for (Project p : page) {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("id", p.getId());
            map.put("title", p.getTitle());
            map.put("description", p.getDescription());
            map.put("filename", p.getFilename());
            map.put("filepath", p.getFilepath());
            map.put("createdAt", p.getCreatedAt() == null ? null : p.getCreatedAt().getTime());

            if (p.getOwner() != null) {
                Map<String,Object> ownerMap = new HashMap<>();
                ownerMap.put("id", p.getOwner().getId());
                ownerMap.put("name", p.getOwner().getName());
                ownerMap.put("email", p.getOwner().getEmail());
                ownerMap.put("department", p.getOwner().getDepartment());
                map.put("owner", ownerMap);
            } else {
                map.put("owner", null);
            }

            List<Map<String,Object>> users = new ArrayList<>();
            if (p.getAllowedUsers() != null) {
                for (Employee u : p.getAllowedUsers()) {
                    Map<String,Object> um = new HashMap<>();
                    um.put("id", u.getId());
                    um.put("name", u.getName());
                    um.put("email", u.getEmail());
                    users.add(um);
                }
            }
            map.put("allowedUsers", users);

            rows.add(map);
        }

        Map<String,Object> wrapper = new HashMap<>();
        wrapper.put("rows", rows);
        wrapper.put("totalCount", totalCount);
        this.result = wrapper;

        return SUCCESS;
    }
}
