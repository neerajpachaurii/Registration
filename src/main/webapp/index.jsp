<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>
<!-- Bootstrap CDN -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet">

<style>
.center-panel {
	max-width: 520px;
	margin: 60px auto;
}

.form-container {
	width: 400px;
	margin: 40px auto;
	padding: 25px;
	background: aqua;
	border-radius: 10px;
	box-shadow: 0 0 15px rgba(0, 0, 0, 0.3);
}
</style>
</head>
<body>
	<!-- <div class="container center-panel">
		<div class="text-center">

			<h4>USER BUTTON</h4>

			<form method="get">
			
				<button type="submit" class="btn btn-primary" formaction="login.jsp">
					Login</button>

				<button type="submit" class="btn btn-success"
					formaction="registration.jsp">Register</button>

			</form>
			
		</div>

		<hr />
	</div> -->
	<div class="form-container"
		style="display: flex; justify-content: center; align-items: center;">

		<form method="get" style="text-align: center;">
			<h4>USER BUTTON</h4>
			<!-- Login Button -->
			<button type="submit" class="btn btn-primary mx-2"
				formaction="login.jsp">Login</button>

			<!-- Register Button -->
			<button type="submit" class="btn btn-success mx-2"
				formaction="registration.jsp">Register</button>

		</form>

	</div>
	<div class="form-container"
		style="display: flex; justify-content: center; align-items: center;">

		<form method="get" style="text-align: center;">
			<h4>EMPLOYEE BUTTON</h4>
			<!-- Login Button -->
			<button type="submit" class="btn btn-primary mx-2"
				formaction="addEmployee.jsp">Register Employee</button>
 
			<!-- Register Button -->
			<button type="submit" class="btn btn-success mx-2"
				formaction="viewEmployees">View Employee</button>
<br/><br/>
            <button type="submit" class="btn btn-success mx-2"
				formaction="editEmployee.jsp">Edit Employee</button>
				
				<button type="submit" class="btn btn-primary mx-2"
				formaction="loginEmployee.jsp">Login</button>
				
				<!-- <button type="submit" class="btn btn-success mx-2"
				formaction="employee_success.jsp">Success Employee</button> -->
				
				
		</form>

	</div>


</body>
</html>
