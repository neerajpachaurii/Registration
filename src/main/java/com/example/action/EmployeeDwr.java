package com.example.action;

import java.util.*;
import com.example.model.Employee;
import com.example.service.EmployeeService;

public class EmployeeDwr {

	private EmployeeService employeeService;

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public EmployeeService getEmployeeService() {
		return this.employeeService;
	}

	public List<Map<String, Object>> getEmployees() {
		List<Employee> list = employeeService.getAll();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		if (list == null)
			return rows;
		for (Employee e : list) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", e.getId());
			m.put("name", e.getName());
			m.put("email", e.getEmail());
			m.put("department", e.getDepartment());
			m.put("salary", e.getSalary());
			m.put("status", e.getStatus());
			rows.add(m);
		}
		return rows;
	}

	public boolean usernameExists(String username) {

		System.out.println("\n==== DWR CALL: usernameExists() ====");
		System.out.println("Username received: " + username);

		try {
			if (employeeService == null) {
				System.out.println("ERROR: employeeService is NULL");
				return false;
			}

			boolean exists = employeeService.employeeExists(username);
			System.out.println("Username exists? => " + exists);

			return exists;

		} catch (Exception ex) {
			System.out.println("ERROR in usernameExists(): " + ex);
			ex.printStackTrace();
			return false;
		}
	}

	public String register(Employee e) {

		System.out.println("\n==== DWR CALL: register() ====");
		System.out.println("Employee object received: " + (e == null ? "null" : e));

		if (e != null) {
			System.out.println("USERNAME  : " + e.getUsername());
			System.out.println("EMAIL     : " + e.getEmail());
			System.out.println("DEPARTMENT: " + e.getDepartment());
			System.out.println("ROLE      : " + e.getRole());
		}

		try {
			if (e == null)
				return "ERROR: Invalid employee data";

			if (employeeService == null) {
				System.out.println("ERROR: employeeService is NULL");
				return "ERROR: employeeService not initialized";
			}

			if (employeeService.employeeExists(e.getUsername())) {
				return "ALREADY_EXISTS";
			}

			employeeService.save(e);

			System.out.println("Employee Saved Successfully!");
			return "SUCCESS";

		} catch (Exception ex) {
			System.out.println("ERROR in register(): " + ex);
			ex.printStackTrace();
			return "ERROR: " + ex.getMessage();
		}
	}
}
