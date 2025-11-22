<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    com.example.model.Employee empObj = (com.example.model.Employee) session.getAttribute("loggedEmployee");
    if (empObj == null) {
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
      #content { margin-left: 260px; }
      .logout-btn {
          position: absolute;
          bottom: 20px; left: 20px; right: 20px;
          font-weight: bold; border-radius: 30px;
      }
  </style>

</head>

<body class="bg-light">

<!-- SIDEBAR -->
<div id="sidebar">
    <h4 class="mb-4">User Panel</h4>

    <p><strong>Name:</strong><br> <%= empObj.getName() %></p>
    <p><strong>Department:</strong><br> <%= empObj.getDepartment() %></p>

    <hr>

    <div class="d-grid gap-2">
        <a href="index.jsp" class="btn btn-light">Dashboard</a>
        <a href="viewProjects" class="btn btn-light">My Projects</a>
        <a href="viewEmployees" class="btn btn-light">View Employees</a>
        <a href="editEmployee.jsp" class="btn btn-light">Edit Employee</a>
    </div>

    <a href="index.jsp" class="btn btn-outline-light logout-btn">Logout</a>
</div>


<!-- MAIN CONTENT -->
<div id="content" class="p-5">

    <h2>Welcome, <%= empObj.getName() %></h2>
    <p>Your assigned and owned projects are listed below.</p>

    <!-- EMPLOYEE PROJECT LIST -->
    <h4 class="mt-4">Your Projects</h4>

    <s:if test="projects == null || projects.size() == 0">
        <div class="alert alert-info mt-3">No projects assigned to you.</div>
    </s:if>

    <s:if test="projects.size() > 0">
        <table class="table table-striped mt-3">
          <thead>
            <tr>
              <th>Title</th>
              <th>Description</th>
              <th>Owner</th>
              <th>File</th>
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
              </tr>
            </s:iterator>
          </tbody>
        </table>
    </s:if>

</div>

</body>
</html>
