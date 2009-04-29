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
                    <c:when test="${requestScope.topic == 'trial_list'}">
                       <li><a href="trialList.action" class="selected">Trial List Report</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="trialList.action" >Trial List Report</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.topic == 'trial_counts'}">
                       <li><a href="trialCounts.action" class="selected">Trial Counts Report</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="trialCounts.action" >Trial Counts Report</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.topic == 'milestones'}">
                       <li><a href="milestones.action" class="selected">Milestones Report</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="milestones.action" >Milestones Report</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.topic == 'summary_sent'}">
                       <li><a href="summarySent.action" class="selected">Summary Sent Report</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="summarySent.action" >Summary Sent Report</a></li>
                    </c:otherwise>
                </c:choose>
                <li><a href="/viewer/logout.action" >Log Out</a></li>
            </c:when>
            <c:otherwise>
               <c:choose>
                   <c:when test="${requestScope.topic == 'login'}">
                      <li><a href="/viewer/public/welcome.action" class="selected">Log In</a></li>
                   </c:when>
                   <c:otherwise>
                      <li><a href="/viewer/public/welcome.action" >Log In</a></li>
                   </c:otherwise>
               </c:choose>
            </c:otherwise>
        </c:choose>
    </ul>
</li>


