<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Employee</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow p-4" style="max-width: 500px; margin: auto;">
        <h3 class="text-center text-primary mb-3">Edit Employee</h3>

        <s:form action="updateEmployee" method="post" theme="simple">

            <s:hidden name="employee.id" value="%{employee.id}" />

            <div class="mb-3">
                <label class="form-label">Name</label>
                <s:textfield name="employee.name" value="%{employee.name}" cssClass="form-control"/>
            </div>

            <div class="mb-3">
                <label class="form-label">Email</label>
                <s:textfield name="employee.email" value="%{employee.email}" cssClass="form-control"/>
            </div>

            <div class="mb-3">
                <label class="form-label">Department</label>
                <s:textfield name="employee.department" value="%{employee.department}" cssClass="form-control"/>
            </div>

            <div class="mb-3">
                <label class="form-label">Salary</label>
                <s:textfield name="employee.salary" value="%{employee.salary}" cssClass="form-control"/>
            </div>
            
            <div class="mb-3">
                <label class="form-label">Address</label>
                <s:textfield name="employee.address" value="%{employee.address}" cssClass="form-control"/>
            </div>
            
            <div class="mb-3">
                <label class="form-label">Country</label>
                <s:textfield name="employee.country" value="%{employee.country}" cssClass="form-control"/>
            </div>
            
            <div class="mb-3">
                <label class="form-label">Expenses</label>
                <s:textfield name="employee.expenses" value="%{employee.expenses}" cssClass="form-control"/>
            </div>
            
            <div class="mb-3">
                <label class="form-label">Father_Name</label>
                <s:textfield name="employee.father_name" value="%{employee.father_name}" cssClass="form-control"/>
            </div>
            
            <div class="mb-3">
                <label class="form-label">Mother_name</label>
                <s:textfield name="employee.mother_name" value="%{employee.mother_name}" cssClass="form-control"/>
            </div>

            <button type="submit" class="btn btn-primary w-100">Update Employee</button>

        </s:form>

        <div class="text-center mt-3">
            <a href="viewEmployees" class="btn btn-secondary btn-sm">Back</a>
        </div>

    </div>
</div>

</body>
</html>
