<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
    <c:when test="${(sessionScope.viewerRole == 'Abstractor')}"><% response.sendRedirect("/viewer/ctro/home.action"); %></c:when>
    <c:when test="${(sessionScope.viewerRole == 'Submitter')}"><% response.sendRedirect("/viewer/public/home.action"); %></c:when>
    <c:otherwise><% response.sendRedirect("/viewer/home.action"); %></c:otherwise>
</c:choose>