<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8"/>
  <title>Employee Login</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<div class="container mt-5">
  <div class="card p-4 shadow" style="max-width: 420px; margin: auto;">
    <h3 class="text-center mb-3">Employee Login</h3>

    <s:form action="loginEmployee" method="post" theme="simple">
      <s:if test="hasActionErrors()">
        <div class="alert alert-danger"><s:actionerror/></div>
      </s:if>

      <div class="mb-3">
        <label>Username</label>
        <!-- ModelDriven will populate employee.username -->
        <s:textfield name="username" cssClass="form-control"/>
      </div>

      <div class="mb-3">
        <label>Password</label>
        <s:password name="password" cssClass="form-control"/>
      </div>

      <div class="mb-3">
        <label>Login As</label>
        <!-- This will set employee.role via ModelDriven -->
        <s:select name="role" cssClass="form-control" list="{'USER','ADMIN'}" headerKey="" headerValue="-- Select Role --"/>
      </div>

      <div class="d-grid gap-2">
        <s:submit cssClass="btn btn-primary" value="Login"/>
        <a href="addEmployee.jsp" class="btn btn-secondary">New User? Register</a>
      </div>
    </s:form>
  </div>
</div>
</body>
</html>
