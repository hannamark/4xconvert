<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<li class="stdnav"><div>NCI CTRP Viewer</div>
    <ul>
        <c:choose>
           <c:when test="${requestScope.topic == 'run_reports'}">
              <li><a href="welcome.action" class="selected">Home</a></li>
           </c:when>
           <c:when test="${requestScope.topic == 'login'}">
              <li><a href="/viewer/logout.action">Home</a></li>
           </c:when>
           <c:otherwise>
              <li><a href="welcome.action">Home</a></li>
           </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${pageContext.request.remoteUser != null}">
                <c:if test="${(sessionScope.viewerRole == 'Abstractor')}">
                <c:choose>
                    <c:when test="${requestScope.topic == 'run_summary_submission'}">
                       <li><a href="criteriaSummaryOfSubmission.action" class="selected">Summary of Submission</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="criteriaSummaryOfSubmission.action" >Summary of Submission</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.topic == 'run_trial_process'}">
                       <li><a href="criteriaTrialProcessing.action" class="selected">Trial Processing</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="criteriaTrialProcessing.action" >Trial Processing</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.topic == 'run_portfolio_average'}">
                       <li><a href="criteriaAverageMilestone.action" class="selected">Portfolio Average Milestone</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="criteriaAverageMilestone.action" >Portfolio Average Milestone</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.topic == 'run_submitted_date'}">
                       <li><a href="criteriaSubmissionByDate.action" class="selected">Trials Submitted by Date</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="criteriaSubmissionByDate.action" >Trials Submitted by Date</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.topic == 'run_submitted_institution'}">
                       <li><a href="criteriaSubmissionByInstitution.action" class="selected">Trials Submitted by Institution</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="criteriaSubmissionByInstitution.action" >Trials Submitted by Institution</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.topic == 'run_current_milestone'}">
                       <li><a href="criteriaCurrentMilestone.action" class="selected">Current Milestone</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="criteriaCurrentMilestone.action" >Current Milestone</a></li>
                    </c:otherwise>
                </c:choose>
              </c:if>  
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


