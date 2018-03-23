<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag" %>

<li>
	<c:if test = "${currentIndex > 1}">
		<a href="?previous" aria-label="Previous">
			<span aria-hidden="true">&laquo;</span>
		</a>
	</c:if>
</li>
<c:choose>
	<c:when test = "${currentIndex < 3}" >
		<c:set var = "scale" scope = "session" value = "0"/>
	</c:when>
	<c:when test = "${currentIndex > maxIndex-3}" >
		<c:set var = "scale" scope = "session" value = "${maxIndex - 5}"/>
	</c:when>
	<c:otherwise>
		<c:set var = "scale" scope = "session" value = "${currentIndex - 3}"/>
	</c:otherwise>
</c:choose>
<c:forEach var = "i" begin = "1" end = "5">
	<c:choose>
		<c:when test = "${currentIndex == i+scale}">
			<li><a class="disable" style="color:grey;">${i+scale}</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="?index=${i+scale}">${i+scale}</a></li>
		</c:otherwise>
	</c:choose>
</c:forEach>
<li>
	<c:if test = "${currentIndex < maxIndex}">
		<a href="?next" aria-label="Next">
			<span aria-hidden="true">&raquo;</span>
		</a>
	</c:if>
</li>