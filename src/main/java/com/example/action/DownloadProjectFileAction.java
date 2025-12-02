package com.example.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;

public class DownloadProjectFileAction extends ActionSupport {

    private String fileName;            
    private InputStream fileInputStream;
    private String downloadFileName;

  
    private static final String DEFAULT_RELATIVE_DIR = "uploads/project_files";

    public void setFileName(String fileName) {
        if (fileName != null) {
            try {
               
                this.fileName = URLDecoder.decode(fileName, "UTF-8").trim();
            } catch (Exception e) {
                this.fileName = fileName.trim();
            }
        } else {
            this.fileName = null;
        }
    }


    public InputStream getFileInputStream() {
        return fileInputStream;
    }


    public String getDownloadFileName() {
        return downloadFileName;
    }

    @Override
    public String execute() throws Exception {

        System.out.println("[DownloadProjectFileAction] invoked. fileName=" + fileName);

        if (fileName == null || fileName.isEmpty()) {
            addActionError("Invalid file specified.");
            System.out.println("Invalid fileName");
            return "error";
        }

        if (fileName.contains("..") || fileName.contains("/") || fileName.contains("\\")) {
            addActionError("Invalid file name.");
            System.out.println(" Unsafe filename: " + fileName);
            return "error";
        }

        String configuredDir = ServletActionContext.getServletContext().getInitParameter("upload.dir");
        String realUploadDir;

        if (configuredDir != null && !configuredDir.trim().isEmpty()) {
            realUploadDir = configuredDir.trim();
            System.out.println(" Using configured upload.dir = " + realUploadDir);
        } else {
            realUploadDir = ServletActionContext.getServletContext().getRealPath(DEFAULT_RELATIVE_DIR);
            System.out.println(" No external upload.dir configured. Using webapp path = " + realUploadDir);
        }

        if (realUploadDir == null) {
            addActionError("Server misconfiguration: upload directory not found.");
            System.out.println(" realUploadDir is null");
            return "error";
        }

        File dir = new File(realUploadDir);

        System.out.println(" Checking directory: " + dir.getAbsolutePath());
        if (dir.exists() && dir.isDirectory()) {
            File[] list = dir.listFiles();
            System.out.println(" Files in dir: " + (list == null ? "null" : list.length));
            if (list != null) {
                for (File f : list) {
                    System.out.println("    - " + f.getName());
                }
            }
        } else {
            System.out.println("directory does not exist or is not a directory");
        }

        File file = new File(realUploadDir, fileName);
        System.out.println(" Trying to open file: " + file.getAbsolutePath());

        if (!file.exists() || !file.isFile()) {
            addActionError("Requested file not found: " + fileName);
            System.out.println("FILE NOT FOUND");
            return "error";
        }

        fileInputStream = new FileInputStream(file);
        downloadFileName = file.getName();

        System.out.println(" File opened, ready to stream: " + downloadFileName);
        return SUCCESS;
    }

}
