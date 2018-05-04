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
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="<tag:link directory='css' target='bootstrap.min.css'/>"
	rel="stylesheet" media="screen">
<link href="<tag:link directory='css' target='font-awesome.css'/>"
	rel="stylesheet" media="screen">
<link href="<tag:link directory='css' target='main.css'/>"
	rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="<tag:link target='dashboard'/>">
				<spring:message code="nav_bar.title" />
			</a>
			<form id="logout-form" action="<tag:link target='logout'/>" method="POST">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<button type="submit" style="margin-top: 8px; float: right" class="btn btn-default">Logout</button>
			</form>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<c:if test="${not empty errorMessage}">
				<div class="alert alert-danger">Error: ${errorMessage}</div>
			</c:if>
			<c:if test="${not empty successMessage}">
				<div class="alert alert-success">Success: ${successMessage}</div>
			</c:if>
			<h1 id="homeTitle">
				<c:out value="${nbComputers}" />
				<spring:message code="dashboard.title" />
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="" method="GET" class="form-inline">
						<input type="search" id="searchbox" name="search"
							class="form-control"
							placeholder="<spring:message code="dashboard.search.holder"/>"
							value='<c:out value="${search}"/>' /> <input type="submit"
							id="searchsubmit"
							value="<spring:message code="dashboard.search.label"/>"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="<tag:link target='addComputer'/>"><spring:message
							code="dashboard.add" /></a> <a class="btn btn-default"
						id="editComputer" href="#" onclick="$.fn.toggleEditMode();">
						<spring:message code="dashboard.delete" />
					</a>
				</div>
			</div>
		</div>

		<form:form id="deleteForm" action="delete" method="POST">
			<input type="hidden" id="selection" name="selection" value="" />
		</form:form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><a
							href="<tag:link target='sortBy' search='${keywords}' colum_name='cuName' asc="${sortBy == 'cuName' && asc != true}" computerId='${computer.id}'/>"
							onclick=""> <spring:message code="dashboard.row.name" />
						</a></th>
						<th><a
							href="<tag:link target='sortBy' search='${keywords}' colum_name='introduced' asc="${sortBy == 'introduced' && asc != true}" computerId='${computer.id}'/>"
							onclick=""> <spring:message code="dashboard.row.introduced" />
						</a></th>
						<!-- Table header for Discontinued Date -->
						<th><a
							href="<tag:link target='sortBy' search='${keywords}' colum_name='discontinued' asc="${sortBy == 'discontinued' && asc != true}" computerId='${computer.id}'/>"
							onclick=""> <spring:message code="dashboard.row.discontinued" />
						</a></th>
						<!-- Table header for Company -->
						<th><a
							href="<tag:link target='sortBy' search='${keywords}' colum_name='caName' asc="${sortBy == 'caName' && asc != true}" computerId='${computer.id}'/>"
							onclick=""> <spring:message code="dashboard.row.company" />
						</a></th>
					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach var="computer" items="${computer_list}">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}"></td>
							<td><a
								href="<tag:link target='editComputer' computerId='${computer.id}'/>"
								onclick=""><c:out value="${computer.name}" /></a></td>
							<td><c:out value="${computer.dateIntroduced}" /></td>
							<td><c:out value="${computer.dateDiscontinued}" /></td>
							<td><c:out value="${computer.manufactor.name}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>
	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<tag:pagination />
			</ul>
			<div class="btn-group btn-group-sm pull-right" role="group">
				<a href="<tag:link target='size' size='10' search='${keywords}'/>" aria-label="Next">
					<button type="button" class="btn btn-default">10</button>
				</a> 
				<a href="<tag:link target='size' size='50' search='${keywords}'/>" aria-label="Next">
					<button type="button" class="btn btn-default">50</button>
				</a> 
				<a href="<tag:link target='size' size='100' search='${keywords}'/>" aria-label="Next">
					<button type="button" class="btn btn-default">100</button>
				</a>
			</div>
		</div>
	</footer>
	<script src="<tag:link directory='js' target='jquery.min.js'/>"></script>
	<script src="<tag:link directory='js' target='bootstrap.min.js'/>"></script>
	<script src="<tag:link directory='js' target='dashboard.js'/>"></script>
</body>
</html>