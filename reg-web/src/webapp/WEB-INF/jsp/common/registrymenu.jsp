<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>

<c:url value="/protected/ajaxselectTrialselectTypeOfTrial.action" var="selectTrial"/>
<script>
function selectTrialType(){
    showPopWin('${selectTrial}', 950, 400, '', 'Select a Trial Submission Category')
}
</script>
        <li class="stdnav"><div>NCI CTRP</div>
            <ul>
                <c:choose>
                   <c:when test="${requestScope.topic == ''}">
                      <li><a id="homeMenuOption" href="/registry/home.action" class="selected">Home</a></li>
                   </c:when>
                   <c:otherwise>
                      <li><a id="homeMenuOption" href="/registry/home.action">Home</a></li>
                   </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${pageContext.request.remoteUser != null}">
                                <c:choose>
                                    <c:when test="${requestScope.topic == 'myaccount'}">
                                       <li><a id="myAccountMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/protected/registerUsershowMyAccount.action');" class="selected">My Account</a></li>
                                    </c:when>
                                    <c:otherwise>
                                       <li><a id="myAccountMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/protected/registerUsershowMyAccount.action');" >My Account</a></li>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${requestScope.topic == 'submittrial'}">
                                       <li><a id="registerTrialMenuOption" href="javascript:void(0)" onclick="selectTrialType();" class="selected">Register Trial</a></li>
                                    </c:when>
                                    <c:otherwise>
                                       <li><a id="registerTrialMenuOption" href="javascript:void(0)" onclick="selectTrialType();" >Register Trial</a></li>
                                    </c:otherwise>
                                </c:choose>
                                <li><a href="javascript:void(0)" class="fakelink">Search</a></li>
					            <li class="stdsub">
					                <ul>
	                                    <c:choose>                                
		                                    <c:when test="${requestScope.topic == 'searchtrials'}">
		                                       <li><a id="searchTrialsMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/protected/searchTrial.action');" class="selected">Trials</a></li>
		                                    </c:when>
		                                    <c:when test="${requestScope.topic == 'searchresults'}">
		                                       <li><a id="searchTrialsMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/protected/searchTrial.action');" class="selected">Trials</a></li>
		                                    </c:when>
		                                    <c:otherwise>
		                                       <li><a id="searchTrialsMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/protected/searchTrial.action');" >Trials</a></li>
		                                    </c:otherwise>
	                                    </c:choose>
                                        <li><a id="personSearchMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/protected/personsSearchexecute.action');" class="${requestScope.topic == 'searchperson'?'selected':''}">Persons</a></li>
       					                <li><a id="organizationSearchMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/protected/organizationsSearchexecute.action');" class="${requestScope.topic == 'searchorganization'?'selected':''}">Organizations</a></li>
						            </ul>            
					            </li>                                
                                
                                <c:if test="${sessionScope.regUserWebDto != null && sessionScope.regUserWebDto.affiliatedOrgType.code == 'Admin'}">
                                    <c:choose>
                                        <c:when test="${requestScope.topic == 'siteadmin'}">
                                            <li><a id="siteAdministrationMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/protected/siteAdministrationsearch.action');" class="selected">Site Administration</a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><a id="siteAdministrationMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/protected/siteAdministrationsearch.action');" >Site Administration</a></li>
                                        </c:otherwise>
                                    </c:choose>
                                    <li>
                                        <c:choose>
                                            <c:when test="${requestScope.topic == 'displayownership'}">
                                                <a id="showTrialOwnershipMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/protected/displayTrialOwnershipsearch.action');" class="selected">Display Trial Ownership</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a id="showTrialOwnershipMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/protected/displayTrialOwnershipsearch.action');" >Display Trial Ownership</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </li>
                                    <li>
                                        <c:choose>
                                            <c:when test="${requestScope.topic == 'manageownership'}">
                                                <a id="manageTrialOwnershipMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/protected/manageTrialOwnershipsearch.action');" class="selected">Manage Trial Ownership</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a id="manageTrialOwnershipMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/protected/manageTrialOwnershipsearch.action');" >Manage Trial Ownership</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </li>
                                    <li>
                                        <c:choose>
                                            <c:when test="${requestScope.topic == 'managesiteownership'}">
                                                <a id="manageSiteOwnershipMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/protected/manageSiteOwnershipsearch.action');" class="selected">Manage Site Record Ownership</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a id="manageSiteOwnershipMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/protected/manageSiteOwnershipsearch.action');" >Manage Site Record Ownership</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </li>
                                    <li><a href="javascript:void(0)" class="fakelink">Manage Accrual Access</a></li>
                                    <li class="stdsub">
                                    <ul>                               
                                        <li><a id="manageAccrualAccessMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/protected/manageAccrualAccess.action');"  class="${requestScope.topic == 'accrualaccess'?'selected':''}">Assign &amp; Un-assign Access</a></li>
                                        <li><a id="viewAccrualAccessAssignmentHistoryMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/protected/manageAccrualAccessassignmentHistory.action');"  class="${requestScope.topic == 'assignmentHistory'?'selected':''}">Assignment History</a></li>
                                        <li><a id="viewAccrualAccessAssignmentByTrialMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/protected/manageAccrualAccessassignmentByTrial.action');"  class="${requestScope.topic == 'assignmentByTrial'?'selected':''}">Assignment By Trial</a></li>
                                    </ul>            
                                    </li>                                      
                                 </c:if>
                                <li><a id="logoutMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/logout.action');" >Log Out</a></li>
                    </c:when>
                    <c:otherwise>
                                <c:choose>
                                    <c:when test="${requestScope.topic == 'register'}">
                                       <li><a id="createAccountMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/registerUser.action');" class="selected">Create Account</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                          <c:when test="${requestScope.topic == 'myaccount'}">
                                             <li><a id="createAccountMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/registerUser.action');" class="selected">Create Account</a></li>
                                          </c:when>
                                          <c:otherwise>
                                             <li><a id="createAccountMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/registerUser.action');" >Create Account</a></li>
                                          </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                                <li><a id="registerTrialMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/protected/disClaimerAction.action?actionName=submitTrial.action');" >Register Trial</a></li>
                                <li><a id="searchTrialsMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/protected/disClaimerAction.action?actionName=searchTrial.action');" >Search Trials</a></li>
                                <c:choose>
                                    <c:when test="${requestScope.topic == 'login'}">
                                       <li><a id="loginMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/protected/disClaimerAction.action?actionName=searchTrial.action');" class="selected">Log In</a></li>
                                    </c:when>
                                    <c:otherwise>
                                       <li><a id="loginMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('/registry/protected/disClaimerAction.action?actionName=searchTrial.action');" >Log In</a></li>
                                    </c:otherwise>
                                </c:choose>
                    </c:otherwise>
                </c:choose>
                <li><a id="helpMenuOption" href="javascript:void(0)" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a></li>
            </ul>
        </li>
