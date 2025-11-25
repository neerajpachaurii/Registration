package com.example.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.example.model.Employee;
import com.example.model.Project;
import com.example.service.ProjectService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import java.io.File;
import java.util.Map;

public class AddProjectAction extends ActionSupport {

	private Project project = new Project();
	private File upload;
	private String uploadFileName;
	private String uploadContentType;

	@Autowired
	private ProjectService projectService;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	@Override
	public String execute() throws Exception {

		Map<String, Object> session = ActionContext.getContext().getSession();
		Employee emp = (Employee) session.get("loggedEmployee");

		if (emp == null) {
			addActionError("Please login first.");
			return "login";
		}

		if (project.getTitle() == null || project.getTitle().trim().isEmpty()) {
			addActionError("Title required");
			return INPUT;
		}
		if (upload != null) {

			String basePath = ServletActionContext.getServletContext().getRealPath("/uploads"); // .getRealPath("/uploads/project_files");

			File folder = new File(basePath);
			if (!folder.exists())
				folder.mkdirs();

			String uniqueName = System.currentTimeMillis() + "_" + uploadFileName;

			File dest = new File(folder, uniqueName);

			FileUtils.copyFile(upload, dest);

			project.setFilename(uploadFileName);
			project.setFilepath("uploads/project_files/" + uniqueName);
		}

		project.setOwner(emp);

		projectService.save(project);

		return SUCCESS;
	}
}
