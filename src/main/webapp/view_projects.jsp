<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8"/>
  <title>Projects</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="bg-light">
<div class="container mt-4">
  <div class="card p-4 shadow">
    <h4>Projects</h4>

    <table class="table">
      <thead>
        <tr><th>Title</th><th>Description</th><th>Owner</th><th>File</th><th>Actions</th></tr>
      </thead>
      <tbody>
        <s:iterator value="projects" var="p">
          <tr>
            <td><s:property value="#p.title"/></td>
            <td style="max-width:400px; white-space:pre-wrap;"><s:property value="#p.description"/></td>
            <td><s:property value="#p.owner.name"/></td>
            <td>
              <s:if test="#p.filepath != null">
                <a href="<s:property value='#p.filepath'/>" target="_blank">Download</a>
              </s:if>
            </td>
            <td>
              <!-- If owner or admin, allow delete/edit (you can add actions) -->
              <s:if test="#session.loggedEmployee.role == 'ADMIN' or #session.loggedEmployee.id == #p.owner.id">
                 <a href="editProject?id=<s:property value='#p.id'/>" class="btn btn-sm btn-outline-primary">Edit</a>
                 <a href="deleteProject?id=<s:property value='#p.id'/>" class="btn btn-sm btn-outline-danger" onclick="return confirm('Delete?')">Delete</a>
              </s:if>
            </td>
          </tr>
        </s:iterator>
      </tbody>
    </table>

    <a href="add_Project.jsp" class="btn btn-success">Add Project</a>
  </div>
</div>
</body>
</html>
