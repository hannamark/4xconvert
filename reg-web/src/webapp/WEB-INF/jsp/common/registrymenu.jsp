<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<li class="stdnav"><div>NCI Registry</div>
			<ul>
				<li><a href="home.action">Home</a></li>			
				<li><a href="/registry/protected/searchTrial.action" >Search Trials</a></li>
				<li><a href="/registry/protected/submitTrial.action" >Submit Trial</a></li>
				<c:choose>
				    <c:when test="${pageContext.request.remoteUser != null}">
                        <li><a href="/registry/logout.action" >Logout</a></li>
                        <li><a href="registry/protected/searchTrial.action" >My Account</a></li>
				    </c:when>
			        <c:otherwise>
                        <li><a href="/registry/protected/searchTrial.action" >Login</a></li>
                        <li><a href="registry/protected/searchTrial.action" >Register</a></li>
				    </c:otherwise>
				</c:choose>
				<li><a href="#">Help</a></li>
        	</ul>
        </li>
        

