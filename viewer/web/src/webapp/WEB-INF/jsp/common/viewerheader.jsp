<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="header">
   <div id="logo"><a href="./"><img src="<%=request.getContextPath()%>/images/logo_ctrp.gif" width="442" height="41" alt="CTRP Viewer" /></a></div>
    <!--User Details-->
  	<c:choose>
        <c:when test="${pageContext.request.remoteUser != null}">
        <div id="userarea">Welcome, <%=request.getRemoteUser()%> |  <a href='<c:url value="/logout.action"/>' class="btn_logout">Log Out</a></div>
        </c:when>
        <c:otherwise>
        <div id="userarea"><a href='<c:url value="/public/welcome.action"/>' class="btn_login">Log In</a></div>
        </c:otherwise>
    </c:choose>
</div>
