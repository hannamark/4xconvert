<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="gov.nih.nci.pa.util.CsmHelper"%>
<div id="header">
   <div id="logo"><a href="./"><img src="<%=request.getContextPath()%>/images/logo_ctrp_accrual.gif" width="293" height="41" alt="Accrual" /></a></div>
    <!--User Details-->
  	<c:choose>
        <c:when test="${pageContext.request.remoteUser != null}">
        <div id="userarea">Welcome, ${CsmHelper.firstName} ${CsmHelper.lastName} |  <a href='<c:url value="/logout.action"/>' class="btn_logout">Log Out</a></div>
        </c:when>
        <c:otherwise>
        <div id="userarea"><a href='<c:url value="/protected/welcome.action"/>' class="btn_login">Log In</a></div>
        </c:otherwise>
    </c:choose>
</div>
