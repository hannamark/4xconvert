<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<li class="stdnav"><div>NCI CTRP</div>
			<ul>
                <c:choose>
                   <c:when test="${requestScope.topic == ''}">
                      <li><a href="/registry/home.action" class="selected">Home</a></li>
                   </c:when>
                   <c:otherwise>
                      <li><a href="/registry/home.action">Home</a></li>
                   </c:otherwise>
                </c:choose>
				<c:choose>
				    <c:when test="${pageContext.request.remoteUser != null}">				    
								<c:choose>
									<c:when test="${requestScope.topic == 'my_account'}">
									   <li><a href="registerUsershowMyAccount.action" class="selected">My Account</a></li> 
									</c:when>
									<c:otherwise>
									   <li><a href="registerUsershowMyAccount.action" >My Account</a></li>
									</c:otherwise>
								</c:choose>
								<c:choose>
                                    <c:when test="${requestScope.topic == 'submit_trial'}">
                                       <li><a href="/registry/protected/submitTrial.action" class="selected">Register Trial</a></li>
                                    </c:when>
                                    <c:otherwise>
                                       <li><a href="/registry/protected/submitTrial.action" >Register Trial</a></li>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${requestScope.topic == 'search_trials'}">
                                       <li><a href="/registry/protected/searchTrial.action" class="selected">Search Trials</a></li>
                                    </c:when>
                                    <c:when test="${requestScope.topic == 'search_results'}">
                                       <li><a href="/registry/protected/searchTrial.action" class="selected">Search Trials</a></li>
                                    </c:when>
                                    <c:otherwise>
                                       <li><a href="/registry/protected/searchTrial.action" >Search Trials</a></li>
                                    </c:otherwise>
                                </c:choose>                           		
                        		<li><a href="/registry/logout.action" >Log Out</a></li>
				    </c:when>
			        <c:otherwise>
			                    <c:choose>
                                    <c:when test="${requestScope.topic == 'register'}">
                                       <li><a href="/registry/registerUser.action" class="selected">Create Account</a></li>
                                    </c:when>
                                    <c:otherwise>
		                                <c:choose>
		                                  <c:when test="${requestScope.topic == 'my_account'}">
		                                     <li><a href="/registry/registerUser.action" class="selected">Create Account</a></li>
		                                  </c:when>
		                                  <c:otherwise>
		                                     <li><a href="/registry/registerUser.action" >Create Account</a></li>
		                                  </c:otherwise>
		                                </c:choose> 
                                    </c:otherwise>
                                </c:choose>                         		
                        		<li><a href="/registry/protected/submitTrial.action" >Register Trial</a></li>
                                <li><a href="/registry/protected/searchTrial.action" >Search Trials</a></li>
                                <c:choose>
                                    <c:when test="${requestScope.topic == 'login'}">
                                       <li><a href="/registry/protected/searchTrial.action" class="selected">Log In</a></li>
                                    </c:when>
                                    <c:otherwise>
                                       <li><a href="/registry/protected/searchTrial.action" >Log In</a></li>
                                    </c:otherwise>
                                </c:choose>
				    </c:otherwise>
				</c:choose>
				<li><a href="#" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a></li>
        	</ul>
        </li>
        

