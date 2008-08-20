<%@ page import="gov.nih.nci.pa.util.Constants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="cactusheader">
   <div id="cactuslogo"><a href="./"><img src="<%=request.getContextPath()%>images/logo_cactus.gif" width="417" height="36" alt="Protocol Abstraction" /></a></div>
    <!--User Details-->
    <c:choose>
        <c:when test="${sessionScope.loggedUserName  != null}">
        <div id="userarea">
                      User:  | <c:out value="${sessionScope.loggedUserName }"/>
                    <a href='<c:url value="login.action"/>'>Logout</a>
                 </div>
    </c:when>
    <c:otherwise>
    <div id="userarea"> <a href='<c:url value="login.action"/>'>Login</a></div>
    </c:otherwise>
    </c:choose>
</div>
