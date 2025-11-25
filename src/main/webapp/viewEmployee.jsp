<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employees List</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>

<body class="bg-light">

	<div class="container mt-5">
		<div class="card shadow p-4">
			<h3 class="mb-4 text-primary text-center">Employees List</h3>

			<div class="text-end mb-3">

				<s:if test="#session.loggedEmployee.role == 'ADMIN'">
					<a href="addEmployee.jsp" class="btn btn-success">+ Add New
						Employee</a>
				</s:if>
			</div>

			<table class="table table-bordered table-striped text-center">
				<thead class="table-dark">
					<tr>
						<th>ID</th>
						<th>Name</th>
						<th>Email</th>
						<th>Dept</th>
						<th>Salary</th>
						<th>Action</th>
					</tr>
				</thead>

				<tbody>

					<s:if test="#session.loggedEmployee.role == 'ADMIN'">
						<s:iterator value="employees">
							<tr>
								<td><s:property value="id" /></td>
								<td><s:property value="name" /></td>
								<td><s:property value="email" /></td>
								<td><s:property value="department" /></td>
								<td><s:property value="salary" /></td>
								<td><a href="editEmployee?id=<s:property value='id'/>"
									class="btn btn-primary btn-sm">Edit</a> <a
									href="deleteEmployee?id=<s:property value='id'/>"
									class="btn btn-danger btn-sm"
									onclick="return confirm('Delete this record?')">Delete</a></td>
							</tr>
						</s:iterator>
					</s:if>


					<s:else>
						<tr>
							<td><s:property value="#session.loggedEmployee.id" /></td>
							<td><s:property value="#session.loggedEmployee.name" /></td>
							<td><s:property value="#session.loggedEmployee.email" /></td>
							<td><s:property value="#session.loggedEmployee.department" /></td>
							<td><s:property value="#session.loggedEmployee.salary" /></td>
							<td><a
								href="editEmployee?id=<s:property value='#session.loggedEmployee.id'/>"
								class="btn btn-primary btn-sm">Edit</a>
								<div class="text-center mt-3">
									<a href="employee_home.jsp" class="btn btn-secondary btn-sm">Back</a>
								</div></td>
						</tr>
					</s:else>
				</tbody>

			</table>
		</div>
	</div>

</body>
</html>
