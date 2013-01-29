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
                    <c:when test="${requestScope.topic == 'trialsintro'}">
                       <li><a href="viewTrials.action" class="selected">Trial Search</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="viewTrials.action" >Trial Search</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${sessionScope.studyProtocolIi != null && !sessionScope.trialSummary.industrial.value}">
                        <c:choose>
                            <c:when test="${(requestScope.topic == 'subjectsintro') || (requestScope.topic == 'subjectsadding') || (requestScope.topic == 'subjectsupdate')}">
                                <li><a href="patients.action" class="selected">Study Subject Search</a></li> 
                            </c:when>
                            <c:otherwise>
                                <li><a href="patients.action" >Study Subject Search</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:when test="${sessionScope.studyProtocolIi != null && sessionScope.trialSummary.industrial.value}">
                        <c:choose>
                            <c:when test="${(requestScope.topic == 'accrualcount')}">
                                <li><a href="industrialPatients.action" class="selected">Record Accrual Count</a></li> 
                            </c:when>
                            <c:otherwise>
                                <li><a href="industrialPatients.action" >Record Accrual Count</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.topic == 'batchupload'}">
                        <li><a href="batchUpload.action" class="selected">Batch Upload</a></li> 
                    </c:when>
                    <c:otherwise>
                        <li><a href="batchUpload.action" >Batch Upload</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.topic == 'priorsubmissions'}">
                        <li><a href="priorSubmissions.action" class="selected">Prior Submissions</a></li> 
                    </c:when>
                    <c:otherwise>
                        <li><a href="priorSubmissions.action" >Prior Submissions</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.topic == 'accrualcounts'}">
                        <li><a href="accrualCounts.action" class="selected">Accrual Counts</a></li> 
                    </c:when>
                    <c:otherwise>
                        <li><a href="accrualCounts.action">Accrual Counts</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.topic == 'diseasesearch'}">
                        <li><a href="diseaseSearch.action" class="selected">Disease Search</a></li> 
                    </c:when>
                    <c:otherwise>
                        <li><a href="diseaseSearch.action" >Disease Search</a></li>
                    </c:otherwise>
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


