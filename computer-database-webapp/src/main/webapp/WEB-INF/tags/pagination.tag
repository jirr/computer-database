<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag" %>

<!-- First and Previous buttons -->
<c:if test = "${currentIndex > 1}">
	<li>
		<a href="<tag:link target='dashboardIndex' index='1' search='${keywords}' colum_name='${sortBy}' asc='${asc}'/>" aria-label="First">
			<span aria-hidden="true">&laquo;</span>
		</a>
	</li>
	<li>
		<a href="<tag:link target='dashboardIndex' index='${currentIndex+1}' search='${keywords}' colum_name='${sortBy}' asc='${asc}'/>" aria-label="Previous">
			<span aria-hidden="true">&lsaquo;</span>
		</a>
	</li>
</c:if>
<!-- Scope choice -->
<c:choose>
	<c:when test="${currentIndex < 3}" >
		<c:set var="scale" scope="session" value="0"/>
	</c:when>
	<c:when test="${currentIndex > maxIndex-3}" >
		<c:set var="scale" scope="session" value="${maxIndex - 5}"/>
	</c:when>
	<c:otherwise>
		<c:set var="scale" scope="session" value="${currentIndex - 3}"/>
	</c:otherwise>
</c:choose>
<!-- Number of IndexButtons -->
<c:choose>
	<c:when test="${maxIndex < 5}" >
		<c:set var="endOfIndexLoop" scope="session" value="${maxIndex}"/>
	</c:when>
	<c:otherwise>
		<c:set var="endOfIndexLoop" scope="session" value="5"/>
	</c:otherwise>
</c:choose>
<!-- Buttons -->
<c:forEach var="i" begin="1" end="${endOfIndexLoop}">
	<c:choose>
		<c:when test = "${currentIndex == i+scale}">
			<li><a class="disable" style="color:grey;">${i+scale}</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="<tag:link target='dashboardIndex' index='${i+scale}' search='${keywords}' colum_name='${sortBy}' asc='${asc}'/>">${i+scale}</a></li>
		</c:otherwise>
	</c:choose>
</c:forEach>
<!-- Last and Next buttons -->
<c:if test = "${currentIndex < maxIndex}">
	<li>
		<a href="<tag:link target='dashboardIndex' index='${currentIndex+1}' search='${keywords}' colum_name='${sortBy}' asc='${asc}'/>" aria-label="Next">
			<span aria-hidden="true">&rsaquo;</span>
		</a>
	</li>
	<li>
		<a href="<tag:link target='dashboardIndex' index='${maxIndex}' search='${keywords}' colum_name='${sortBy}' asc='${asc}'/>" aria-label="Last">
			<span aria-hidden="true">&raquo;</span>
		</a>
	</li>
</c:if>