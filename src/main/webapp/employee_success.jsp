<%-- <%@ taglib prefix="s" uri="/struts-tags" %>
<%
    // Ensure user is logged in; if not, redirect to login page
    Object o = session.getAttribute("loggedEmployee");
    if (o == null) {
        response.sendRedirect("loginEmployee.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Success</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">

    <div class="card shadow p-4" style="max-width:600px; margin:auto;">
    <h3 class="text-center text-primary mb-3">Welcome, <%= ((com.example.model.Employee)o).getName() %></h3>

    <p><strong>Username:</strong> <%= ((com.example.model.Employee)o).getUsername() %></p>
    <p><strong>Email:</strong> <%= ((com.example.model.Employee)o).getEmail() %></p>
    <p><strong>Department:</strong> <%= ((com.example.model.Employee)o).getDepartment() %></p>
    <p><strong>Salary:</strong> <%= ((com.example.model.Employee)o).getSalary() %></p>
    </div>
    
    
    <div class="alert alert-success shadow">
        <h4 class="alert-heading">Employee Saved Successfully!</h4>
        <p>Your employee has been added to the database.</p>
        <hr>
        <a href="viewEmployees" class="btn btn-primary">View Employees</a>
        <a href="addEmployee.jsp" class="btn btn-success">Add Another</a>
    </div>
</div>

</body>
</html>
 --%>
 
 <%@ page import="com.example.model.Employee" %>
<%
   Employee emp = (Employee) request.getAttribute("employee");
%>

<html>
<head>
<title>Registration Success</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">
  <div class="alert alert-success p-4 shadow">
    <h3>Employee Registered Successfully!</h3>

    <p><strong>Name:</strong> <%= emp.getName() %></p>
    <p><strong>Username:</strong> <%= emp.getUsername() %></p>

    <a href="loginEmployee.jsp" class="btn btn-primary mt-3">Click Here to Login</a>
  </div>
</div>

</body>
</html>
 