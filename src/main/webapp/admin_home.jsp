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
<title>Admin Dashboard</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet" />

<style>
#sidebar {
	width: 250px;
	min-height: 100vh;
	background: #0d6efd;
	color: white;
	padding: 20px;
	position: fixed;
	top: 0;
	left: 0;
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
		<h4 class="mb-4">Admin Panel</h4>

		<p>
			<strong>Welcome:</strong><br><%=emp.getName()%></p>
		<p>
			<strong>Role:</strong><br> ADMIN
		</p>

		<div class="d-grid gap-2">
			<a href="adminDashboard" class="btn btn-light">Dashboard</a> <a
				href="add_Project.jsp" class="btn btn-light">Add Project</a> <a
				href="viewProjects" class="btn btn-light">View Projects</a> <a
				href="viewEmployees" class="btn btn-light">Employees</a>
		</div>

		<a href="index.jsp" class="btn btn-outline-light logout-btn">Logout</a>
	</div>

	<!-- ========== MAIN CONTENT ========== -->
	<div id="content" class="p-4">

		<div class="d-flex justify-content-between align-items-center mb-3">


			<h2 class="text-primary m-0">Admin Dashboard</h2>


			<div class="d-flex gap-2">


				<a href="add_Project.jsp" class="btn btn-success"> + Add Project
				</a>

				<!-- Download Employees (Visible only if admin & data exists) -->
				<s:if
					test="%{#session.loggedEmployee != null 
                && #session.loggedEmployee.role == 'ADMIN' 
                && employees != null && employees.size() > 0}">
					<a href="exportEmployees" class="btn btn-success btn-sm"> <i
						class="bi bi-file-earmark-excel"></i> Download Data
					</a>
				</s:if>

			</div>
		</div>


		<div class="row mt-4">
			<div class="col-md-4">
				<div class="card p-3 shadow">
					<h5>Total Employees</h5>
					<h2>
						<s:property value="employees.size()" />
					</h2>
				</div>
			</div>

			<div class="col-md-4">
				<div class="card p-3 shadow">
					<h5>Total Projects</h5>
					<h2>
						<s:property value="projects.size()" />
					</h2>
				</div>
			</div>
		</div>


		<!-- SEARCH PANEL -->
		<div class="card p-3 mt-4 shadow">
			<form action="adminDashboard" method="get" class="row g-3">

				<div class="col-md-3">
					<label>Project Title</label>
					<s:textfield name="searchTitle" cssClass="form-control" />
				</div>

				<div class="col-md-3">
					<label>Project Owner</label>
					<s:textfield name="searchOwner" cssClass="form-control" />
				</div>

				<div class="col-md-3">
					<label>Employee Department</label>
					<s:textfield name="searchDept" cssClass="form-control" />
				</div>

				<div class="col-md-3">
					<label>Employee Email</label>
					<s:textfield name="searchEmail" cssClass="form-control" />
				</div>

				<div class="col-12">
					<button class="btn btn-primary">Search</button>
					<a href="adminDashboard" class="btn btn-secondary">Reset</a>
				</div>

			</form>
		</div>


		<!-- ========== PROJECT TABLE ========== -->
		<div class="card p-3 mt-4 shadow">
			<h4>Projects</h4>

			<table class="table table-bordered table-striped mt-3">
				<thead class="table-dark">
					<tr>
						<th>Title</th>
						<th>Description</th>
						<th>Owner</th>
						<!-- <th>File</th> -->
						<th>Assigned To</th>
						<th>Assign</th>
					</tr>
				</thead>

				<tbody>
					<s:iterator value="projects" var="p">
						<tr>
							<td><s:property value="#p.title" /></td>

							<td style="max-width: 300px; white-space: pre-wrap;"><s:property
									value="#p.description" /></td>

							<td><s:property value="#p.owner.name" /></td>

							<%-- <td><s:if test="#p.filepath != null">
									<a
										href="<%=request.getContextPath()%>/<s:property value='#p.filepath'/>"
										target="_blank">Download</a>
								</s:if></td> --%>

							<!-- SHOW ASSIGNED USERS -->
							<td><s:iterator value="#p.allowedUsers">
									<span class="badge bg-primary me-1"> <s:property
											value="name" />
									</span>
								</s:iterator></td>


							<td style="min-width: 200px;">
								<form action="assignProjectAccess" method="post" class="d-flex">
									<input type="hidden" name="projectId"
										value="<s:property value='#p.id'/>" /> <select name="userIds"
										class="form-select form-select-sm me-2">
										<s:iterator value="employees" var="e">
											<option value="<s:property value='#e.id'/>">
												<s:property value="#e.name" /> (
												<s:property value="#e.department" />)
											</option>
										</s:iterator>
									</select>

									<button class="btn btn-sm btn-success">Assign</button>
								</form>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>

			<!-- PAGINATION -->
			<div class="mt-3">
				<s:if test="totalPages > 1">

					<a class="btn btn-sm btn-outline-primary"
						href="adminDashboard?page=1">First</a>

					<a class="btn btn-sm btn-outline-primary"
						href="adminDashboard?page=<s:property value='page-1'/>"> Prev
					</a>

					<span class="mx-2"> Page <s:property value="page" /> / <s:property
							value="totalPages" />
					</span>

					<a class="btn btn-sm btn-outline-primary"
						href="adminDashboard?page=<s:property value='page+1'/>"> Next
					</a>

					<a class="btn btn-sm btn-outline-primary"
						href="adminDashboard?page=<s:property value='totalPages'/>">
						Last </a>

				</s:if>
			</div>

			<!-- ========== EMPLOYEE TABLE ========== -->
			<div class="card p-3 mt-4 shadow">
				<h4>Employees</h4>

				<table class="table table-bordered table-striped mt-3">
					<thead class="table-dark">
						<tr>
							<th>Name</th>
							<th>Department</th>
							<th>Email</th>
							<th>Salary</th>
							<th>Status</th>
							<th>Action</th>
						</tr>
					</thead>

					<tbody>
						<s:iterator value="employees" var="e">
							<tr>
								<td><s:property value="#e.name" /></td>
								<td><s:property value="#e.department" /></td>
								<td><s:property value="#e.email" /></td>
								<td><s:property value="#e.salary" /></td>

								<td><s:if test="#e.status == 'ACTIVE'">
										<span class="badge bg-success"> <s:property
												value="#e.status" />
										</span>
									</s:if> <s:else>
										<span class="badge bg-danger"> <s:property
												value="#e.status" />
										</span>
									</s:else></td>

								<td><s:if test="#e.status == 'ACTIVE'">
										<form action="updateEmployeeStatus" method="post"
											style="display: inline;">
											<input type="hidden" name="id"
												value="<s:property value='#e.id'/>" /> <input type="hidden"
												name="status" value="INACTIVE" />
											<button type="submit" class="btn btn-sm btn-danger"
												onclick="return confirm('Are you sure you want to deactivate this user?');">
												Inactivate</button>
										</form>
									</s:if> <s:else>
										<form action="updateEmployeeStatus" method="post"
											style="display: inline;">
											<input type="hidden" name="id"
												value="<s:property value='#e.id'/>" /> <input type="hidden"
												name="status" value="ACTIVE" />
											<button type="submit" class="btn btn-sm btn-success"
												onclick="return confirm('Are you sure you want to activate this user?');">
												Activate</button>
										</form>
									</s:else></td>
							</tr>
						</s:iterator>

					</tbody>
				</table>

			</div>

		</div>
</body>
</html>
