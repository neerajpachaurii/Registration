<%@ taglib prefix="s" uri="/struts-tags"%>

<%
com.example.model.Employee emp = (com.example.model.Employee) session.getAttribute("loggedEmployee");

if (emp == null) {
	response.sendRedirect("loginEmployee.jsp");
	return;
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employee Dashboard</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet" />

<style>
#sidebar {
	width: 250px;
	min-height: 100vh;
	background: #198754; /* Green */
	color: white;
	padding: 20px;
	position: fixed;
	left: 0;
	top: 0;
}

#content {
	margin-left: 260px;
}

.logout-btn {
	position: absolute;
	bottom: 20px;
	left: 20px;
	right: 20px;
	font-weight: bold;
	border-radius: 30px;
}
</style>
</head>

<body class="bg-light">

	<!-- ========== SIDEBAR ========== -->
	<div id="sidebar">

		<h4 class="mb-4">Employee Panel</h4>

		<p>
			<strong>Welcome:</strong><br>
			<%=emp.getName()%></p>
		<p>
			<strong>Department:</strong><br>
			<%=emp.getDepartment()%></p>

		<div class="d-grid gap-2">
			<s:a action="employeeDashboard" class="btn btn-light">My Projects</s:a>
			<a href="viewEmployees" class="btn btn-light">View Employees</a>

		</div>

		<s:a action="logoutEmployee" class="btn btn-outline-light logout-btn">Logout</s:a>
	</div>


	<!-- ========== MAIN CONTENT ========== -->
	<div id="content" class="p-4">

		<h2 class="text-primary">Your Assigned Projects</h2>

		<div class="card mt-4 shadow p-3">

			<!-- If no projects -->
			<s:if test="projects == null || projects.size() == 0">
				<div class="alert alert-info">Kindly Check Your Assigned
					Projects From the My Projects Button</div>
			</s:if>

			<!-- If projects exist -->
			<s:if test="projects.size() > 0">

				<table class="table table-bordered table-striped mt-3">
					<thead class="table-dark">
						<tr>
							<th>Title</th>
							<th>Description</th>
							<th>Owner</th>
							<th>File</th>
						</tr>
					</thead>

					<tbody>
						<s:iterator value="projects" var="p">

							<tr>
								<td><s:property value="#p.title" /></td>

								<td style="max-width: 300px; white-space: pre-wrap;"><s:property
										value="#p.description" /></td>

								<td><s:property value="#p.owner.name" /></td>

								<td><s:if
										test="#p.filepath != null && #p.filepath.trim().length() > 0">
										<a
											href="downloadProjectFile?filepath=<s:property value='#p.filepath'/>"
											class="btn btn-sm btn-primary" target="_blank"> Download
										</a>
									</s:if></td>


							</tr>

						</s:iterator>
					</tbody>
				</table>

			</s:if>

		</div>

	</div>

</body>
</html>
