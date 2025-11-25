<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title>Projects</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet" />
</head>

<body class="bg-light">
	<div class="container mt-4">
		<div class="card p-4 shadow">

			<h4>Projects</h4>

			<table class="table table-striped">
				<thead>
					<tr>
						<th>Title</th>
						<th>Description</th>
						<th>Owner</th>
						<!--  <th>File</th> -->
						<th>Actions</th>
					</tr>
				</thead>

				<tbody>
					<s:iterator value="projects" var="p">

						<tr>
							<td><s:property value="#p.title" /></td>

							<td style="max-width: 300px; white-space: pre-wrap;"><s:property
									value="#p.description" /></td>

							<td><s:property value="#p.owner.name" /></td>

							<td>
								<!-- ADMIN manage access --> <s:if
									test="#session.loggedEmployee.role == 'ADMIN'">
									<a
										href="assignProjectAccessPage?projectId=<s:property value='#p.id'/>"
										class="btn btn-sm btn-outline-secondary">Manage Access</a>

								</s:if> <!-- Owner or Admin can delete form here--> <s:if
									test="#session.loggedEmployee.role == 'ADMIN' || #session.loggedEmployee.id == #p.owner.id">
									<a href="deleteProject?id=<s:property value='#p.id'/>"
										class="btn btn-sm btn-outline-danger"
										onclick="return confirm('Delete this project?');"> Delete
									</a>
								</s:if>

							</td>
						</tr>

					</s:iterator>
				</tbody>

			</table>

			<a href="add_Project.jsp" class="btn btn-success">Add Project</a>
			<div class="text-center mt-3">
				<a href="admin_home.jsp" class="btn btn-secondary w-100">Back</a>
			</div>

		</div>
	</div>
</body>
</html>
