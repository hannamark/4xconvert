<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
    <c:when test="${(sessionScope.viewerRole == 'Submitter')}"><% response.sendRedirect("/accrual/public/home.action"); %></c:when>
    <c:otherwise><% response.sendRedirect("/accrual/home.action"); %></c:otherwise>
</c:choose>