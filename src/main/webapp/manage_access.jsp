<%@ taglib prefix="s" uri="/struts-tags"%>
<%
com.example.model.Employee admin = (com.example.model.Employee) session.getAttribute("loggedEmployee");
if (admin == null) {
	response.sendRedirect("loginEmployee.jsp");
	return;
}
%>

<!doctype html>
<html>
<head>
<title>Manage Access - <s:property value="projectTitle" /></title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet" />
</head>
<body>
	<div class="container mt-4">
		<h3>
			Manage Access for:
			<s:property value="projectTitle" />
		</h3>

		<form action="assignProjectAccess" method="post">
			<input type="hidden" name="projectId"
				value="<s:property value='projectId'/>" />

			<table class="table">
				<thead>
					<tr>
						<th>Assign</th>
						<th>Name</th>
						<th>Dept</th>
						<th>Currently Assigned?</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="allEmployees" var="emp">
						<tr>
							<td><input type="checkbox" name="userIds"
								value="<s:property value='#emp.id'/>"
								<s:if test="%{#allowedIds.contains(#emp.id)}"> checked="checked" </s:if> />
							</td>
							<td><s:property value="#emp.name" /></td>
							<td><s:property value="#emp.department" /></td>
							<td><s:if test="%{#allowedIds.contains(#emp.id)}">
									<span class="badge bg-success">Assigned</span>
								</s:if></td>
							<td><s:if test="%{#allowedIds.contains(#emp.id)}">
									<!-- remove link; will call execute with removeEmployeeId param -->
									<a
										href="assignProjectAccess?projectId=<s:property value='projectId'/>&removeEmployeeId=<s:property value='#emp.id'/>"
										class="btn btn-sm btn-danger">Remove</a>
								</s:if></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>

			<button type="submit" class="btn btn-primary">Save
				Assignments</button>
			<a href="viewProjects" class="btn btn-secondary">Back</a>
		</form>
	</div>
</body>
</html>
