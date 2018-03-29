<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag" %>

<c:if test = "${currentIndex > 1}">
	<li>
		<a href="<tag:link target='dashboardFirst' search='${keywords}'/>" aria-label="First">
			<span aria-hidden="true">&laquo;</span>
		</a>
	</li>
	<li>
		<a href="<tag:link target='dashboardPrev' search='${keywords}'/>" aria-label="Previous">
			<span aria-hidden="true">&lsaquo;</span>
		</a>
	</li>
</c:if>
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
<c:choose>
	<c:when test="${maxIndex < 5}" >
		<c:set var="endOfIndexLoop" scope="session" value="${maxIndex}"/>
	</c:when>
	<c:otherwise>
		<c:set var="endOfIndexLoop" scope="session" value="5"/>
	</c:otherwise>
</c:choose>
<c:forEach var="i" begin="1" end="${endOfIndexLoop}">
	<c:choose>
		<c:when test = "${currentIndex == i+scale}">
			<li><a class="disable" style="color:grey;">${i+scale}</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="<tag:link target='dashboardIndex' index='${i+scale}' search='${keywords}'/>">${i+scale}</a></li>
		</c:otherwise>
	</c:choose>
</c:forEach>
<c:if test = "${currentIndex < maxIndex}">
	<li>
		<a href="<tag:link target='dashboardNext' search='${keywords}'/>" aria-label="Next">
			<span aria-hidden="true">&rsaquo;</span>
		</a>
	</li>
	<li>
		<a href="<tag:link target='dashboardLast' search='${keywords}'/>" aria-label="Last">
			<span aria-hidden="true">&raquo;</span>
		</a>
	</li>
</c:if>