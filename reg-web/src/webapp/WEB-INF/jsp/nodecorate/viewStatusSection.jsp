<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<reg-web:titleRow titleKey="view.trial.statusDates"/>
<reg-web:valueRow labelKey="view.trial.currentTrialStatus">
    <c:out value="${trialDTO.statusCode}"/>
</reg-web:valueRow>
<c:if test="${trialDTO.reason != ''}">
    <reg-web:valueRow labelKey="view.trial.trialStatusReason">
        <c:out value="${trialDTO.reason}"/>
    </reg-web:valueRow>
</c:if>
<reg-web:valueRow labelKey="view.trial.currentTrialStatusDate">
    <c:out value="${trialDTO.statusDate}"/>
</reg-web:valueRow>
<reg-web:valueRow labelKey="view.trial.trialStartDate">
    <c:out value="${trialDTO.startDate}"/>
    <c:out value="${trialDTO.startDateType }"/>
</reg-web:valueRow>
<reg-web:valueRow labelKey="view.trial.primaryCompletionDate">
    <c:out value="${trialDTO.primaryCompletionDate}"/>
    <c:out value="${trialDTO.primaryCompletionDateType}"/>
</reg-web:valueRow>
<reg-web:valueRow labelKey="view.trial.completionDate">
    <c:out value="${trialDTO.completionDate}"/>
    <c:out value="${trialDTO.completionDateType}"/>    
</reg-web:valueRow> 
<reg-web:valueRow labelKey="blank.label">
    <span class="info">Please refer to the <a href='<s:property value="@gov.nih.nci.pa.util.PaEarPropertyReader@getStateTransitionDiagramUrl()" />' target="newPage">Trial Status Rules for Start and Completion dates</a>.</span>
</reg-web:valueRow>