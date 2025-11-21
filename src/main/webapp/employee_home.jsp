<%@ taglib prefix="s" uri="/struts-tags" %>
<%
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
  <title>Employee Home</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"/>

  <style>
      /* Sidebar Always Visible */
      #sidebar {
          width: 250px;
          min-height: 100vh;
          background: #0d6efd;
          color: white;
          padding: 20px;
          position: fixed;
          top: 0;
          left: 0;
      }

      /* Page Content */
      #content {
          margin-left: 260px;
      }

      .logout-btn {
          position: absolute;
          bottom: 20px;
          left: 20px;
          right: 20px;
          font-weight: bold;
          border-radius: 30px;
      }
  </style>

</head>

<body class="bg-light">

<!-- Sidebar -->
<div id="sidebar">
    <h4 class="mb-4">User Panel</h4>

    <p><strong>Name:</strong><br> <%= ((com.example.model.Employee)o).getName() %></p>
    <p><strong>Department:</strong><br> <%= ((com.example.model.Employee)o).getDepartment() %></p>

    <hr>

    <div class="d-grid gap-2">
        <a href="index.jsp" class="btn btn-light">Dashboard</a>
        <a href="viewEmployees" class="btn btn-light">View Employees</a>
        <a href="editEmployee.jsp" class="btn btn-light">Edit Employee</a>
    </div>

    <!-- Logout -->
    <a href="index.jsp" class="btn btn-outline-light logout-btn">Logout</a>
</div>

<!-- MAIN CONTENT -->
<div id="content" class="p-5">
    <h2>Welcome, <%= ((com.example.model.Employee)o).getName() %></h2>
    <p>Your dashboard is ready.</p>
    
    <a href="add_project.jsp" class="btn btn-success">Add Project</a>
<a href="viewProjects" class="btn btn-secondary">My Projects</a>
</div>

</body>
</html>
