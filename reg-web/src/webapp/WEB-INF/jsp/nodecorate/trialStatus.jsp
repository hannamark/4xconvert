<table class="form">
    <tr>
        <th colspan="2"><fmt:message key="update.trial.statusDates" />
        </th>
    </tr>
    <tr>
        <td colspan="2" class="space">&nbsp;</td>
    </tr>
    <tr>
        <td scope="row" class="label"><label for="updateTrial_statusCode"><fmt:message
                    key="update.trial.currentTrialStatus" /><span class="required">*</span>
        </label></td>
        <s:set name="statusCodeValues" value="@gov.nih.nci.pa.enums.StudyStatusCode@getDisplayNamesForAmend()" />
        <td><s:select headerKey="" headerValue="--Select--" name="trialDTO.statusCode" list="#statusCodeValues"
                value="trialDTO.statusCode" cssStyle="width:206px"
                onchange="displayTrialStatusDefinition('updateTrial_trialDTO_statusCode');" /> <span
            class="formErrorMsg"> <s:fielderror>
                    <s:param>trialDTO.statusCode</s:param>
                </s:fielderror> </span></td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td class="info"><%@ include file="/WEB-INF/jsp/nodecorate/trialStatusDefinitions.jsp"%></td>
    </tr>
    <tr>
        <td scope="row" class="label"><label for="updateTrial_reason"> <fmt:message
                    key="update.trial.trialStatusReason" />
        </label></td>
        <td><s:textarea name="trialDTO.reason" cols="50" rows="2" /> <span class="info">Required for
                Administratively Complete ,Withdrawn and Temporarily Closed statuses only</span> <span class="formErrorMsg">
                <s:fielderror>
                    <s:param>trialDTO.reason</s:param>
                </s:fielderror> </span></td>
    </tr>
    <tr>
        <td scope="row" class="label"><label for="updateTrial_statusDate"><fmt:message
                    key="update.trial.currentTrialStatusDate" /><span class="required">*</span>
        </label></td>
        <td class="value"><s:textfield name="trialDTO.statusDate" maxlength="10" size="10"
                cssStyle="width:70px;float:left" /> <a href="javascript:showCal('Cal1')"> <img
                src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /> </a>
            (mm/dd/yyyy) <span class="formErrorMsg"> <s:fielderror>
                    <s:param>trialDTO.statusDate</s:param>
                </s:fielderror> </span></td>
    </tr>
    <s:set name="dateTypeList" value="@gov.nih.nci.pa.enums.ActualAnticipatedTypeCode@getDisplayNames()" />
    <tr>
        <td scope="row" class="label"><label for="updateTrial_startDate"><fmt:message
                    key="update.trial.trialStartDate" /><span class="required">*</span>
        </label></td>
        <td class="value"><s:textfield name="trialDTO.startDate" maxlength="10" size="10"
                cssStyle="width:70px;float:left" /> <a href="javascript:showCal('Cal2')"> <img
                src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /> </a>
            (mm/dd/yyyy) <s:radio name="trialDTO.startDateType" list="#dateTypeList" /> <span class="formErrorMsg">
                <s:fielderror>
                    <s:param>trialDTO.startDate</s:param>
                </s:fielderror> </span> <span class="formErrorMsg"> <s:fielderror>
                    <s:param>trialDTO.startDateType</s:param>
                </s:fielderror> </span></td>
    </tr>
    <tr>
        <td scope="row" class="label"><label for="updateTrial_completionDate"><fmt:message
                    key="update.trial.primaryCompletionDate" /><span class="required">*</span>
        </label></td>
        <td class="value"><s:textfield name="trialDTO.completionDate" maxlength="10" size="10"
                cssStyle="width:70px;float:left" /> <a href="javascript:showCal('Cal3')"> <img
                src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /> </a>
            (mm/dd/yyyy) <s:radio name="trialDTO.completionDateType" list="#dateTypeList" /> <span class="formErrorMsg">
                <s:fielderror>
                    <s:param>trialDTO.completionDate</s:param>
                </s:fielderror> </span> <span class="formErrorMsg"> <s:fielderror>
                    <s:param>trialDTO.completionDateType</s:param>
                </s:fielderror> </span></td>
    </tr>
</table>