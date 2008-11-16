<%@ page import="gov.nih.nci.registry.util.Constants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="header">
   <div id="logo"><a href="./"><img src="<%=request.getContextPath()%>/images/logo_ctrp.gif" width="414" height="41" alt="NCI Clinical Trials Reporting Portal" /></a></div>
    <!--User Details-->
  	<c:choose>
        <c:when test="${pageContext.request.remoteUser != null}">
        <div id="userarea">Welcome, <a href='<c:url value="registerUsershowMyAccount.action"/>'> <%=request.getRemoteUser()%> </a>  |  <a href='<c:url value="/logout.action"/>'>Logout</a></div>
        </c:when>
        <c:otherwise>
        <div id="userarea"><a href='<c:url value="/protected/home.action"/>'>Login</a></div>
        </c:otherwise>
    </c:choose>
</div>
