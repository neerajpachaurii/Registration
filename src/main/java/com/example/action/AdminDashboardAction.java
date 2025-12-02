//package com.example.action;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.struts2.ServletActionContext;
//
//import com.example.model.Employee;
//import com.example.model.Project;
//import com.example.service.EmployeeService;
//import com.example.service.ProjectService;
//import com.opensymphony.xwork2.ActionSupport;
//
//public class AdminDashboardAction extends ActionSupport {
//
//	private EmployeeService employeeService;
//	private ProjectService projectService;
//
//	public void setEmployeeService(EmployeeService s) {
//		this.employeeService = s;
//	}
//
//	public void setProjectService(ProjectService s) {
//		this.projectService = s;
//	}
//
//	// OUTPUT LISTS
//	private List<Employee> employees;
//	private List<Project> projects;
//
//	// SEARCH INPUTS
//	private String searchTitle;
//	private String searchOwner;
//	private String searchDept;
//	private String searchEmail;
//
//	// PAGINATION
//	private int page = 1;
//	private int pageSize = 5;
//	private int totalPages;
//
//	// Stream for Excel export (Struts stream result will use this)
//	private InputStream inputStream;
//
//	public InputStream getInputStream() {
//		return inputStream;
//	}
//
//	public List<Employee> getEmployees() {
//		return employees;
//	}
//
//	public List<Project> getProjects() {
//		return projects;
//	}
//
//	public void setSearchTitle(String s) {
//		this.searchTitle = s;
//	}
//
//	public void setSearchOwner(String s) {
//		this.searchOwner = s;
//	}
//
//	public void setSearchDept(String s) {
//		this.searchDept = s;
//	}
//
//	public void setSearchEmail(String s) {
//		this.searchEmail = s;
//	}
//
//	public void setPage(int page) {
//		this.page = page;
//	}
//
//	public int getPage() {
//		return page;
//	}
//
//	public int getTotalPages() {
//		return totalPages;
//	}
//
//	@Override
//	public String execute() throws Exception {
//
//		Employee admin = (Employee) ServletActionContext.getRequest().getSession().getAttribute("loggedEmployee");
//
//		if (admin == null || !"ADMIN".equals(admin.getRole())) {
//			return "login";
//		}
//
//		// LOAD ALL
//		List<Project> allProjects = projectService.getAllProjects();
//		List<Employee> allEmployees = employeeService.getAll();
//
//		if (allProjects == null)
//			allProjects = new ArrayList<Project>();
//		if (allEmployees == null)
//			allEmployees = new ArrayList<Employee>();
//
//		// FILTER PROJECTS
//		List<Project> filteredProjects = new ArrayList<Project>();
//		for (Project p : allProjects) {
//			boolean match = true;
//
//			if (searchTitle != null && !"".equals(searchTitle.trim())) {
//				if (p.getTitle() == null || !p.getTitle().toLowerCase().contains(searchTitle.toLowerCase())) {
//					match = false;
//				}
//			}
//
//			if (searchOwner != null && !"".equals(searchOwner.trim())) {
//				String owner = (p.getOwner() != null) ? p.getOwner().getName() : "";
//				if (owner == null || !owner.toLowerCase().contains(searchOwner.toLowerCase())) {
//					match = false;
//				}
//			}
//
//			if (match) {
//				filteredProjects.add(p);
//			}
//		}
//
//		// FILTER EMPLOYEES
//		List<Employee> filteredEmployees = new ArrayList<Employee>();
//		for (Employee e : allEmployees) {
//			boolean match = true;
//
//			if (searchDept != null && !"".equals(searchDept.trim())) {
//				if (e.getDepartment() == null || !e.getDepartment().toLowerCase().contains(searchDept.toLowerCase())) {
//					match = false;
//				}
//			}
//
//			if (searchEmail != null && !"".equals(searchEmail.trim())) {
//				if (e.getEmail() == null || !e.getEmail().toLowerCase().contains(searchEmail.toLowerCase())) {
//					match = false;
//				}
//			}
//
//			if (match) {
//				filteredEmployees.add(e);
//			}
//		}
//
//		// PAGINATION
//		int total = filteredProjects.size();
//		totalPages = (total + pageSize - 1) / pageSize;
//		if (totalPages == 0)
//			totalPages = 1;
//		if (page < 1)
//			page = 1;
//		if (page > totalPages)
//			page = totalPages;
//
//		int start = (page - 1) * pageSize;
//		int end = Math.min(start + pageSize, total);
//
//		// handle case when filteredProjects is empty or start==end
//		if (start >= 0 && end >= start && !filteredProjects.isEmpty()) {
//			projects = filteredProjects.subList(start, end);
//		} else {
//			projects = new ArrayList<Project>();
//		}
//
//		employees = filteredEmployees;
//
//		return SUCCESS;
//	}
//
//	public String exportToExcel() {
//		try {
//			// Session check
//			Employee admin = (Employee) ServletActionContext.getRequest().getSession().getAttribute("loggedEmployee");
//
//			if (admin == null || !"ADMIN".equals(admin.getRole())) {
//				addActionError("Access Denied");
//				return ERROR;
//			}
//
//			// Load all employees from service
//			employees = employeeService.getAll();
//
//			if (employees == null || employees.isEmpty()) {
//				addActionError("No Employee Data Found to Export");
//				return ERROR;
//			}
//
//			StringBuilder sb = new StringBuilder();
//
//			// Header
//			sb.append("ID\tName\tEmail\tDepartment\tSalary\tStatus\tRole\n");
//
//			for (Employee e : employees) {
//				sb.append(nullSafeString(e.getId())).append("\t").append(nullSafeString(e.getName())).append("\t")
//						.append(nullSafeString(e.getEmail())).append("\t").append(nullSafeString(e.getDepartment()))
//						.append("\t").append(nullSafeString(e.getSalary())).append("\t")
//						.append(nullSafeString(e.getStatus())).append("\t").append(nullSafeString(e.getRole()))
//						.append("\n");
//			}
//
//			byte[] bytes = sb.toString().getBytes("UTF-8");
//			inputStream = new ByteArrayInputStream(bytes);
//
//			// Struts stream result will use getInputStream()
//			return "excel";
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			addActionError("Error exporting data: " + e.getMessage());
//			return ERROR;
//		}
//	}
//	
//	
//
//	// helper: avoid NPE while appending
//	private String nullSafeString(Object o) {
//		return (o == null) ? "" : o.toString();
//	}
//}
package com.example.action;

