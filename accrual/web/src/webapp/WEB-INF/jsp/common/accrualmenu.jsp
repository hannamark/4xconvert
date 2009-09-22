<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<li class="stdnav"><div>NCI CTRP</div>
    <ul>
        <c:choose>
           <c:when test="${requestScope.topic == 'home'}">
              <li><a href="/accrual/logout.action" class="selected">Home</a></li>
           </c:when>
           <c:otherwise>
              <li><a href="/accrual/logout.action">Home</a></li>
           </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${pageContext.request.remoteUser != null}">
                 <c:choose>
                    <c:when test="${requestScope.topic == 'search_trials'}">
                       <li><a href="viewTrials.action" class="selected">Trial Search</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="viewTrials.action" >Trial Search</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${sessionScope.studyProtocolIi != null}">
                        <c:choose>
                            <c:when test="${requestScope.topic == 'list_accural_submissions'}">
                                <li><a href="accrualSubmissions.action" class="selected">Submissions</a></li> 
                            </c:when>
                            <c:otherwise>
                                <li><a href="accrualSubmissions.action" >Submissions</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                </c:choose>
                <li><a href="/accrual/logout.action" >Log Out</a></li>
            </c:when>
            <c:otherwise>
               <c:choose>
                   <c:when test="${requestScope.topic == 'login'}">
                      <li><a href="/accrual/protected/welcome.action" class="selected">Log In</a></li>
                   </c:when>
                   <c:otherwise>
                      <li><a href="/accrual/protected/welcome.action" >Log In</a></li>
                   </c:otherwise>
               </c:choose>
            </c:otherwise>
        </c:choose>
    </ul>
</li>


