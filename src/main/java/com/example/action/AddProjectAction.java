package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import com.example.model.Project;
import com.example.model.Employee;
import com.example.service.ProjectService;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class AddProjectAction extends ActionSupport {

    private Project project = new Project();

    // Struts file upload fields
    private File upload;             // uploaded file
    private String uploadFileName;
    private String uploadContentType;

    @Autowired
    private ProjectService projectService;

    // getters & setters for project and upload fields
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public File getUpload() { return upload; }
    public void setUpload(File upload) { this.upload = upload; }
    public String getUploadFileName() { return uploadFileName; }
    public void setUploadFileName(String uploadFileName) { this.uploadFileName = uploadFileName; }
    public String getUploadContentType() { return uploadContentType; }
    public void setUploadContentType(String uploadContentType) { this.uploadContentType = uploadContentType; }

    @Override
    public String execute() throws Exception {
        // get logged user
        Map<String,Object> session = ActionContext.getContext().getSession();
        Employee e = (Employee) session.get("loggedEmployee");
        if (e == null) {
            addActionError("Please login first.");
            return LOGIN;
        }

        if (project.getTitle() == null || project.getTitle().trim().isEmpty()
        		 || project.getDescription() == null || project.getDescription().trim().isEmpty()) {

        		    addActionError("Please fill all required fields before submitting!");
        		    return INPUT;
        		}

        // handle file upload if present
        String savedPath = null;
        if (upload != null) {
            String uploadsDir = ServletActionContext.getServletContext().getRealPath("/uploads");
            File dir = new File(uploadsDir);
            if (!dir.exists()) dir.mkdirs();

            // unique filename - timestamp + original name
            String newName = System.currentTimeMillis() + "_" + uploadFileName;
            File dest = new File(dir, newName);

            // copy file
            try (InputStream in = new FileInputStream(upload);
                 OutputStream out = new FileOutputStream(dest)) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) > 0) out.write(buffer, 0, len);
            }

            savedPath = "uploads/" + newName; // relative path to webapp
            project.setFilename(uploadFileName);
            project.setFilepath(savedPath);
        }

        // set owner
        project.setOwner(e);
        projectService.save(project);

        // keep project in request for confirmation if needed
        ServletActionContext.getRequest().setAttribute("project", project);

        return SUCCESS;
    }
}
