<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag" %>

<c:if test = "${currentIndex > 1}">
	<li>
		<a href="?first" aria-label="First">
			<span aria-hidden="true">&laquo;</span>
		</a>
	</li>
	<li>
		<a href="?previous" aria-label="Previous">
			<span aria-hidden="true">&lsaquo;</span>
		</a>
	</li>
</c:if>
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
<c:if test = "${currentIndex < maxIndex}">
	<li>
		<a href="?next" aria-label="Next">
			<span aria-hidden="true">&rsaquo;</span>
		</a>
	</li>
	<li>
		<a href="?last" aria-label="Last">
			<span aria-hidden="true">&raquo;</span>
		</a>
	</li>
</c:if>