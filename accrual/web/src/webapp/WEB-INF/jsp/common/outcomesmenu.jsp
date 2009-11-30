<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<li class="stdnav">
<div>NCI Outcomes</div>
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
            <c:when test="${(requestScope.topic == 'subjects_intro') || (requestScope.topic == 'subjects_adding') || (requestScope.topic == 'subjects_update')}">
                <li><a href="executeParticipants.action" class="selected">Patient Search</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="executeParticipants.action">Patient Search</a></li>
            </c:otherwise>
        </c:choose>
    </c:if>
    <c:if test="${(pageContext.request.remoteUser != null) && (sessionScope.accrualRole == 'Outcomes') && (sessionScope.studySubjectIi != null)}">
        <li class="hassubmenu">Baseline Data
        <ul id="part_sites">
            <c:choose>
                <c:when test="${(requestScope.topic == 'diagnosis_detail')}">
                    <li><a href="executeDiagnosis.action" class="selected">Diagnosis</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="executeDiagnosis.action">Diagnosis</a></li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${(requestScope.topic == 'staging_detail')}">
                    <li><a href="executeStaging.action" class="selected">Staging</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="executeStaging.action">Staging</a></li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${(requestScope.topic == 'pathology_detail')}">
                    <li><a href="executePathology.action" class="selected">Pathology</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="executePathology.action">Pathology</a></li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${(requestScope.topic == 'priorTherapies_detail')}">
                    <li><a href="executePriorTherapies.action" class="selected">Prior
                    Therapies</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="executePriorTherapies.action">Prior
                    Therapies</a></li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${(requestScope.topic == 'performanceStatus_detail')}">
                    <li><a href="executePerformanceStatus.action" class="selected">Performance Status</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="executePerformanceStatus.action">Performance Status</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
        </li>
    </c:if>
    <c:if test="${(pageContext.request.remoteUser != null) && (sessionScope.accrualRole == 'Outcomes') && (sessionScope.studySubjectIi != null)}">
        <li class="hassubmenu">Treatment
        <ul id="part_sites">
        <c:choose>
                <c:when test="${(requestScope.topic == 'treatment_detail') || (requestScope.topic == 'treatment_adding') || (requestScope.topic == 'treatment_update')}">
                    <li><a href="executeTreatment.action" class="selected">Treatment Plan</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="executeTreatment.action">Treatment Plan</a></li>
                </c:otherwise>
            </c:choose>
            <c:if test="${(sessionScope.treatmentPlanIi != null)}">
            <c:choose>
                <c:when test="${(requestScope.topic == 'course_detail') || (requestScope.topic == 'course_adding') || (requestScope.topic == 'course_update')}">
                    <li><a href="executeCourse.action" class="selected">Course</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="executeCourse.action">Course</a></li>
                </c:otherwise>
            </c:choose>
            </c:if>
            <c:if test="${(sessionScope.courseIi != null)}">
            <c:choose>
                <c:when test="${(requestScope.topic == 'drugBiologics_detail') || (requestScope.topic == 'drugBiologics_adding') || (requestScope.topic == 'drugBiologics_update')}">
                    <li><a href="executeDrugBiologics.action" class="selected">Drug/Biologics</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="executeDrugBiologics.action">Drug/Biologics</a></li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${(requestScope.topic == 'surgery_detail') || (requestScope.topic == 'surgery_adding') || (requestScope.topic == 'surgery_update')}">
                    <li><a href="executeSurgery.action" class="selected">Surgery</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="executeSurgery.action">Surgery</a></li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${(requestScope.topic == 'radiation_detail') || (requestScope.topic == 'radiation_adding') || (requestScope.topic == 'radiation_update')}">
                    <li><a href="executeRadiation.action" class="selected">Radiation</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="executeRadiation.action">Radiation</a></li>
                </c:otherwise>
            </c:choose>
            </c:if>
            <c:if test="${(sessionScope.treatmentPlanIi != null)}">
            <c:choose>
                <c:when test="${(requestScope.topic == 'offTreatment_detail')}">
                    <li><a href="executeOffTreatment.action" class="selected">Off
                    Treatment</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="executeOffTreatment.action">Off Treatment</a></li>
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
            <c:when test="${(requestScope.topic == 'diseaseEvaluation_detail') || (requestScope.topic == 'diseaseEvaluation_adding') }">
                <a href="executeParticipantOutcomes.action" class="selected">Disease Evaluation</a>
            </c:when>
            <c:otherwise>
                <a href="executeParticipantOutcomes.action">Disease Evaluation</a>
            </c:otherwise>
        </c:choose>
        <c:choose>
                <c:when test="${(requestScope.topic == 'lesionAssessment_detail') || (requestScope.topic == 'lesionAssessment_adding') || (requestScope.topic == 'lesionAssessment_update')}">
                    <li><a href="executeLesionAssessment.action" class="selected">Lesion Assessment</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="executeLesionAssessment.action">Lesion Assessment</a></li>
                </c:otherwise>
         </c:choose>
         <c:choose>
                <c:when test="${(requestScope.topic == 'deathInformation_detail')}">
                    <li><a href="executeDeathInformation.action" class="selected">Death Information</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="executeDeathInformation.action">Death Information</a></li>
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


