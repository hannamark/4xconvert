<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<tr style="display:none">
    <td>
        <s:hidden name="trialDTO.leadOrganizationIdentifier" id="trialDTO.leadOrganizationIdentifier"/>
        <s:hidden name="trialDTO.leadOrganizationName" id="trialDTO.leadOrganizationName"/>
        <s:hidden name="trialDTO.piIdentifier" id="trialDTO.piIdentifier"/>
        <s:hidden name="trialDTO.piName" id="trialDTO.piName"/>
    </td>
</tr>
<tr>
    <th colspan="2"><fmt:message key="submit.trial.leadOrgInvestigator"/></th>
</tr>
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>
<%-- Lead organization --%>
<tr>
    <td scope="row" class="label-noinput">
        <fmt:message key="submit.trial.leadOrganization"/><span class="required">*</span>
    </td>
    <td class="value">
        <s:property value="trialDTO.leadOrganizationName"/>
    </td>
</tr>
<%-- principal investigator --%>
<tr>
    <td scope="row" class="label-noinput">
        <fmt:message key="submit.trial.principalInvestigator"/><span class="required">*</span>
    </td>
    <td class="value">
        <s:property value="trialDTO.piName"/>
    </td>
</tr>
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>