<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="header">
   <div id="logo"><a href="./"><img src="<%=request.getContextPath()%>/images//logo_ctrp_reg.gif" alt="NCI CTRP Registration Site" height="41" width="340"></a></div>
    <!--User Details-->
  	<c:choose>
        <c:when test="${pageContext.request.remoteUser != null}">
        <div id="userarea">Welcome,  ${CsmHelper.firstName} |  <a href='<c:url value="/logout.action"/>' class="btn_logout">Log Out</a></div>
        </c:when>
        <c:otherwise>
        <div id="userarea"><a href='<c:url value="/public/welcome.action"/>' class="btn_login">Log In</a></div>
        </c:otherwise>
    </c:choose>
    <div class="clear"></div>
      <jsp:include page="/WEB-INF/jsp/common/topMenu.jsp"/>     
    <div class="clear"></div>
</div>
