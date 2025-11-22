<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    com.example.model.Employee emp = (com.example.model.Employee) session.getAttribute("loggedEmployee");
    if (emp == null) { response.sendRedirect("loginEmployee.jsp"); return; }
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Admin Home</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"/>

  <style>
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

<!-- SIDEBAR -->
<div id="sidebar">
    <h4 class="mb-4">Admin Panel</h4>

    <p><strong>Welcome:</strong><br> <%= emp.getName() %></p>
    <p><strong>Role:</strong><br> ADMIN</p>

   <!--  <div class="d-grid gap-2">
        <a href="index.jsp" class="btn btn-light">Dashboard</a>
        <a href="add_Project.jsp" class="btn btn-light">Add Project</a>
         <a href="view_projects.jsp" class="btn btn-light">View Projects</a>
        <a href="viewEmployees" class="btn btn-light">Employees</a>
    </div>
 -->
 <div class="d-grid gap-2">
    <a href="index.jsp" class="btn btn-light">Dashboard</a>
    <a href="add_Project.jsp" class="btn btn-light">Add Project</a>
    <a href="viewProjects" class="btn btn-light">View Projects</a>
    <a href="viewEmployees" class="btn btn-light">Employees</a>
</div>
 
    <a href="index.jsp" class="btn btn-outline-light logout-btn">Logout</a>
</div>


<!-- MAIN CONTENT -->
<div id="content" class="p-5">

    <h2>Admin Dashboard</h2>
    <p>Manage all projects and assign them to employees.</p>

    <h4 class="mt-4">All Projects</h4>

    <table class="table table-bordered table-striped mt-3">
      <thead>
        <tr>
          <th>Title</th>
          <th>Description</th>
          <th>Owner</th>
          <th>File</th>
          <th>Action</th>
        </tr>
      </thead>

      <tbody>
        <s:iterator value="projects" var="p">
          <tr>
            <td><s:property value="#p.title"/></td>

            <td style="max-width:300px; white-space:pre-wrap;">
              <s:property value="#p.description"/>
            </td>

            <td><s:property value="#p.owner.name"/></td>

            <td>
              <s:if test="#p.filepath != null">
                <a href="<%=request.getContextPath()%>/<s:property value='#p.filepath'/>"
                   target="_blank">Download</a>
              </s:if>
            </td>

            <td>
              <a href="assignProjectAccessPage?projectId=<s:property value='#p.id'/>"
                 class="btn btn-sm btn-primary">
                 Assign Access
              </a>
            </td>
          </tr>
        </s:iterator>
      </tbody>

    </table>

</div>

</body>
</html>
