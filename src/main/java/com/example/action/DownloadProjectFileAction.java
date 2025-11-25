package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class DownloadProjectFileAction extends ActionSupport {

	private String filepath;
	private InputStream fileInputStream;
	private String downloadFileName;

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	@Override
	public String execute() throws Exception {

		String fullPath = ServletActionContext.getServletContext().getRealPath("/") + filepath;

		File file = new File(fullPath);

		if (!file.exists()) {
			return ERROR;
		}

		fileInputStream = new FileInputStream(file);
		downloadFileName = file.getName();

		return SUCCESS;
	}
}
