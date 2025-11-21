<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8"/>
  <title>Add Project</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="bg-light">

<div class="container mt-4" style="max-width:700px;">
  <div class="card p-4 shadow">
    <h4>Add Project</h4>

    <!-- POPUP ERROR MESSAGE IF FIELDS EMPTY -->
    <s:if test="hasActionErrors()">
      <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <s:actionerror/>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
      </div>
    </s:if>

    <!-- ACTION NAME FIXED HERE -->
    <s:form action="addProject" method="post" enctype="multipart/form-data" theme="simple">
      <s:fielderror/>

      <div class="mb-3">
        <label class="form-label">Title</label>
        <s:textfield name="project.title" cssClass="form-control" required="true"/>
      </div>

      <div class="mb-3">
        <label class="form-label">Description</label>
        <s:textarea name="project.description" cssClass="form-control" rows="4" required="true"/>
      </div>

      <div class="mb-3">
        <label class="form-label">Attach file</label>
        <input type="file" name="upload" class="form-control"/>
      </div>

      <button class="btn btn-primary w-100">Save Project</button>
    </s:form>

    <div class="mt-3">
      <!-- Open via Struts action so login/permission handling भी चलेगा -->
      <a href="viewProjects" class="btn btn-secondary">View Projects</a>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
