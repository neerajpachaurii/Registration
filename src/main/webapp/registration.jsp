<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Account</title>

<!-- Bootstrap 5 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" 
      rel="stylesheet">

<style>
   body {
       background: pink;
       font-family: "Times New Roman";
   }

   .form-container {
       width: 400px;
       margin: 40px auto;
       padding: 25px;
       background: aqua;
       border-radius: 10px;
       box-shadow: 0 0 15px rgba(0,0,0,0.3);
   }

   .form-container h2 {
       text-align: center;
       margin-bottom: 20px;
       font-weight: bold;
   }

   .login-link {
       text-align: center;
       margin-top: 10px;
       display: block;
   }
</style>

</head>
<body>

<div class="form-container">

    <h2>Create Account</h2>

    <s:form action="register" method="post" theme="simple">

        <s:fielderror />

        <div class="mb-3">
            <label class="form-label">Username</label>
            <s:textfield name="username" cssClass="form-control" required="true"/>
        </div>

        
        <div class="mb-3">
            <label class="form-label">Full Name</label>
            <s:textfield name="name" cssClass="form-control" required="true"/>
        </div>

        <div class="mb-3">
            <label class="form-label">Email</label>
            <s:textfield name="email" cssClass="form-control" required="true"/>
        </div>

        <div class="mb-3">
            <label class="form-label">Phone</label>
            <s:textfield name="phone" cssClass="form-control" required="true"/>
        </div>
        <div class="mb-3">
            <label class="form-label">Password</label>
            <s:password name="password" cssClass="form-control" required="true"/>
        </div>
        

        <button type="submit" class="btn btn-success w-100">Register</button>

    </s:form>
<div style="text-align:center; margin-top:10px;">
    <a href="login.jsp" class="btn btn-secondary">Already have an account? Login</a>
</div>
</div>

</body>
</html>