import java.io.ByteArrayInputStream;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.example.model.Employee;
import com.example.model.Project;
import com.example.service.EmployeeService;
import com.example.service.ProjectService;
import com.opensymphony.xwork2.ActionSupport;


public class AdminDashboardAction extends ActionSupport {

    private EmployeeService employeeService;
    private ProjectService projectService;

    public void setEmployeeService(EmployeeService s) {
        this.employeeService = s;
    }

    public void setProjectService(ProjectService s) {
        this.projectService = s;
    }

    private List<Employee> employees;
    private List<Project> projects;

    // SEARCH
    private String searchTitle;
    private String searchOwner;
    private String searchDept;
    private String searchEmail;

    // PAGINATION
    private int page = 1;
    private int pageSize = 5;
    private int totalPages;

    // Excel stream
    private InputStream inputStream;

    public InputStream getInputStream() {
        return inputStream;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setSearchTitle(String s) { this.searchTitle = s; }
    public void setSearchOwner(String s) { this.searchOwner = s; }
    public void setSearchDept(String s) { this.searchDept = s; }
    public void setSearchEmail(String s) { this.searchEmail = s; }

    public void setPage(int page) { this.page = page; }
    public int getPage() { return page; }
    public int getTotalPages() { return totalPages; }

    @Override
    public String execute() throws Exception {

        Employee admin = (Employee) ServletActionContext.getRequest().getSession()
                .getAttribute("loggedEmployee");

        if (admin == null || !"ADMIN".equals(admin.getRole())) {
            return "login";
        }

        List<Project> allProjects = projectService.getAllProjects();
        List<Employee> allEmployees = employeeService.getAll();

        if (allProjects == null) allProjects = new ArrayList<Project>();
        if (allEmployees == null) allEmployees = new ArrayList<Employee>();

        // FILTER PROJECTS
        List<Project> filteredProjects = new ArrayList<Project>();
        for (Project p : allProjects) {
            boolean match = true;

            if (searchTitle != null && !"".equals(searchTitle.trim())) {
                if (p.getTitle() == null ||
                        !p.getTitle().toLowerCase().contains(searchTitle.toLowerCase())) {
                    match = false;
                }
            }

            if (searchOwner != null && !"".equals(searchOwner.trim())) {
                String owner = (p.getOwner() != null) ? p.getOwner().getName() : "";
                if (owner == null ||
                        !owner.toLowerCase().contains(searchOwner.toLowerCase())) {
                    match = false;
                }
            }

            if (match) filteredProjects.add(p);
        }

        // FILTER EMPLOYEES
        List<Employee> filteredEmployees = new ArrayList<Employee>();
        for (Employee e : allEmployees) {
            boolean match = true;

            if (searchDept != null && !"".equals(searchDept.trim())) {
                if (e.getDepartment() == null ||
                        !e.getDepartment().toLowerCase().contains(searchDept.toLowerCase())) {
                    match = false;
                }
            }

            if (searchEmail != null && !"".equals(searchEmail.trim())) {
                if (e.getEmail() == null ||
                        !e.getEmail().toLowerCase().contains(searchEmail.toLowerCase())) {
                    match = false;
                }
            }

            if (match) filteredEmployees.add(e);
        }

        // PAGINATION
        int total = filteredProjects.size();
        totalPages = (total + pageSize - 1) / pageSize;

        if (totalPages == 0) totalPages = 1;
        if (page < 1) page = 1;
        if (page > totalPages) page = totalPages;

        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);

        if (start >= 0 && end >= start && !filteredProjects.isEmpty()) {
            projects = filteredProjects.subList(start, end);
        } else {
            projects = new ArrayList<Project>();
        }

        employees = filteredEmployees;

        return SUCCESS;
    }

