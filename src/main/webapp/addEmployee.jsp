<%-- 
 
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
  --%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Employee</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
body { background: #f5f7fa; font-family: "Inter", sans-serif; }
.card { border-radius: 12px; }
.required::after { content: " *"; color: #d9534f; font-weight: 600; }
.btn-primary { background: linear-gradient(90deg, #2563eb, #4f46e5); border: none; }
</style>

<!-- DWR -->
<script src="${pageContext.request.contextPath}/dwr/engine.js"></script>
<script src="${pageContext.request.contextPath}/dwr/util.js"></script>
<script src="${pageContext.request.contextPath}/dwr/interface/EmployeeDwr.js"></script>

<script>
function showMessage(type, text) {
    var div = document.getElementById('alertBox');
    div.innerHTML =
        '<div class="alert ' +
        (type === 'success' ? 'alert-success' :
        type === 'error' ? 'alert-danger' : 'alert-info') +
        ' alert-dismissible fade show" role="alert">' +
        text +
        '<button type="button" class="btn-close" data-bs-dismiss="alert"></button></div>';
}

function buildEmployeeFromForm() {
    return {
        username: document.getElementById('username').value.trim(),
        password: document.getElementById('password').value,
        name: document.getElementById('name').value.trim(),
        email: document.getElementById('email').value.trim(),
        department: document.getElementById('department').value.trim(),
        salary: parseFloat(document.getElementById('salary').value) || 0,
        address: document.getElementById('address').value.trim(),
        country: document.getElementById('country').value.trim(),
        expenses: parseFloat(document.getElementById('expenses').value) || 0,
        father_name: document.getElementById('father_name').value.trim(),
        mother_name: document.getElementById('mother_name').value.trim(),
        role: document.getElementById('role').value.trim(),
        status: document.getElementById('status').value.trim()
    };
}

function registerUsingDwr() {
    var emp = buildEmployeeFromForm();

    if(!emp.username || !emp.password || !emp.name || !emp.email) {
        showMessage("error","Please fill required fields: Username, Password, Name and Email.");
        return;
    }

    var btn = document.getElementById("registerBtn");
    btn.disabled = true;
    btn.innerHTML = "Processing...";

    // STEP 1:  FOR CHECKING USERNAME EXISTENCE
    EmployeeDwr.usernameExists(emp.username, function(exists) {

        if(exists) {
            showMessage("info","Username already exists. Try another.");
            btn.disabled = false;
            btn.innerHTML = "Register Using DWR";
            return;
        }

        // STEP 2: REGISTERING USER
        EmployeeDwr.register(emp, function(result) {

            console.log("DWR REGISTER RESULT:", result);

            if(result === "SUCCESS") {

                console.log("REGISTERED EMPLOYEE DETAILS:");
                console.log(JSON.stringify(emp, null, 4));

                showMessage("success","Employee Registered Successfully!");
            }
            else if(result === "ALREADY_EXISTS") {
                showMessage("info","Username already exists.");
            }
            else if(result.startsWith("ERROR:")) {
                showMessage("error","Registration Failed: " + result.substring(6));
            }
            else {
                showMessage("error","Unexpected Response: " + result);
            }

            btn.disabled = false;
            btn.innerHTML = "Register Using DWR";
        });

    });
}
</script>

</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow p-4" style="max-width: 700px; margin: auto;">

        <h3 class="text-center mb-3 text-primary">Register Employee</h3>

        <div id="alertBox"></div>

        <s:form action="addEmployee" method="post" theme="simple">

            <div class="row g-3">

                <div class="col-md-6">
                    <label class="form-label required">Username</label>
                    <s:textfield id="username" name="employee.username" cssClass="form-control"/>
                </div>

                <div class="col-md-6">
                    <label class="form-label required">Name</label>
                    <s:textfield id="name" name="employee.name" cssClass="form-control"/>
                </div>

                <div class="col-md-6">
                    <label class="form-label required">Email</label>
                    <s:textfield id="email" name="employee.email" cssClass="form-control"/>
                </div>

                <div class="col-md-6">
                    <label class="form-label">Department</label>
                    <s:textfield id="department" name="employee.department" cssClass="form-control"/>
                </div>

                <div class="col-md-6">
                    <label class="form-label">Salary</label>
                    <s:textfield id="salary" name="employee.salary" cssClass="form-control"/>
                </div>

                <div class="col-md-6">
                    <label class="form-label">Address</label>
                    <s:textfield id="address" name="employee.address" cssClass="form-control"/>
                </div>

                <div class="col-md-6">
                    <label class="form-label">Country</label>
                    <s:textfield id="country" name="employee.country" cssClass="form-control"/>
                </div>

                <div class="col-md-6">
                    <label class="form-label">Expenses</label>
                    <s:textfield id="expenses" name="employee.expenses" cssClass="form-control"/>
                </div>

                <div class="col-md-6">
                    <label class="form-label">Father Name</label>
                    <s:textfield id="father_name" name="employee.father_name" cssClass="form-control"/>
                </div>

                <div class="col-md-6">
                    <label class="form-label">Mother Name</label>
                    <s:textfield id="mother_name" name="employee.mother_name" cssClass="form-control"/>
                </div>

                <div class="col-md-6">
                    <label class="form-label">Role</label>
                    <s:textfield id="role" name="employee.role" cssClass="form-control"/>
                </div>

                <div class="col-md-6">
                    <label class="form-label">Status</label>
                    <s:textfield id="status" name="employee.status" cssClass="form-control" value="Active"/>
                </div>
                
                
                <div class="col-md-12">
                    <label class="form-label required">Password</label>
                    <s:password id="password" name="employee.password" cssClass="form-control"/>
                </div>
                

            </div>

            <div class="mt-4">

                <button type="button" id="registerBtn" class="btn btn-primary w-100 mb-2"
                        onclick="registerUsingDwr()">
                    REGISTER USING DWR
                </button>

                <button type="submit" class="btn btn-outline-dark w-100">
                    REGISTER WITHOUT DWR
                </button>

            </div>

        </s:form>

        <div class="text-center mt-3">
            <a href="loginEmployee" class="btn btn-outline-secondary">Already have an account? Login</a>
        </div>

    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
