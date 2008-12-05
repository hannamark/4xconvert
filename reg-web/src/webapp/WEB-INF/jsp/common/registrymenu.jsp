<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<li class="stdnav"><div>NCI CTRP</div>
			<ul>
				<li><a href="/registry/home.action">Home</a></li>
				<c:choose>
				    <c:when test="${pageContext.request.remoteUser != null}">                        		
                        		<li><a href="registerUsershowMyAccount.action" >My Account</a></li>
                        		<li><a href="/registry/logout.action" >Logout</a></li>
				    </c:when>
			        <c:otherwise>                        		
                        		<li><a href="/registry/registerUser.action" >Create Account</a></li>
                        		<li><a href="/registry/protected/searchTrial.action" >Login</a></li>
				    </c:otherwise>
				</c:choose>								
				<li><a href="/registry/protected/submitTrial.action" >Register Trial</a></li>
				<li><a href="/registry/protected/searchTrial.action" >Search Trials</a></li>
				<li><a href="#" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a></li>
        	</ul>
        </li>
        

