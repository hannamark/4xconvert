<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<li class="stdnav">
<div>NCI Outcomes</div>
<ul>
    <c:choose>
        <c:when test="${(requestScope.topic == 'home') || (requestScope.topic == '')}">
            <li><a href="/outcomes/home.action" class="selected">Home</a></li>
        </c:when>
        <c:otherwise>
            <li><a href="/outcomes/home.action">Home</a></li>
        </c:otherwise>
    </c:choose>
    
    <c:choose>
        <c:when test="${pageContext.request.remoteUser == null}">            
            <c:choose>
                <c:when test="${requestScope.topic == 'create_account'}">
                    <li><a href="/outcomes/userAccount.action" class="selected">Create Account</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/outcomes/userAccount.action">Create Account</a></li>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${requestScope.topic == 'create_account'}">
                    <li><a href="/outcomes/outcomes/userAccountupdateAccount.action" class="selected">My Account</a></li> 
                </c:when>
                <c:otherwise>
                    <li><a href="/outcomes/outcomes/userAccountupdateAccount.action" >My Account</a></li>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>
    
    <c:if test="${(pageContext.request.remoteUser != null) && (sessionScope.accrualRole == 'Outcomes')}">
        <c:choose>
            <c:when test="${(requestScope.topic == 'search_patient') || (requestScope.topic == 'add_patient') || (requestScope.topic == 'update_patient') || (requestScope.topic == 'view_patient')}">
                <li><a href="/outcomes/outcomes/executeParticipants.action" class="selected">Patient Search</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="/outcomes/outcomes/executeParticipants.action">Patient Search</a></li>
            </c:otherwise>
        </c:choose>
    </c:if>
    <c:if test="${(pageContext.request.remoteUser != null) && (sessionScope.accrualRole == 'Outcomes') && (sessionScope.studySubjectIi != null)}">
        <li class="hassubmenu">Baseline Data
        <ul id="part_sites">
            <c:choose>
                <c:when test="${(requestScope.topic == 'diagnosis')}">
                    <li><a href="/outcomes/outcomes/executeDiagnosis.action" class="selected">Diagnosis</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/outcomes/outcomes/executeDiagnosis.action">Diagnosis</a></li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${(requestScope.topic == 'staging') || (requestScope.topic == 'add_tumor')}">
                    <li><a href="/outcomes/outcomes/executeStaging.action" class="selected">Staging</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/outcomes/outcomes/executeStaging.action">Staging</a></li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${(requestScope.topic == 'pathology')}">
                    <li><a href="/outcomes/outcomes/executePathology.action" class="selected">Pathology</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/outcomes/outcomes/executePathology.action">Pathology</a></li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${(requestScope.topic == 'prior_therapies')}">
                    <li><a href="/outcomes/outcomes/executePriorTherapies.action" class="selected">Prior Therapies</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/outcomes/outcomes/executePriorTherapies.action">Prior Therapies</a></li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${(requestScope.topic == 'performance')}">
                    <li><a href="/outcomes/outcomes/executePerformanceStatus.action" class="selected">Performance Status</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/outcomes/outcomes/executePerformanceStatus.action">Performance Status</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
        </li>
    </c:if>
    <c:if test="${(pageContext.request.remoteUser != null) && (sessionScope.accrualRole == 'Outcomes') && (sessionScope.studySubjectIi != null)}">
        <li class="hassubmenu">Treatment
        <ul id="part_sites">
        <c:choose>
                <c:when test="${(requestScope.topic == 'treatment_plan') || (requestScope.topic == 'add_treatment') || (requestScope.topic == 'update_treatment')}">
                    <li><a href="/outcomes/outcomes/executeTreatment.action" class="selected">Treatment Regimen</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/outcomes/outcomes/executeTreatment.action">Treatment Regimen</a></li>
                </c:otherwise>
            </c:choose>
            <c:if test="${(sessionScope.treatmentPlanIi != null)}">
            <c:choose>
                <c:when test="${(requestScope.topic == 'course') || (requestScope.topic == 'add_course') || (requestScope.topic == 'update_course')}">
                    <li><a href="/outcomes/outcomes/executeCourse.action" class="selected">Cycle</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/outcomes/outcomes/executeCourse.action">Cycle</a></li>
                </c:otherwise>
            </c:choose>
            </c:if>
            <c:if test="${(sessionScope.courseIi != null)}">
            <c:choose>
                <c:when test="${(requestScope.topic == 'biologics') || (requestScope.topic == 'add_biologics') || (requestScope.topic == 'update_biologics')}">
                    <li><a href="/outcomes/outcomes/executeDrugBiologics.action" class="selected">Drug/Biologics</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/outcomes/outcomes/executeDrugBiologics.action">Drug/Biologics</a></li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${(requestScope.topic == 'surgery') || (requestScope.topic == 'add_surgery') || (requestScope.topic == 'update_surgery')}">
                    <li><a href="/outcomes/outcomes/executeSurgery.action" class="selected">Surgery</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/outcomes/outcomes/executeSurgery.action">Surgery</a></li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${(requestScope.topic == 'radiation') || (requestScope.topic == 'add_radiation') || (requestScope.topic == 'update_radiation')}">
                    <li><a href="/outcomes/outcomes/executeRadiation.action" class="selected">Radiation</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/outcomes/outcomes/executeRadiation.action">Radiation</a></li>
                </c:otherwise>
            </c:choose>
            </c:if>
            <c:if test="${(sessionScope.treatmentPlanIi != null)}">
            <c:choose>
                <c:when test="${(requestScope.topic == 'off_treatment')}">
                    <li><a href="/outcomes/outcomes/executeOffTreatment.action" class="selected">Off
                    Treatment</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/outcomes/outcomes/executeOffTreatment.action">Off Treatment</a></li>
                </c:otherwise>
            </c:choose>
            </c:if>
        </ul>
        </li>
    </c:if>
    <c:if test="${(pageContext.request.remoteUser != null) && (sessionScope.accrualRole == 'Outcomes') && (sessionScope.studySubjectIi != null)}">
        <li class="hassubmenu">Patient Outcomes
         <ul id="part_sites">
        <c:choose>
            <c:when test="${(requestScope.topic == 'evaluation') || (requestScope.topic == 'add_evaluation') }">
                <a href="/outcomes/outcomes/executeParticipantOutcomes.action" class="selected">Disease Evaluation</a>
            </c:when>
            <c:otherwise>
                <a href="/outcomes/outcomes/executeParticipantOutcomes.action">Disease Evaluation</a>
            </c:otherwise>
        </c:choose>
        <c:choose>
                <c:when test="${(requestScope.topic == 'lesion') || (requestScope.topic == 'add_lesion') || (requestScope.topic == 'update_lesion')}">
                    <li><a href="/outcomes/outcomes/executeLesionAssessment.action" class="selected">Lesion Assessment</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/outcomes/outcomes/executeLesionAssessment.action">Lesion Assessment</a></li>
                </c:otherwise>
         </c:choose>
         <c:choose>
                <c:when test="${(requestScope.topic == 'death')}">
                    <li><a href="/outcomes/outcomes/executeDeathInformation.action" class="selected">Death Information</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/outcomes/outcomes/executeDeathInformation.action">Death Information</a></li>
                </c:otherwise>
         </c:choose>
        </ul>
        </li>
    </c:if>
    <c:choose>
        <c:when test="${pageContext.request.remoteUser != null}">
            <li><a href="/outcomes/logout.action">Log Out</a></li>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${requestScope.topic == 'login'}">
                    <li><a href="/outcomes/common/welcome.action" class="selected">Log In</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/outcomes/common/welcome.action">Log In</a></li>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>
</ul>
</li>


