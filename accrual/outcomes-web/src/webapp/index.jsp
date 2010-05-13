<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
StringBuilder params = new StringBuilder();
java.util.Enumeration names = request.getParameterNames();
if (names.hasMoreElements()) {
    params.append("?");
}
while (names.hasMoreElements()) {
    String name = (String) names.nextElement();
    params.append(name);
    params.append("=");
    params.append(request.getParameter(name));
    if (names.hasMoreElements()) {
        params.append("&");
    }
}
%>
<c:choose>
    <c:when test="${(sessionScope.accrualRole == 'Outcomes')}"><% response.sendRedirect("/outcomes/outcomes/home.action"); %></c:when>
    <c:otherwise><% response.sendRedirect("/outcomes/home.action" + params.toString()); %></c:otherwise>
</c:choose>