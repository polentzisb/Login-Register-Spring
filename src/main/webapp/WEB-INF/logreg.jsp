<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!-- c:out ; c:forEach etc. --> 
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!-- Formatting (dates) --> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login Register</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/main.css">
<!-- change to match your file/naming structure -->
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
<!-- change to match your file/naming structure -->
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-4 offset-1 ">

				<h2>Register</h2>
				<div>
					<form:errors path="user.*" class="text-danger" />
				</div>
				<form:form method="POST" action="/registration"
					modelAttribute="user">
					<div>

						<form:label path="userName">UserName:</form:label>
						<form:input class="form-control" type="text" path="userName" />
					</div>
					<div>
						<form:label path="email">Email:</form:label>
						<form:input class="form-control" type="email" path="email" />
					</div>
					<div>
						<form:label path="password">Password:</form:label>
						<form:password path="password" class="form-control"/>
					</div>
					<div>
						<form:label path="passwordConfirmation">Password Confirmation:</form:label>
						<form:password path="passwordConfirmation" class="form-control"/>
					</div>
					<button type="submit" class="btn btn-info mt-4">Register</button>
				</form:form>
				<p class="text-danger mt-2"><c:out value="${succesRegister}" /></p>
			</div>
			
			<div class="col-4 offset-2">
				<h2>Login</h2>
				<div>
					<form:errors path="login.*" class="text-danger" />
				</div>
				<form:form method="POST" action="/login" modelAttribute="login">
					<div>
						<form:label path="email">Email:</form:label>
						<form:input type="email" path="email" class="form-control"/>
					</div>
					<div>
						<form:label path="password">Password:</form:label>
						<form:password path="password" class="form-control"/>
					</div>
					 <button type="submit" class="btn btn-info mt-4">Login</button>
				</form:form>
			</div>
		</div>
	</div>

</body>
</html>