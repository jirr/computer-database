<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            <a class="navbar-brand" href="<tag:link target='dashboard'/>"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="alert alert-danger">
                Error 500: An error has occured!
                <br/>
                <!-- stacktrace -->
            </div>
        </div>
    </section>

	<script src="<tag:link directory='js' target='jquery.min.js'/>"></script>
	<script src="<tag:link directory='js' target='bootstrap.min.js'/>"></script>
	<script src="<tag:link directory='js' target='dashboard.js'/>"></script>

</body>
</html>