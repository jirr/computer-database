<jsp:directive.tag pageEncoding="UTF-8" body-content="empty" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="target" required="true" %>
<%@ attribute name="directory" required="false" %>
<%@ attribute name="size" required="false" %>
<%@ attribute name="index" required="false" %>
<%@ attribute name="search" required="false" %>
<%@ attribute name="colum_name" required="false" %>
<%@ attribute name="asc" required="false" %>
<%@ attribute name="computerId" required="false" %>

<c:choose>
    <c:when test="${directory == 'css'}">
    	<c:url value="/static/css/"/>${target}
    </c:when>
    <c:when test="${directory == 'js'}">
    	<c:url value="/static/js/"/>${target}
    </c:when>
    <c:when test="${directory == 'js'}">
    	<c:url value="/static/js/"/>${target}
    </c:when>
    <c:when test="${target == 'logout'}">
		<c:url value="/logout"/>
	</c:when>
	<c:when test="${target == 'dashboard'}">
		<c:url value="/computer/dashboard"/>
	</c:when>
    <c:when test="${target == 'dashboardIndex'}">
    	<c:url value="/computer/dashboard?index=${index}&size=${size}&search=${search}&sort=${colum_name}&asc=${asc}"/>
    </c:when>
    <c:when test="${target == 'size'}">
    	<c:url value="/computer/dashboard?size=${size}&search=${search}&sort=${colum_name}&asc=${asc}"/>
    </c:when>
    <c:when test="${target == 'sortBy'}">
    	<c:url value="/computer/dashboard?size=${size}&search=${search}&sort=${colum_name}&asc=${asc}"/>
    </c:when>
    <c:when test="${target == 'search'}">
    	<c:url value="/computer/dashboard?size=${size}&search=${search}&sort=${colum_name}&asc=${asc}"/>
    </c:when>
    <c:when test="${target == 'editComputer'}">
    	<c:url value="/computer/edit?id=${computerId}"/>
    </c:when>
    <c:when test="${target == 'addComputer'}">
    	<c:url value="/computer/add"/>
    </c:when>
</c:choose>