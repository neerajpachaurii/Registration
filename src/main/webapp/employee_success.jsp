
 
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

    <a href="loginEmployee" class="btn btn-primary mt-3">Click Here to Login</a>
  </div>
</div>

</body>
</html>
 