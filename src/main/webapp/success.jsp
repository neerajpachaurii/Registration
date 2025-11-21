<%@ taglib prefix="s" uri="/struts-tags"%>
<%
com.example.model.User user = (com.example.model.User) session.getAttribute("loggedUser");
if (user == null) {
	request.setAttribute("actionErrors", java.util.Arrays.asList("Please login first."));
	request.getRequestDispatcher("error.jsp").forward(request, response);
	return;
}
%>

<!DOCTYPE html>
<html>
<head>
<title>Welcome</title>
<style>
        body { background: #f2f4f7; }
        .form-container{
            max-width: 520px;
            margin: 40px auto;
            padding: 30px;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
        }
        h2 { text-align:center; margin-bottom:20px; font-weight:700; }
    </style>
</head>
<body>
	<div class="form-container" style="margin-top: 40px">
		<h3>
			Welcome,
			<%=user != null ? user.getName() : "User"%></h3>
		<p>
			Username:
			<%=user != null ? user.getUsername() : ""%></p>
		<p>
			Email:
			<%=user != null ? user.getEmail() : ""%></p>
		<p>
			Phone:
			<%=user != null ? user.getPhone() : ""%></p>
		<!-- <form action="logout" method="post">
			<input type="submit" value="Logout" />
		</form> -->
		<a href="index.jsp" class="btn btn-primary">LogOut</a>
	</div>
</body>
</html>
