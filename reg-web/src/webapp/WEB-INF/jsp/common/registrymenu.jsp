<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<link href="${pageContext.request.contextPath}/styles/subModalstyle.css" rel="stylesheet" type="text/css" media="all"/>
<link href="${pageContext.request.contextPath}/styles/subModal.css" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>

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
                                       <li><a id="myAccountMenuOption" href="#" onclick="submitXsrfForm('/registry/protected/registerUsershowMyAccount.action');" class="selected">My Account</a></li>
                                    </c:when>
                                    <c:otherwise>
                                       <li><a id="myAccountMenuOption" href="#" onclick="submitXsrfForm('/registry/protected/registerUsershowMyAccount.action');" >My Account</a></li>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${requestScope.topic == 'submittrial'}">
                                       <li><a id="registerTrialMenuOption" href="#" onclick="selectTrialType();" class="selected">Register Trial</a></li>
                                    </c:when>
                                    <c:otherwise>
                                       <li><a id="registerTrialMenuOption" href="#" onclick="selectTrialType();" >Register Trial</a></li>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${requestScope.topic == 'searchtrials'}">
                                       <li><a id="searchTrialsMenuOption" href="#" onclick="submitXsrfForm('/registry/protected/searchTrial.action');" class="selected">Search Trials</a></li>
                                    </c:when>
                                    <c:when test="${requestScope.topic == 'searchresults'}">
                                       <li><a id="searchTrialsMenuOption" href="#" onclick="submitXsrfForm('/registry/protected/searchTrial.action');" class="selected">Search Trials</a></li>
                                    </c:when>
                                    <c:otherwise>
                                       <li><a id="searchTrialsMenuOption" href="#" onclick="submitXsrfForm('/registry/protected/searchTrial.action');" >Search Trials</a></li>
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${sessionScope.regUserWebDto != null && sessionScope.regUserWebDto.affiliatedOrgType.code == 'Admin'}">
                                    <c:choose>
                                        <c:when test="${requestScope.topic == 'siteadmin'}">
                                            <li><a id="siteAdministrationMenuOption" href="#" onclick="submitXsrfForm('/registry/protected/siteAdministrationsearch.action');" class="selected">Site Administration</a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><a id="siteAdministrationMenuOption" href="#" onclick="submitXsrfForm('/registry/protected/siteAdministrationsearch.action');" >Site Administration</a></li>
                                        </c:otherwise>
                                    </c:choose>
                                    <li>
                                        <c:choose>
                                            <c:when test="${requestScope.topic == 'displayownership'}">
                                                <a id="showTrialOwnershipMenuOption" href="#" onclick="submitXsrfForm('/registry/protected/displayTrialOwnershipsearch.action');" class="selected">Display Trial Ownership</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a id="showTrialOwnershipMenuOption" href="#" onclick="submitXsrfForm('/registry/protected/displayTrialOwnershipsearch.action');" >Display Trial Ownership</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </li>
                                    <li>
                                        <c:choose>
                                            <c:when test="${requestScope.topic == 'manageownership'}">
                                                <a id="manageTrialOwnershipMenuOption" href="#" onclick="submitXsrfForm('/registry/protected/manageTrialOwnershipsearch.action');" class="selected">Manage Trial Ownership</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a id="manageTrialOwnershipMenuOption" href="#" onclick="submitXsrfForm('/registry/protected/manageTrialOwnershipsearch.action');" >Manage Trial Ownership</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </li>
                                    <li>
                                        <c:choose>
                                            <c:when test="${requestScope.topic == 'managesiteownership'}">
                                                <a id="manageSiteOwnershipMenuOption" href="#" onclick="submitXsrfForm('/registry/protected/manageSiteOwnershipsearch.action');" class="selected">Manage Site Record Ownership</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a id="manageSiteOwnershipMenuOption" href="#" onclick="submitXsrfForm('/registry/protected/manageSiteOwnershipsearch.action');" >Manage Site Record Ownership</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </li>                                    
                                 </c:if>
                                <li><a id="logoutMenuOption" href="#" onclick="submitXsrfForm('/registry/logout.action');" >Log Out</a></li>
                    </c:when>
                    <c:otherwise>
                                <c:choose>
                                    <c:when test="${requestScope.topic == 'register'}">
                                       <li><a id="createAccountMenuOption" href="#" onclick="submitXsrfForm('/registry/registerUser.action');" class="selected">Create Account</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                          <c:when test="${requestScope.topic == 'myaccount'}">
                                             <li><a id="createAccountMenuOption" href="#" onclick="submitXsrfForm('/registry/registerUser.action');" class="selected">Create Account</a></li>
                                          </c:when>
                                          <c:otherwise>
                                             <li><a id="createAccountMenuOption" href="#" onclick="submitXsrfForm('/registry/registerUser.action');" >Create Account</a></li>
                                          </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                                <li><a id="registerTrialMenuOption" href="#" onclick="submitXsrfForm('/registry/protected/disClaimerAction.action?actionName=submitTrial.action');" >Register Trial</a></li>
                                <li><a id="searchTrialsMenuOption" href="#" onclick="submitXsrfForm('/registry/protected/disClaimerAction.action?actionName=searchTrial.action');" >Search Trials</a></li>
                                <c:choose>
                                    <c:when test="${requestScope.topic == 'login'}">
                                       <li><a id="loginMenuOption" href="#" onclick="submitXsrfForm('/registry/protected/disClaimerAction.action?actionName=searchTrial.action');" class="selected">Log In</a></li>
                                    </c:when>
                                    <c:otherwise>
                                       <li><a id="loginMenuOption" href="#" onclick="submitXsrfForm('/registry/protected/disClaimerAction.action?actionName=searchTrial.action');" >Log In</a></li>
                                    </c:otherwise>
                                </c:choose>
                    </c:otherwise>
                </c:choose>
                <li><a id="helpMenuOption" href="#" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a></li>
            </ul>
        </li>
