<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag" %>
<!DOCTYPE html>
<html>
<head>
	<title>Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="utf-8">
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
            <h1 id="homeTitle">
                <c:out value ="${nbComputers}"/> Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="" method="GET" class="form-inline">
                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" value='<c:out value="${search}"/>' />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="<tag:link target='addComputer'/>">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Delete</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
        </form>
        
        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                        </th>
                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                <c:forEach var="computer" items="${computer_list}">
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="${computer.id}">
                        </td>
                        <td>
                            <a href="<tag:link target='editComputer' computerId='${computer.id}'/>" onclick=""><c:out value="${computer.name}" /></a>
                        </td>
                        <td><c:out value="${computer.dateIntroduced}" /></td>
                        <td><c:out value="${computer.dateDiscontinued}" /></td>
                        <td><c:out value="${computer.manufactorName}" /></td>
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
	        <div class="btn-group btn-group-sm pull-right" role="group" >
	            <a href="<tag:link target='size' size='10'/>" aria-label="Next">
	            	<button type="button" class="btn btn-default">10</button></a>
	            <a href="<tag:link target='size' size='50'/>" aria-label="Next">
	            	<button type="button" class="btn btn-default">50</button></a>
	            <a href="<tag:link target='size' size='100'/>" aria-label="Next">
	            	<button type="button" class="btn btn-default">100</button></a>
	        </div>
        </div>
    </footer>
	<script src="<tag:link directory='js' target='jquery.min.js'/>"></script>
	<script src="<tag:link directory='js' target='bootstrap.min.js'/>"></script>
	<script src="<tag:link directory='js' target='dashboard.js'/>"></script>
</body>
</html>