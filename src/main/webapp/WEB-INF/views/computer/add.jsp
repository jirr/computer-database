<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
            <a class="navbar-brand" href="<tag:link target='dashboard'/>"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1>Add Computer</h1>
                    <form:form action="add" method="POST" modelAttribute="computer">
                        <fieldset>
                            <div class="form-group">
                                <label for="name">Computer name</label>
                                <input type="text" class="form-control" id="name" name="name" placeholder="Computer name" required pattern="^[\wÀ-ÿ]+[\wÀ-ÿ_\-'\+\.\* ]+$">
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="dateIntroduced" name="dateIntroduced" placeholder="Introduced date" data-validation="date" data-validation-format="yyyy-mm-dd">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="dateDiscontinued" name="dateDiscontinued" placeholder="Discontinued date" data-validation="date" data-validation-format="yyyy-mm-dd">
                            </div>
                            <div class="form-group">
                                <form:label for="companyId" path="manufactor">Company</form:label>
                                <form:select class="form-control" id="companyId" name="companyId" path="manufactor.id">
                                <form:option value="-1">--</form:option>
                                    <c:forEach var="company" items="${companyList}">
                                    	<form:option value="${company.id}">${company.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary">
                            or
                            <a href="<tag:link target='dashboard'/>" class="btn btn-default">Cancel</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
    
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>
	<script>
	  	$.validate({
	    	lang: 'en',
	    	modules: 'html5'
	  	});
	</script>
</body>
</html>