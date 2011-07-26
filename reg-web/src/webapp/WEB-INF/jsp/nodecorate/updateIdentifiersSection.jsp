<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<tr style="display:none">
    <td>
        <s:hidden name="trialDTO.leadOrgTrialIdentifier" id="trialDTO.leadOrgTrialIdentifier"/>
        <s:hidden name="trialDTO.nctIdentifier" id="trialDTO.nctIdentifier"/>
        <s:hidden name="trialDTO.assignedIdentifier" id="trialDTO.assignedIdentifier"/>
        <s:hidden name="trialDTO.ctepIdentifier" id="trialDTO.ctepIdentifier"/>
        <s:hidden name="trialDTO.dcpIdentifier" id="trialDTO.dcpIdentifier"/>
    </td>
</tr>
<tr>
    <th colspan="2"><fmt:message key="view.trial.trialIDs"/></th>
</tr>
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>
<tr>
    <td scope="row" class="label">
        <label>
            <fmt:message key="submit.trial.leadOrgidentifier"/><span class="required">*</span>
        </label>
    </td>
    <td>
        <s:property value="trialDTO.leadOrgTrialIdentifier"/>
    </td>
</tr>
<tr>
    <td scope="row" class="label">
        <label>
            <fmt:message key="submit.trial.nctNumber"/>
        </label>
    </td>
    <td>
        <s:property value="trialDTO.nctIdentifier"/>
    </td>
</tr>
<tr>
    <td scope="row" class="label">
        <label>
            <fmt:message key="view.trial.identifier"/>
        </label>
    </td>
    <td class="value">
        <s:property value="trialDTO.assignedIdentifier"/>
    </td>
</tr>
<c:if test="${trialDTO.ctepIdentifier != null }">
    <tr>
        <td scope="row" class="label">
            <label>
                <fmt:message key="submit.trial.ctepIdentifier"/>
            </label>
        </td>
        <td>
            <s:property value="trialDTO.ctepIdentifier"/>
        </td>
    </tr>
</c:if>
<c:if test="${trialDTO.dcpIdentifier!= null}">
    <tr>
        <td scope="row" class="label">
            <label>
                <fmt:message key="submit.trial.dcpIdentifier"/>
            </label>
        </td>
        <td>
            <s:property value="trialDTO.dcpIdentifier"/>
        </td>
    </tr>
</c:if>
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>
