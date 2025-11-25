package com.example.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;

    private String name;
    private String email;
    private String department;
    private String address;
    private String country;
    private String father_name;
    private String mother_name;
    private double expenses;
    private double salary;

    private String status;
    
    
    @Column(name="role")
    private String role;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private Set<Project> ownedProjects = new HashSet<Project>();

    @ManyToMany(mappedBy = "allowedUsers", fetch = FetchType.LAZY)
    private Set<Project> sharedProjects = new HashSet<Project>();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getFather_name() { return father_name; }
    public void setFather_name(String father_name) { this.father_name = father_name; }

    public String getMother_name() { return mother_name; }
    public void setMother_name(String mother_name) { this.mother_name = mother_name; }

    public double getExpenses() { return expenses; }
    public void setExpenses(double expenses) { this.expenses = expenses; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Set<Project> getOwnedProjects() { return ownedProjects; }
    public void setOwnedProjects(Set<Project> ownedProjects) { this.ownedProjects = ownedProjects; }

    public Set<Project> getSharedProjects() { return sharedProjects; }
    public void setSharedProjects(Set<Project> sharedProjects) { this.sharedProjects = sharedProjects; }
}
