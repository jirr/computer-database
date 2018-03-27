<jsp:directive.tag pageEncoding="UTF-8" body-content="empty" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="target" required="true" %>
<%@ attribute name="directory" required="false" %>
<%@ attribute name="size" required="false" %>
<%@ attribute name="index" required="false" %>
<%@ attribute name="computerId" required="false" %>

<c:choose>
    <c:when test="${directory == 'css'}">
    	<c:url value="/static/css/"/>${target}
    </c:when>
    <c:when test="${directory == 'js'}">
    	<c:url value="/static/js/"/>${target}
    </c:when>
    <c:when test="${target == 'dashboard'}">
		<c:url value="/dashboard"/>
	</c:when>
    <c:when test="${target == 'dashboardPrev'}">
    	<c:url value="/dashboard?prev&size=${size}"/>
    </c:when>
    <c:when test="${target == 'dashboardNext'}">
    	<c:url value="/dashboard?next&size=${size}"/>
    </c:when>
    <c:when test="${target == 'dashboardFirst'}">
    	<c:url value="/dashboard?first&size=${size}"/>
    </c:when>
    <c:when test="${target == 'dashboardLast'}">
    	<c:url value="/dashboard?last&size=${size}"/>
    </c:when>
    <c:when test="${target == 'dashboardIndex'}">
    	<c:url value="/dashboard?index=${index}&size=${size}"/>
    </c:when>
    <c:when test="${target == 'size'}">
    	<c:url value="/dashboard?size=${size}"/>
    </c:when>
    <c:when test="${target == 'editComputer'}">
    	<c:url value="/editComputer?computerId="/>${computerId}
    </c:when>
    <c:when test="${target == 'addComputer'}">
    	<c:url value="/addComputer"/>
    </c:when>
</c:choose>