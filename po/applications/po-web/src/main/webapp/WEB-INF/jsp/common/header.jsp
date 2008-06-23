            <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
			<div id="nciheader">		
				<div id="ncilogo"><a href="http://www.cancer.gov"><img src="<%=request.getContextPath()%>/images/logotype.gif" width="283" height="37" alt="Logo: National Cancer Institute" /></a></div>	
				<div id="nihtag"><a href="http://www.cancer.gov"><img src="<%=request.getContextPath()%>/images/tagline.gif" width="295" height="37" alt="Logo: U.S. National Institutes of Health | www.cancer.gov" /></a></div>
			</div>
			<div id="curateheader">
				<div id="curatelogo"><a href='<c:url value="/protected/home.action"/>'><img src="<%=request.getContextPath()%>/images/logo_po_curate.gif" width="384" height="43" alt="Logo: Person|Organization Curate System" /></a></div>
				<c:choose>
                <c:when test="${pageContext.request.remoteUser != null}">
                <div id="userarea">Welcome, <%=request.getRemoteUser()%>  |  <a href='<c:url value="/login/logout.action"/>'>Logout</a></div>
                </c:when>
                <c:otherwise>
                <div id="userarea"><a href='<c:url value="/protected/home.action"/>'>Login</a></div>
                </c:otherwise>
                </c:choose>
			</div>
