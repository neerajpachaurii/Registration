<%@ taglib prefix="s" uri="/struts-tags" %>
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
  <title>Employee - Welcome</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="bg-light">
<div class="container mt-5">
  <div class="card shadow p-4" style="max-width:600px; margin:auto;">
    <h3 class="text-center text-primary mb-3">Welcome, <%= ((com.example.model.Employee)o).getName() %></h3>

    <p><strong>Username:</strong> <%= ((com.example.model.Employee)o).getUsername() %></p>
    <p><strong>Email:</strong> <%= ((com.example.model.Employee)o).getEmail() %></p>
    <p><strong>Department:</strong> <%= ((com.example.model.Employee)o).getDepartment() %></p>
    <p><strong>Salary:</strong> <%= ((com.example.model.Employee)o).getSalary() %></p>

    <div class="mt-3">
      <a href="employee_home.jsp" class="btn btn-secondary">Home</a>
<!--       <form method="post" action="logoutEmployee" style="display:inline;">
          <button  class="btn btn-danger">Logout</button>
      </form>
 -->  
       <a href="index.jsp" class="btn btn-primary">LogOut</a>
				
   </div>
  </div>
</div>
</body>
</html>
