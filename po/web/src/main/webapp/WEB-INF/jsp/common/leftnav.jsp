<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<ul class="menu">
    <li class="liheader">PO Curation Portal</li>
    <c:choose>
        <c:when test="${pageContext.request.remoteUser != null}">
            <li><a id="Help" href="javascript://noop/" onclick="openHelpWindow('');">Help</a></li>
            <li><a id="Logout" href="<c:url value="/login/logout.action"/>">Logout</a></li>
            <c:if test="${sessionScope.disclaimerAccepted}">
            <li class="lisubheader">Organization</li>
	            <li><a id="EntityInboxOrganization" href="<c:url value="/protected/curate/search/listOrgs.action"/>">Inbox</a></li>
	            <li><a id="SearchOrganization" href="<c:url value="/protected/search/organization/start.action"/>">Search</a></li>
	            <li><a id="CreateOrganization" href="<c:url value="/protected/create/organization/start.action"/>">Create</a></li>
                <li><a id="ImportCtepOrgs" href="<c:url value="/protected/import/ctep/org/start.action"/>">CTEP Import</a></li>
            <li class="lisubheader">Person</li>
	            <li><a id="EntityInboxPerson" href="<c:url value="/protected/curate/search/listPersons.action"/>">Inbox</a></li>
                <li><a id="SearchPerson" href="<c:url value="/protected/search/person/start.action"/>">Search</a></li>
                <li><a id="CreatePerson" href="<c:url value="/protected/create/person/start.action"/>">Create</a></li>
                <li><a id="ImportCtepPeople" href="<c:url value="/protected/import/ctep/person/start.action"/>">CTEP Import</a></li>
            <li class="lisubheader">Organization Family</li>
                <li><a id="ListFamily" href="<c:url value="/protected/search/family/list.action"/>">List</a></li>
                <li><a id="SearchFamily" href="<c:url value="/protected/search/family/start.action"/>">Search</a></li>
                <li><a id="CreateFamily" href="<c:url value="/protected/family/create/start.action"/>">Create</a></li>
            </c:if>	            
        </c:when>
        <c:otherwise>
            <li><a href="<c:url value="/protected/home.action"/>">Login</a></li>
        </c:otherwise>
    </c:choose>
</ul>
