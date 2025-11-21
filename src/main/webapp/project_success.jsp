<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Project Saved</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Bootstrap 5 CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="bg-light">

<div class="container my-4" style="max-width:900px;">

  <div class="card shadow-sm p-4">
    <h3 class="mb-3">Project Saved</h3>

    <!-- If project is present in request, show summary -->
    <s:if test="#request.project != null">
      <div class="mb-3">
        <h5><s:property value="%{#request.project.title}" /></h5>
        <p style="white-space:pre-wrap;"><s:property value="%{#request.project.description}" /></p>

        <p>
          <strong>Owner:</strong>
          <s:property value="%{#request.project.owner != null ? #request.project.owner.name : 'N/A'}" />
        </p>

        <p>
          <strong>Uploaded file:</strong>
          <s:if test="%{#request.project.filepath != null && #request.project.filepath != ''}">
            <!-- build URL relative to context path -->
            <a href="<%= request.getContextPath() %>/<s:property value="%{#request.project.filepath}" />" target="_blank">
              <s:property value="%{#request.project.filename}" />
            </a>
          </s:if>
          <s:else>
            None
          </s:else>
        </p>
      </div>

      <div class="d-flex gap-2">
        <a href="viewProjects" class="btn btn-primary">View All Projects</a>
        <a href="addProject" class="btn btn-outline-secondary">Add Another</a>
      </div>
    </s:if>

    <s:else>
      <div class="alert alert-warning">Koi project detail available nahi hai.</div>
      <a href="viewProjects" class="btn btn-primary">View Projects</a>
    </s:else>

  </div>
</div>

<!-- Modal (hidden until shown) -->
<s:if test="#request.project != null">
  <div class="modal fade" id="successModal" tabindex="-1" aria-labelledby="successModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="successModalLabel">Project Saved Successfully</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <p><strong>Title:</strong> <s:property value="%{#request.project.title}" /></p>
          <p><strong>Description:</strong></p>
          <p style="white-space:pre-wrap;"><s:property value="%{#request.project.description}" /></p>

          <p>
            <strong>File:</strong>
            <s:if test="%{#request.project.filepath != null && #request.project.filepath != ''}">
              <a id="downloadLink" href="<%= request.getContextPath() %>/<s:property value="%{#request.project.filepath}" />" target="_blank">
                <s:property value="%{#request.project.filename}" />
              </a>
            </s:if>
            <s:else>None</s:else>
          </p>
        </div>
        <div class="modal-footer">
          <a href="viewProjects" class="btn btn-primary">View Projects</a>
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
</s:if>

<!-- Bootstrap bundle (includes Popper) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>

<!-- Auto-show modal if project exists -->
<s:if test="#request.project != null">
  <script>
    document.addEventListener('DOMContentLoaded', function () {
      var modalEl = document.getElementById('successModal');
      if (modalEl) {
        var bsModal = new bootstrap.Modal(modalEl, {backdrop: 'static'});
        bsModal.show();
      }
    });
  </script>
</s:if>

</body>
</html>
