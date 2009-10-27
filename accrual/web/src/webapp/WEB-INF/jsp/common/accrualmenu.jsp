<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<li class="stdnav"><div>NCI CTRP</div>
    <ul>
        <c:choose>
           <c:when test="${requestScope.topic == 'home'}">
              <li><a href="home.action" class="selected">Home</a></li>
           </c:when>
           <c:otherwise>
              <li><a href="home.action">Home</a></li>
           </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${pageContext.request.remoteUser != null}">
                 <c:choose>
                    <c:when test="${requestScope.topic == 'trials_intro'}">
                       <li><a href="viewTrials.action" class="selected">Trial Search</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="viewTrials.action" >Trial Search</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${sessionScope.studyProtocolIi != null}">
                        <c:choose>
                            <c:when test="${(requestScope.topic == 'accrual_submissions') || (requestScope.topic == 'accrual_submitting') || (requestScope.topic == 'accrual_reviewing')}">
                                <li><a href="accrualSubmissions.action" class="selected">Submissions</a></li> 
                            </c:when>
                            <c:otherwise>
                                <li><a href="accrualSubmissions.action" >Submissions</a></li>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${(requestScope.topic == 'subjects_intro') || (requestScope.topic == 'subjects_adding') || (requestScope.topic == 'subjects_update')}">
                                <li><a href="patients.action" class="selected">Study Subject Search</a></li> 
                            </c:when>
                            <c:otherwise>
                                <li><a href="patients.action" >Study Subject Search</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                </c:choose>
                <li><a href="/accrual/logout.action" >Log Out</a></li>
            </c:when>
            <c:otherwise>
               <c:choose>
                   <c:when test="${requestScope.topic == 'login'}">
                      <li><a href="/accrual/common/welcome.action" class="selected">Log In</a></li>
                   </c:when>
                   <c:otherwise>
                      <li><a href="/accrual/common/welcome.action" >Log In</a></li>
                   </c:otherwise>
               </c:choose>
            </c:otherwise>
        </c:choose>
    </ul>
</li>


