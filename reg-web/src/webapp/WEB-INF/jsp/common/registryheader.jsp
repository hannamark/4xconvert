<%@ page import="gov.nih.nci.registry.util.Constants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="header">
   <div id="logo"><a href="#"><img src="<%=request.getContextPath()%>/images/logo_ctrp_reg.gif" width="340" height="41" alt="NCI CTRP Registration Site" /></a></div>
    <!--User Details-->
  	<c:choose>
        <c:when test="${pageContext.request.remoteUser != null}">
        <div id="userarea">Welcome, <a href='<c:url value="registerUsershowMyAccount.action"/>'> <%=request.getRemoteUser()%> </a>  |  <a href='<c:url value="/logout.action"/>' class="btn_logout">Log Out</a></div>
        </c:when>
        <c:otherwise>
        <div id="userarea"><a href='<c:url value="/protected/disClaimerAction.action?actionName=searchTrial.action"/>' class="btn_login">Log In</a></div>
        </c:otherwise>
    </c:choose>
</div>
