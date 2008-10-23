<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<ul class="menu">
    <li class="liheader">PO Curating System</li>
    <c:choose>
        <c:when test="${pageContext.request.remoteUser != null}">
            <li><a href="<c:url value="/login/logout.action"/>">Logout</a></li>
            <li class="lisubheader">Entity Search</li>
	            <li><a id="SearchOrganization" href="<c:url value="/protected/search/organization/start.action"/>">Organization</a></li>
            <li class="lisubheader">Entity Inboxes</li>
	            <li><a id="EntityInboxOrganization" href="<c:url value="/protected/curate/search/listOrgs.action"/>">Organization</a></li>
	            <li><a id="EntityInboxPerson" href="<c:url value="/protected/curate/search/listPersons.action"/>">Person</a></li>
            <li class="lisubheader">Role Inboxes</li>
        </c:when>
        <c:otherwise>
            <li><a href="<c:url value="/protected/home.action"/>">Login</a></li>
        </c:otherwise>
    </c:choose>
    <li><a href="helpTK.html">Help</a></li>
</ul>
