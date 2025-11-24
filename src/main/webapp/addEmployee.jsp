
 
 <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Employee</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="card shadow p-4" style="max-width: 600px; margin: auto;">
        <h3 class="text-center mb-3 text-primary">Register Employee</h3>

        <s:form action="addEmployee" method="post" theme="simple">

  <s:fielderror/>
            <div class="mb-3">
                <label>Username</label>
                <s:textfield name="employee.username" cssClass="form-control" required="true"/>
            </div>

            

            <div class="mb-3">
                <label>Name</label>
                <s:textfield name="employee.name" cssClass="form-control" required="true"/>
            </div>

            <div class="mb-3">
                <label>Email</label>
                <s:textfield name="employee.email" cssClass="form-control" required="true"/>
            </div>

            <div class="mb-3">
                <label>Department</label>
                <s:textfield name="employee.department" cssClass="form-control" required="true"/>
            </div>

            <div class="mb-3">
                <label>Salary</label>
                <s:textfield name="employee.salary" cssClass="form-control" required="true"/>
            </div>
            
            <div class="mb-3">
                <label class="form-label">Address</label>
                <s:textfield name="employee.address" cssClass="form-control" required="true"/>
            </div>
            
            <div class="mb-3">
                <label class="form-label">Country</label>
                <s:textfield name="employee.country" cssClass="form-control" required="true"/>
            </div>
            
            <div class="mb-3">
                <label class="form-label">Expenses</label>
                <s:textfield name="employee.expenses" cssClass="form-control" required="true"/>
            </div>
            
            <div class="mb-3">
                <label class="form-label">Father_Name</label>
                <s:textfield name="employee.father_name" cssClass="form-control" required="true"/>
            </div>
            
            <div class="mb-3">
                <label class="form-label">Mother_name</label>
                <s:textfield name="employee.mother_name" cssClass="form-control" required="true"/>
            </div>
            
            <div class="mb-3">
                <label>Password</label>
                <s:password name="employee.password" cssClass="form-control" required="true"/>
            </div>
            

            <button type="submit" class="btn btn-primary w-100">Register</button>
        </s:form>
        <div style="text-align:center; margin-top:10px;">
    <a href="loginEmployee.jsp" class="btn btn-secondary">Already have an account? Login</a>
</div>
    </div>
</div>
</body>
</html>
 