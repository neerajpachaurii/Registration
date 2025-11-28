<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Add Project</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet" />
</head>

<body class="bg-light">

	<div class="container mt-4" style="max-width: 700px;">
		<div class="card p-4 shadow">
			<h4>Add Project</h4>

			<s:if test="hasActionErrors()">
				<div class="alert alert-danger">
					<s:actionerror />
				</div>
			</s:if>

			<s:form action="addProject" method="post"
				enctype="multipart/form-data" theme="simple">

				<div class="mb-3">
					<label class="form-label">Title</label>
					<s:textfield name="project.title" cssClass="form-control" />
				</div>

				<div class="mb-3">
					<label class="form-label">Description</label>
					<s:textarea name="project.description" cssClass="form-control"
						rows="4" />
				</div>

				<div class="mb-3">
					<label class="form-label">Upload File</label> <input type="file"
						name="upload" class="form-control" />
				</div>

				<button class="btn btn-primary w-100">Save Project</button>
				<div class="text-center mt-3">
					<a href="adminDashboard" class="btn btn-secondary w-100">Back</a>
				</div>
			</s:form>
		</div>
	</div>

</body>
</html>
