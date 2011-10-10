<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<reg-web:titleRow titleKey="update.trial.statusDates"/>

<reg-web:spaceRow/>

<reg-web:valueRow labelFor="updateTrial_statusCode" labelKey="update.trial.currentTrialStatus" required="true">
    <s:set name="statusCodeValues" value="@gov.nih.nci.pa.enums.StudyStatusCode@getDisplayNamesForAmend()" />
    <s:select headerKey="" headerValue="--Select--" id="updateTrial_trialDTO_statusCode" name="trialDTO.statusCode" list="#statusCodeValues"
                  value="trialDTO.statusCode" onchange="displayTrialStatusDefinition('updateTrial_trialDTO_statusCode');" /> 
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

<reg-web:valueRow labelFor="updateTrial_reason" labelKey="update.trial.trialStatusReason">
    <s:textarea name="trialDTO.reason" cols="50" rows="2" /> 
    <span class="info">Required for Administratively Complete, Withdrawn and Temporarily Closed statuses only</span>
    <span class="formErrorMsg">
        <s:fielderror>
            <s:param>trialDTO.reason</s:param>
        </s:fielderror> 
    </span>
</reg-web:valueRow>

<reg-web:valueRow labelFor="updateTrial_statusDate" labelKey="update.trial.currentTrialStatusDate" required="true">
    <s:textfield name="trialDTO.statusDate" maxlength="10" size="10" cssStyle="width:70px;float:left" /> 
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

<reg-web:valueRow labelFor="updateTrial_startDate" labelKey="update.trial.trialStartDate" required="true">
    <s:textfield name="trialDTO.startDate" maxlength="10" size="10" cssStyle="width:70px;float:left" />
    <a href="javascript:showCal('Cal2')"> 
        <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" /> 
    </a> (mm/dd/yyyy) 
    <s:radio name="trialDTO.startDateType" list="#dateTypeList" />
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

<reg-web:valueRow labelFor="updateTrial_primaryCompletionDate" labelKey="update.trial.primaryCompletionDate" required="true">
    <s:textfield name="trialDTO.primaryCompletionDate" maxlength="10" size="10" cssStyle="width:70px;float:left" />
    <a href="javascript:showCal('Cal3')">
        <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" /> 
    </a> (mm/dd/yyyy)
    <s:radio name="trialDTO.primaryCompletionDateType" list="#dateTypeList" />
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

<reg-web:spaceRow/>
