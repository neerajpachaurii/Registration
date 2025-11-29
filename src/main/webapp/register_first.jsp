<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Register First</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="bg-light">
<div class="container mt-5">
  <div class="card shadow p-4" style="max-width:600px; margin:auto;">
    <h3 class="text-center text-danger mb-3">Employee Not Found</h3>

    <p>If you don't have an employee account yet, please register first.</p>

    <div class="d-grid gap-2">
   
      <a href="addEmployee.jsp" class="btn btn-success">Register Employee</a>
     
      <a href="registration.jsp" class="btn btn-outline-primary">Or create user account</a>
      <a href="loginEmployee.jsp" class="btn btn-secondary">Back to Login</a>
    </div>

  </div>
</div>
</body>
</html>
