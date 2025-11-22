<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<title>Project Saved</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>

<body class="bg-light">

<div class="container mt-5" style="max-width:800px;">
  <div class="card p-4 shadow">
    <h3>Project Saved Successfully</h3>

    <s:if test="#request.project != null">
      <p><b>Title:</b> <s:property value="%{#request.project.title}"/></p>
      <p><b>Description:</b></p>
      <p><s:property value="%{#request.project.description}" escapeHtml="false"/></p>

      <p><b>File:</b>
         <s:if test="%{#request.project.filepath != null}">
           <a href="<%=request.getContextPath()%>/<s:property value='%{#request.project.filepath}'/>" target="_blank">
             <s:property value="%{#request.project.filename}"/>
           </a>
         </s:if>
      </p>

      <a href="view_projects.jsp" class="btn btn-primary">View Projects</a>
    </s:if>
  </div>
</div>

</body>
</html>
