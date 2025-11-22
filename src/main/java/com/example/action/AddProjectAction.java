//package com.example.action;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.apache.struts2.ServletActionContext;
//
//import com.example.model.Employee;
//import com.example.model.Project;
//import com.example.service.ProjectService;
//
//import com.opensymphony.xwork2.ActionSupport;
//import com.opensymphony.xwork2.ActionContext;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.Map;
//
//public class AddProjectAction extends ActionSupport {
//
//    private Project project = new Project();
//
//    private File upload;
//    private String uploadFileName;
//    private String uploadContentType;
//
//    @Autowired
//    private ProjectService projectService;
//
//    public Project getProject() { return project; }
//    public void setProject(Project project) { this.project = project; }
//
//    public File getUpload() { return upload; }
//    public void setUpload(File upload) { this.upload = upload; }
//
//    public String getUploadFileName() { return uploadFileName; }
//    public void setUploadFileName(String uploadFileName) { this.uploadFileName = uploadFileName; }
//
//    public String getUploadContentType() { return uploadContentType; }
//    public void setUploadContentType(String uploadContentType) { this.uploadContentType = uploadContentType; }
//
//    public String execute() throws Exception {
//
//        Map session = ActionContext.getContext().getSession();
//        Employee emp = (Employee) session.get("loggedEmployee");
//
//        if (emp == null) {
//            addActionError("Please login first.");
//            return "login";
//        }
//
//        if (project.getTitle() == null || "".equals(project.getTitle().trim())) {
//            addActionError("Title required");
//            return INPUT;
//        }
//
//        if (upload != null) {
//            String uploadDir = ServletActionContext.getServletContext().getRealPath("/uploads");
//            File dir = new File(uploadDir);
//            if (!dir.exists()) dir.mkdirs();
//
//            String uniqueName = System.currentTimeMillis() + "_" + uploadFileName;
//            File dest = new File(dir, uniqueName);
//
//            InputStream in = new FileInputStream(upload);
//            OutputStream out = new FileOutputStream(dest);
//
//            byte buffer[] = new byte[1024];
//            int len = 0;
//
//            while ((len = in.read(buffer)) > 0) {
//                out.write(buffer, 0, len);
//            }
//
//            in.close();
//            out.close();
//
//            project.setFilename(uploadFileName);
//            project.setFilepath("uploads/" + uniqueName);
//        }
//
//        project.setOwner(emp);
//        projectService.save(project);
//
//        ServletActionContext.getRequest().setAttribute("project", project);
//
//        return SUCCESS;
//    }
//}

package com.example.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.struts2.ServletActionContext;
import com.example.model.Employee;
import com.example.model.Project;
import com.example.service.ProjectService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class AddProjectAction extends ActionSupport {

    private Project project = new Project();
    private File upload;
    private String uploadFileName;
    private String uploadContentType;

    @Autowired
    private ProjectService projectService;

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public File getUpload() { return upload; }
    public void setUpload(File upload) { this.upload = upload; }

    public String getUploadFileName() { return uploadFileName; }
    public void setUploadFileName(String uploadFileName) { this.uploadFileName = uploadFileName; }

    public String getUploadContentType() { return uploadContentType; }
    public void setUploadContentType(String uploadContentType) { this.uploadContentType = uploadContentType; }

    @SuppressWarnings("unchecked")
    @Override
    public String execute() throws Exception {

        Map session = ActionContext.getContext().getSession();
        Employee emp = (Employee) session.get("loggedEmployee");

        if (emp == null) {
            addActionError("Please login first.");
            return "login";
        }

        if (project.getTitle() == null || "".equals(project.getTitle().trim())) {
            addActionError("Title required");
            return INPUT;
        }

        if (upload != null) {
            String uploadDir = ServletActionContext.getServletContext().getRealPath("/uploads");
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String uniqueName = System.currentTimeMillis() + "_" + uploadFileName;
            File dest = new File(dir, uniqueName);

            InputStream in = new FileInputStream(upload);
            OutputStream out = new FileOutputStream(dest);
            byte buffer[] = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.close();

            project.setFilename(uploadFileName);
            project.setFilepath("uploads/" + uniqueName);
        }

        // IMPORTANT: set owner so owner-based queries work
        project.setOwner(emp);
        projectService.save(project);

        // Pass to JSP
        ServletActionContext.getRequest().setAttribute("project", project);

        return SUCCESS;
    }
}
