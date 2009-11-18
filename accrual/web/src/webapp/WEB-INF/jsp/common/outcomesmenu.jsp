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
    <c:if test="${(pageContext.request.remoteUser != null) && (sessionScope.accrualRole == 'Outcomes')}">
        <c:choose>
            <c:when test="${(requestScope.topic == 'subjects_intro') || (requestScope.topic == 'subjects_adding') || (requestScope.topic == 'subjects_update')}">
                <li><a href="patients.action" class="selected">Study Subject Search</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="patients.action">Study Subject Search</a></li>
            </c:otherwise>
        </c:choose>
    </c:if>
    <c:if test="${(pageContext.request.remoteUser != null) && (sessionScope.accrualRole == 'Outcomes')}">
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
    <c:if test="${(pageContext.request.remoteUser != null) && (sessionScope.accrualRole == 'Outcomes')}">
        <li class="hassubmenu"><c:choose>
            <c:when test="${(requestScope.topic == 'treatment_detail')}">
                <a href="executeTreatment.action" class="selected">Treatment</a>
            </c:when>
            <c:otherwise>
                <a href="executeTreatment.action">Treatment</a>
            </c:otherwise>
        </c:choose>
        <ul id="part_sites">
            <c:choose>
                <c:when test="${(requestScope.topic == 'drugBiologics_detail')}">
                    <li><a href="executeDrugBiologics.action" class="selected">Drug/Biologics</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="executeDrugBiologics.action">Drug/Biologics</a></li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${(requestScope.topic == 'procedure_detail')}">
                    <li><a href="executeProcedure.action" class="selected">Procedure</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="executeProcedure.action">Procedure</a></li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${(requestScope.topic == 'radiation_detail')}">
                    <li><a href="executeRadiation.action" class="selected">Radiation</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="executeRadiation.action">Radiation</a></li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${(requestScope.topic == 'offTreatment_detail')}">
                    <li><a href="executeOffTreatment.action" class="selected">Off
                    Treatment</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="executeOffTreatment.action">Off Treatment</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
        </li>
    </c:if>
    <c:if test="${(pageContext.request.remoteUser != null) && (sessionScope.accrualRole == 'Outcomes')}">
        <li class="hassubmenu"><c:choose>
            <c:when test="${(requestScope.topic == 'patientOutcomes_detail')}">
                <a href="executeParticipantOutcomes.action" class="selected">Patient Outcomes</a>
            </c:when>
            <c:otherwise>
                <a href="executeParticipantOutcomes.action">Patient Outcomes</a>
            </c:otherwise>
        </c:choose>
        <ul id="part_sites">
            <c:choose>
                <c:when test="${(requestScope.topic == 'deathInformation_detail')}">
                    <li><a href="executeDeathInformation.action" class="selected">Death Information</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="executeDeathInformation.action">Death
                    Information</a></li>
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


