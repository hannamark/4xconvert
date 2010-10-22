<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="<%=request.getContextPath()%>/styles/subModalstyle.css" rel="stylesheet" type="text/css" media="all"/>
<link href="<%=request.getContextPath()%>/styles/subModal.css" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>

<c:url value="/protected/ajaxselectTrialselectTypeOfTrial.action" var="selectTrial"/>
<SCRIPT LANGUAGE="JavaScript">
function selectTrialType(){
	showPopWin('${selectTrial}', 950, 400, '', 'Select a Trial Submission Category')
}
</script>
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
									   <li><a href="/registry/protected/registerUsershowMyAccount.action" class="selected">My Account</a></li>
									</c:when>
									<c:otherwise>
									   <li><a href="/registry/protected/registerUsershowMyAccount.action" >My Account</a></li>
									</c:otherwise>
								</c:choose>
								<c:choose>
                                    <c:when test="${requestScope.topic == 'submit_trial'}">
                                       <li><a href="#" onclick="selectTrialType();" class="selected">Register Trial</a></li>
                                    </c:when>
                                    <c:otherwise>
                                       <li><a href="#" onclick="selectTrialType();" >Register Trial</a></li>
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
                                <c:if test="${sessionScope.regUserWebDto != null && sessionScope.regUserWebDto.affiliatedOrgType.code == 'Admin'}">
                                    <c:choose>
                                        <c:when test="${requestScope.topic == 'site_administration'}">
                                            <li><a href="/registry/protected/siteAdministrationsearch.action" class="selected">Site Administration</a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><a href="/registry/protected/siteAdministrationsearch.action" >Site Administration</a></li>
                                        </c:otherwise>
                                    </c:choose>
                                    <li>
                                        <c:choose>
                                            <c:when test="${requestScope.topic == 'display_trial_ownership'}">
                                                <a href="/registry/protected/displayTrialOwnershipsearch.action" class="selected">Display Trial Ownership</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="/registry/protected/displayTrialOwnershipsearch.action" >Display Trial Ownership</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </li>
                                    <li>
                                        <c:choose>
                                            <c:when test="${requestScope.topic == 'manage_user_trial_ownership'}">
                                                <a href="/registry/protected/manageTrialOwnershipsearch.action" class="selected">Manage Trial Ownership</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="/registry/protected/manageTrialOwnershipsearch.action" >Manage Trial Ownership</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </li>
                                 </c:if>
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
                        		<li><a href="/registry/protected/disClaimerAction.action?actionName=submitTrial.action" >Register Trial</a></li>
                                <li><a href="/registry/protected/disClaimerAction.action?actionName=searchTrial.action" >Search Trials</a></li>
                                <c:choose>
                                    <c:when test="${requestScope.topic == 'login'}">
                                       <li><a href="/registry/protected/disClaimerAction.action?actionName=searchTrial.action" class="selected">Log In</a></li>
                                    </c:when>
                                    <c:otherwise>
                                       <li><a href="/registry/protected/disClaimerAction.action?actionName=searchTrial.action" >Log In</a></li>
                                    </c:otherwise>
                                </c:choose>
				    </c:otherwise>
				</c:choose>
				<li><a href="#" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a></li>
        	</ul>
        </li>


