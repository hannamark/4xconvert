<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<tr>
    <th colspan="2"><fmt:message key="view.trial.trialIDs"/></th>
</tr>
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>
<tr>
    <td scope="row" class="label">
        <reg-web:displayTooltip tooltip="tooltip.trial_id">
            <label for="leadOrgTrialIdentifierField">
                <fmt:message key="submit.trial.leadOrgidentifier"/><span class="required">*</span>
            </label>
        </reg-web:displayTooltip>
    </td>
    <td>
        <s:textfield id="leadOrgTrialIdentifierField" name="trialDTO.leadOrgTrialIdentifier" maxlength="200" size="100" cssStyle="width:200px" />
        <span class="formErrorMsg">
            <s:fielderror>
                <s:param>trialDTO.leadOrgTrialIdentifier</s:param>
            </s:fielderror>
        </span>
    </td>
</tr>
<tr>
    <td scope="row" class="label">
        <reg-web:displayTooltip tooltip="tooltip.nct_number">
            <label for="nctIdentifierField">
                <fmt:message key="submit.trial.nctNumber"/>
            </label>
        </reg-web:displayTooltip>
    </td>
    <td>
        <s:textfield id="nctIdentifierField" name="trialDTO.nctIdentifier" maxlength="200" size="100" cssStyle="width:200px"/>
    </td>
</tr>
<tr>
    <td scope="row" class="label-noinput">
        <fmt:message key="view.trial.identifier"/>
    </td>
    <td class="value">
        <s:property value="trialDTO.assignedIdentifier"/>
    </td>
</tr>
<c:if test="${trialDTO.ctepIdentifier != null }">
    <tr>
        <td scope="row" class="label">
            <label for="ctepIdentifierField">
                <fmt:message key="submit.trial.ctepIdentifier"/>
            </label>
        </td>
        <td>
            <s:textfield id="ctepIdentifierField" name="trialDTO.ctepIdentifier" maxlength="200" size="100" cssStyle="width:200px"/>
        </td>
    </tr>
</c:if>
<c:if test="${trialDTO.dcpIdentifier!= null}">
    <tr>
        <td scope="row" class="label">
            <label for="dcpIdentifierField">
                <fmt:message key="submit.trial.dcpIdentifier"/>
            </label>
        </td>
        <td>
            <s:textfield id="dcpIdentifierField" name="trialDTO.dcpIdentifier"  maxlength="200" size="100" cssStyle="width:200px"/>
        </td>
    </tr>
</c:if>
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>
