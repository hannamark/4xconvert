<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<li class="stdnav"><div>NCI CTRP</div>
    <ul>
        <c:choose>
           <c:when test="${requestScope.topic == 'run_reports'}">
              <li><a href="welcome.action" class="selected">Home</a></li>
           </c:when>
           <c:when test="${requestScope.topic == 'login'}">
              <li><a href="/accrual/logout.action">Home</a></li>
           </c:when>
           <c:otherwise>
              <li><a href="welcome.action">Home</a></li>
           </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${pageContext.request.remoteUser != null}">
                 
                 <!-- 
                 <c:choose>
                    <c:when test="${requestScope.topic == 'run_sample'}">
                       <li><a href="sample.action" class="selected">Sample Action</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="sample.action" >Sample Action</a></li>
                    </c:otherwise>
                </c:choose>
                
                <c:choose>
                    <c:when test="${requestScope.topic == 'list_protocols'}">
                       <li><a href="viewProtocol.action" class="selected">View Protocol</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="viewProtocol.action" >View Protocol</a></li>
                    </c:otherwise>
                </c:choose>
                -->
                
                 <c:choose>
                    <c:when test="${requestScope.topic == 'list_trials'}">
                       <li><a href="viewTrials.action" class="selected">View Trials</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="viewTrials.action" >View Trials</a></li>
                    </c:otherwise>
                </c:choose>
                
                 <c:choose>
                    <c:when test="${requestScope.topic == 'search_trials'}">
                       <li><a href="viewTrialssearch.action" class="selected">Search Trials</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="viewTrialssearch.action" >Search Trials</a></li>
                    </c:otherwise>
                </c:choose>
                
                                
                 <li>Accrual Submission</li> 
                <c:choose>
                    <c:when test="${requestScope.topic == ''}">
                       <li><a href="" class="selected">View Previous Submission</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="" >View Previous Submission</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.topic == ''}">
                       <li><a href="" class="selected">New Submission</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="" >New Submission</a></li>
                    </c:otherwise>
                </c:choose>
                
                <!-- 
                
                 <li>Reporting</li> 
                <c:choose>
                    <c:when test="${requestScope.topic == ''}">
                       <li><a href="" class="selected">CDS Reporting</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="" >CDS Reporting</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.topic == ''}">
                       <li><a href="" class="selected">Basic Results Reporting</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="" >Basic Results Reporting</a></li>
                    </c:otherwise>
                </c:choose>
                
                -->
              <li><a href="/accrual/logout.action" >Log Out</a></li>
            </c:when>
            <c:otherwise>
               <c:choose>
                   <c:when test="${requestScope.topic == 'login'}">
                      <li><a href="/accrual/public/welcome.action" class="selected">Log In</a></li>
                   </c:when>
                   <c:otherwise>
                      <li><a href="/accrual/public/welcome.action" >Log In</a></li>
                   </c:otherwise>
               </c:choose>
            </c:otherwise>
        </c:choose>
    </ul>
</li>


