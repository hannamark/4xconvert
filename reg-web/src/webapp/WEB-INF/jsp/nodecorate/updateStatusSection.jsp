<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:titleRow titleKey="update.trial.statusDates"/>
<reg-web:spaceRow/>
<reg-web:valueRow labelFor="trialDTO_statusCode" labelKey="update.trial.currentTrialStatus" required="true" tooltip="tooltip.current_trial_status">
    <s:set name="statusCodeValues" value="@gov.nih.nci.pa.enums.StudyStatusCode@getDisplayNamesForAmend()" />
    <s:select headerKey="" headerValue="--Select--" id="trialDTO_statusCode" name="trialDTO.statusCode" list="#statusCodeValues"
                  value="trialDTO.statusCode" onchange="displayTrialStatusDefinition('trialDTO_statusCode');" /> 
    <span class="formErrorMsg"> 
        <s:fielderror>
            <s:param>trialDTO.statusCode</s:param>
        </s:fielderror>
    </span>
</reg-web:valueRow>

<tr>
    <td>&nbsp;</td>
    <td class="info">
        <%@ include file="/WEB-INF/jsp/nodecorate/trialStatusDefinitions.jsp"%>
    </td>
</tr>

<reg-web:valueRow labelFor="trialDTO_reason" labelKey="update.trial.trialStatusReason" tooltip="tooltip.why_study_stopped">
    <s:textarea id="trialDTO_reason" name="trialDTO.reason" cols="50" rows="2" maxlength="160"
        cssClass="charcounter"
    /> 
    </br>
    <span class="info">Required for Administratively Complete, Withdrawn and Temporarily Closed statuses only</span>
    <span class="formErrorMsg">
        <s:fielderror>
            <s:param>trialDTO.reason</s:param>
        </s:fielderror> 
    </span>
</reg-web:valueRow>

<reg-web:valueRow labelFor="trialDTO_statusDate" labelKey="update.trial.currentTrialStatusDate" required="true" tooltip="tooltip.current_trial_status_date">
    <s:textfield id="trialDTO_statusDate" name="trialDTO.statusDate" maxlength="10" size="10" cssStyle="width:70px;float:left" /> 
    <a href="javascript:showCal('Cal1')"> 
        <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" />
    </a> (mm/dd/yyyy) 
    <span class="formErrorMsg">
        <s:fielderror>
            <s:param>trialDTO.statusDate</s:param>
        </s:fielderror>
    </span>
</reg-web:valueRow>

<s:set name="dateTypeList" value="@gov.nih.nci.pa.enums.ActualAnticipatedTypeCode@getDisplayNames()" />

<reg-web:valueRow labelFor="trialDTO_startDate" labelKey="update.trial.trialStartDate" required="true" tooltip="tooltip.trial_start_date">
    <s:textfield id="trialDTO_startDate" name="trialDTO.startDate" maxlength="10" size="10" cssStyle="width:70px;float:left" />
    <a href="javascript:showCal('Cal2')"> 
        <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" /> 
    </a> (mm/dd/yyyy) 
    <s:radio id="trialDTO_startDateType" name="trialDTO.startDateType" list="#dateTypeList" />
    <span class="formErrorMsg">
        <s:fielderror>
            <s:param>trialDTO.startDate</s:param>
        </s:fielderror>
    </span>
    <span class="formErrorMsg">
        <s:fielderror>
            <s:param>trialDTO.startDateType</s:param>
        </s:fielderror>
    </span>
</reg-web:valueRow>
<!-- Hide the asterix in primary completion date label if it is non interventional trial and CTGovXmlRequired is false -->
<c:set var="asterix" value="true"/>
<c:if test="${trialDTO.trialType == 'NonInterventional' && trialDTO.xmlRequired == false}">
    <c:set var="asterix" value="false"/>
</c:if>
<reg-web:valueRow requiredId="primaryCompletionDateId" labelFor="trialDTO_primaryCompletionDate" labelKey="update.trial.primaryCompletionDate" required="${asterix}" tooltip="tooltip.primary_completion_date">
    <s:textfield id="trialDTO_primaryCompletionDate" name="trialDTO.primaryCompletionDate" maxlength="10" size="10" cssStyle="width:70px;float:left" />
    <a href="javascript:showCal('Cal3')">
        <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" /> 
    </a> (mm/dd/yyyy)
    <s:radio id="trialDTO_primaryCompletionDateType" name="trialDTO.primaryCompletionDateType" list="#dateTypeList" />
    <span class="formErrorMsg">
        <s:fielderror>
            <s:param>trialDTO.primaryCompletionDate</s:param>
        </s:fielderror>
    </span>
    <span class="formErrorMsg">
        <s:fielderror>
            <s:param>trialDTO.primaryCompletionDateType</s:param>
        </s:fielderror>
    </span>
</reg-web:valueRow>

<reg-web:valueRow labelFor="trialDTO_completionDate" labelKey="update.trial.completionDate" tooltip="tooltip.completion_date">
    <s:textfield id="trialDTO_completionDate" name="trialDTO.completionDate" maxlength="10" size="10" cssStyle="width:70px;float:left" />
    <a href="javascript:showCal('Cal4')">
        <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" /> 
    </a> (mm/dd/yyyy)
    <s:radio id="trialDTO_completionDateType" name="trialDTO.completionDateType" list="#dateTypeList" />
    <span class="formErrorMsg">
        <s:fielderror>
            <s:param>trialDTO.completionDate</s:param>
        </s:fielderror>
    </span>
    <span class="formErrorMsg">
        <s:fielderror>
            <s:param>trialDTO.completionDateType</s:param>
        </s:fielderror>
    </span>
</reg-web:valueRow>
<reg-web:valueRow labelKey="blank.label" noLabelTag="true"> 
    <span class="info">Please refer to the <a href="javascript:void(0)" onclick="Help.popHelp('statusdates');">Trial Status Rules for Start and Completion dates</a>.</span>
</reg-web:valueRow>
<reg-web:spaceRow/> 
