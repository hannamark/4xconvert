<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
    <c:when test="${(sessionScope.accrualRole == 'Outcomes')}"><% response.sendRedirect("/outcomes/outcomes/home.action"); %></c:when>
    <c:otherwise><% response.sendRedirect("/outcomes/home.action"); %></c:otherwise>
</c:choose>