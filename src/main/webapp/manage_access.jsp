<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<title>Manage Access</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>

<body class="bg-light">

<div class="container mt-4" style="max-width:800px;">
  <div class="card p-4 shadow">
    <h3>Manage Access: <s:property value="projectTitle"/></h3>

    <s:form action="assignProjectAccess" method="post" theme="simple">
      <s:hidden name="projectId" value="%{projectId}"/>

      <div class="border p-3" style="max-height:350px; overflow-y:auto;">
        <s:iterator value="allEmployees" var="e">
          <div class="form-check">
            <input type="checkbox" class="form-check-input"
                   name="userIds" value="<s:property value='#e.id'/>"
                   <s:if test="%{allowedIds.contains(#e.id)}">checked="checked"</s:if>
            />
            <label class="form-check-label"><s:property value="#e.name"/></label>
          </div>
        </s:iterator>
      </div>

      <button class="btn btn-primary mt-3">Save Access</button>
      <a href="viewProjects" class="btn btn-secondary mt-3">Cancel</a>

    </s:form>

  </div>
</div>

</body>
</html>
