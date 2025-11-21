<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Registration Successful</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="bg-light">
<div class="container mt-5">
  <div class="card shadow p-4" style="max-width:600px; margin:auto;">
    <!-- show any action messages -->
    <s:if test="hasActionMessages()">
      <div class="alert alert-success">
        <s:actionmessage/>
      </div>
    </s:if>

    <h3 class="text-center mb-3">Registration Successful</h3>
    <p>Your account has been created successfully. You can now login.</p>

    <div class="d-grid gap-2">
      <a href="login.jsp" class="btn btn-primary">Login as User</a>
      <a href="loginEmployee.jsp" class="btn btn-secondary">Login as Employee</a>
      <a href="index.jsp" class="btn btn-outline-dark">Home</a>
    </div>
  </div>
</div>
</body>
</html>
	