    // EMPLOYEES EXCEL EXPORT
    public String exportToExcel() {
        try {
            Employee admin = (Employee) ServletActionContext.getRequest().getSession()
                    .getAttribute("loggedEmployee");

            if (admin == null || !"ADMIN".equals(admin.getRole())) {
                addActionError("Access Denied");
                return ERROR;
            }

            employees = employeeService.getAll();

            if (employees == null || employees.isEmpty()) {
                addActionError("No Employee Data Found to Export");
                return ERROR;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("ID\tName\tEmail\tDepartment\tSalary\tStatus\tRole\n");

            for (Employee e : employees) {
                sb.append(nullSafeString(e.getId())).append("\t")
                        .append(nullSafeString(e.getName())).append("\t")
                        .append(nullSafeString(e.getEmail())).append("\t")
                        .append(nullSafeString(e.getDepartment())).append("\t")
                        .append(nullSafeString(e.getSalary())).append("\t")
                        .append(nullSafeString(e.getStatus())).append("\t")
                        .append(nullSafeString(e.getRole())).append("\n");
            }

            inputStream = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
            return "excel";

        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    // PROJECTS EXCEL EXPORT
    public String exportProjectsToExcel() {
        try {
            Employee admin = (Employee) ServletActionContext.getRequest().getSession()
                    .getAttribute("loggedEmployee");

            if (admin == null || !"ADMIN".equals(admin.getRole())) {
                addActionError("Access Denied");
                return ERROR;
            }

            List<Project> all = projectService.getAllProjects();

            if (all == null || all.isEmpty()) {
                addActionError("No Project Data Found to Export");
                return ERROR;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("ID\tTitle\tDescription\tOwner\tAssigned Users\n");

            for (Project p : all) {

                String owner = "";
                if (p.getOwner() != null) owner = p.getOwner().getName();

                String allowed = "";
                if (p.getAllowedUsers() != null) {
                    List<String> names = new ArrayList<String>();
                    for (Employee u : p.getAllowedUsers()) {
                        names.add(u.getName());
                    }
                    allowed = join(names, ",");
                }

                sb.append(p.getId()).append("\t")
                        .append(nullSafeString(p.getTitle())).append("\t")
                        .append(nullSafeString(p.getDescription())).append("\t")
                        .append(owner).append("\t")
                        .append(allowed).append("\n");
            }

            inputStream = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
            return "excelProjects";

        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }
 // PRINTING EMPLOYEE REPORT
    @SuppressWarnings("unchecked")
    public String printEmployeeReport() {
        try {
            // 1) security check: only ADMIN allowed
            com.example.model.Employee admin =
                    (com.example.model.Employee) ServletActionContext.getRequest().getSession()
                            .getAttribute("loggedEmployee");

            if (admin == null || !"ADMIN".equals(admin.getRole())) {
                addActionError("Access Denied");
                return ERROR;
            }

            // 2) ensure employeeService available
            if (employeeService == null) {
                throw new RuntimeException("REPORT ERROR: employeeService is null. Check Spring wiring.");
            }

            // 3) load JRXML from classpath
            java.io.InputStream stream = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("reports/employeeReport.jrxml");

            if (stream == null) {
                throw new RuntimeException("REPORT ERROR: JRXML not found at 'reports/employeeReport.jrxml'. Place employeeReport.jrxml under src/main/resources/reports/ and rebuild.");
            }

            // 4) compile report
            net.sf.jasperreports.engine.JasperReport report =
                    net.sf.jasperreports.engine.JasperCompileManager.compileReport(stream);

            // 5) get data
            java.util.List<com.example.model.Employee> list = employeeService.getAll();
            if (list == null) list = new java.util.ArrayList<com.example.model.Employee>();

            // 6) fill report (Java 7 safe)
            net.sf.jasperreports.engine.JRDataSource ds =
                    new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource(list);
            java.util.HashMap params = new java.util.HashMap();

            net.sf.jasperreports.engine.JasperPrint jp =
                    net.sf.jasperreports.engine.JasperFillManager.fillReport(report, params, ds);

            // 7) export PDF bytes
            byte[] pdf = net.sf.jasperreports.engine.JasperExportManager.exportReportToPdf(jp);

            // 8) write to response
            javax.servlet.http.HttpServletResponse resp = ServletActionContext.getResponse();
            resp.setContentType("application/pdf");
            resp.setHeader("Content-Disposition", "attachment; filename=EmployeeReport.pdf");
            resp.getOutputStream().write(pdf);
            resp.getOutputStream().flush();

            // 9) tell Struts no further result handling required
            return NONE;

        } catch (Exception ex) {
            ex.printStackTrace();
            addActionError("Failed to generate report: " + ex.getMessage());
            return ERROR;
        }
    }
    // Helper method (Java 7 compatible)
    private String join(List<String> list, String sep) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) sb.append(sep);
        }
        return sb.toString();
    }

    private String nullSafeString(Object o) {
        return (o == null) ? "" : o.toString();
    }
}
