<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<li class="stdnav"><div>NCI CTRP Viewer</div>
    <ul>
        <c:choose>
           <c:when test="${requestScope.topic == 'welcome'}">
              <li><a href="/viewer/home.action" class="selected">Home</a></li>
           </c:when>
           <c:otherwise>
              <li><a href="/viewer/home.action">Home</a></li>
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
                       <li><a href="/viewer/protected/submitTrial.action" class="selected">Register Trial</a></li>
                    </c:when>
                    <c:otherwise>
                       <li><a href="/viewer/protected/submitTrial.action" >Register Trial</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.topic == 'search_trials'}">
                       <li><a href="/viewer/protected/searchTrial.action" class="selected">Search Trials</a></li>
                    </c:when>
                    <c:when test="${requestScope.topic == 'search_results'}">
                       <li><a href="/viewer/protected/searchTrial.action" class="selected">Search Trials</a></li>
                    </c:when>
                    <c:otherwise>
                       <li><a href="/viewer/protected/searchTrial.action" >Search Trials</a></li>
                    </c:otherwise>
                </c:choose>                                   
                <li><a href="/viewer/logout.action" >Log Out</a></li>
            </c:when>
            <c:otherwise>
               <c:choose>
                   <c:when test="${requestScope.topic == 'register'}">
                      <li><a href="/viewer/registerUser.action" class="selected">Create Account</a></li>
                   </c:when>
                   <c:otherwise>
                       <c:choose>
                         <c:when test="${requestScope.topic == 'my_account'}">
                            <li><a href="/viewer/registerUser.action" class="selected">Create Account</a></li>
                         </c:when>
                         <c:otherwise>
                            <li><a href="/viewer/registerUser.action" >Create Account</a></li>
                         </c:otherwise>
                       </c:choose> 
                   </c:otherwise>
               </c:choose>                                 
               <li><a href="/viewer/protected/submitTrial.action" >Register Trial</a></li>
               <li><a href="/viewer/protected/searchTrial.action" >Search Trials</a></li>
               <c:choose>
                   <c:when test="${requestScope.topic == 'login'}">
                      <li><a href="/viewer/protected/searchTrial.action" class="selected">Log In</a></li>
                   </c:when>
                   <c:otherwise>
                      <li><a href="/viewer/protected/searchTrial.action" >Log In</a></li>
                   </c:otherwise>
               </c:choose>
            </c:otherwise>
        </c:choose>
    </ul>
</li>


