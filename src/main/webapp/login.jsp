<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8"/>
  <title>Login</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"/>

  <style>
     body { background: pink; font-family: "Times New Roman"; }
     .form-container { width:420px; margin:60px auto; padding:25px; background:aqua;
                      border-radius:10px; box-shadow:0 0 15px rgba(0,0,0,0.3); }
     h2 { text-align:center; margin-bottom:20px; font-weight:bold; }
     .btn-row { display:flex; justify-content:space-between; align-items:center; margin-top:15px; }
     .btn-row a { font-size:14px; align-self:center; }
  </style>
</head>
<body>

<div class="form-container">
  <h2>Login</h2>

  <!-- Struts form: Submits to Struts action name="login" -->
  <s:form action="login" method="post" theme="simple">

    <!-- Show action-level errors (e.g. \"User not found\", \"Invalid...\") -->
    <s:if test="hasActionErrors()">
      <div class="alert alert-danger">
        <s:actionerror/>
      </div>
    </s:if>

    <!-- Show field-level errors near form -->
    <s:fielderror/>

    <div class="mb-3">
      <label class="form-label">Username</label>
      <s:textfield name="username" cssClass="form-control"/>
    </div>

    <div class="mb-3">
      <label class="form-label">Password</label>
      <s:password name="password" cssClass="form-control"/>
    </div>

    <div class="btn-row">
      <!-- This is the correct submit button - it POSTS form to your LoginAction -->
      <s:submit cssClass="btn btn-primary" value="Login"/>

      <!-- Registration link (opens registration page action) -->
      <a href="registration.jsp" class="btn btn-secondary">New user? Register</a>
    </div>

  </s:form>
</div>

</body>
</html>
