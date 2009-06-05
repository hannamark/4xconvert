<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<li class="stdnav"><div>NCI CTRP Viewer</div>
    <ul>
        <c:choose>
           <c:when test="${requestScope.topic == 'welcome'}">
              <li><a href="home.action" class="selected">Home</a></li>
           </c:when>
           <c:otherwise>
              <li><a href="home.action">Home</a></li>
           </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${pageContext.request.remoteUser != null}">
                <c:if test="${(sessionScope.viewerRole == 'Abstractor')}">
                <c:choose>
                    <c:when test="${requestScope.topic == 'SummaryOfSubmission'}">
                       <li><a href="criteriaSummaryOfSubmission.action" class="selected">Summary of Submission</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="criteriaSummaryOfSubmission.action" >Summary of Submission</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.topic == 'TrialProcessing'}">
                       <li><a href="criteriaTrialProcessing.action" class="selected">Trial Processing</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="criteriaTrialProcessing.action" >Trial Processing</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.topic == 'AverageMilestone'}">
                       <li><a href="criteriaAverageMilestone.action" class="selected">Portfolio Average Milestone</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="criteriaAverageMilestone.action" >Portfolio Average Milestone</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.topic == 'SubmissionByDate'}">
                       <li><a href="criteriaSubmissionByDate.action" class="selected">Trials Submitted by Date</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="criteriaSubmissionByDate.action" >Trials Submitted by Date</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.topic == 'SubmissionByInstitution'}">
                       <li><a href="criteriaSubmissionByInstitution.action" class="selected">Trials Submitted by Institution</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="criteriaSubmissionByInstitution.action" >Trials Submitted by Institution</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.topic == 'CurrentMilestone'}">
                       <li><a href="criteriaCurrentMilestone.action" class="selected">Current Milestone</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="criteriaCurrentMilestone.action" >Current Milestone</a></li>
                    </c:otherwise>
                </c:choose>
                <div class="line"></div>
                <c:choose>
                    <c:when test="${requestScope.topic == 'trial_list'}">
                       <li><a href="trialList.action" class="selected">Trial List Report</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="trialList.action" >Trial List Report</a></li>
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


