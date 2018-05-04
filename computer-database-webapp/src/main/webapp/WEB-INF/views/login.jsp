<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<tag:link directory='css' target='bootstrap.min.css'/>" rel="stylesheet" media="screen">
<link href="<tag:link directory='css' target='font-awesome.css'/>" rel="stylesheet" media="screen">
<link href="<tag:link directory='css' target='main.css'/>" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="<tag:link target='dashboard'/>"> 
				<spring:message code="nav_bar.title" />
			</a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<c:if test="${param.error != null}">
				<div class="alert alert-danger">Error: Invalid username or password.</div>
			</c:if>
			<c:if test="${param.logout != null}">
				<div class="alert alert-success">Success: Successfully logged out !</div>
			</c:if>
			<h1>Connexion</h1>
			<div class="row">
				<form:form action="login" method="POST" modelAttribute="user">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<fieldset>
						<div class="form-group">
							<label for="login">Username</label> <input type="text"
								class="form-control" id="username" name="username"
								placeholder="Username">
						</div>
						<div class="form-group">
							<label for="password">Password</label> <input type="password"
								class="form-control" id="password" name="password"
								placeholder="Password">
						</div>
					</fieldset>
					<input id="Connect" type="submit" class="btn btn-primary">
				</form:form>
			</div>
		</div>
	</section>
	<script src="<tag:link directory='js' target='jquery.min.js'/>"></script>
</body>
</html>