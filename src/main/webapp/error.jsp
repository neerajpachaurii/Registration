<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head><title>Error</title></head>
<body>
  <div style="max-width:600px;margin:40px auto;padding:20px;border-radius:8px;background:#ffecec;">
    <h3 style="color:#d8000c">Something went wrong</h3>

    <!-- Show action errors -->
    <s:if test="hasActionErrors()">
      <div class="alert alert-danger">
        <s:actionerror />
      </div>
    </s:if>

    <!-- Helpful links -->
    <p>
      <a href="<s:url action='registrationPage'/>">Register</a> |
      <a href="<s:url action='loginPage'/>">Back to Login</a>
    </p>
  </div>
</body>
</html>
