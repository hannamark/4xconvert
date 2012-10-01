<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<tr style="display:none">
    <td>
        <s:hidden name="trialDTO.summaryFourFundingCategoryCode" id="trialDTO.summaryFourFundingCategoryCode" />
        <s:hidden name="trialDTO.summaryFourOrgIdentifier" id="trialDTO.summaryFourOrgIdentifier"/>
        <s:hidden name="trialDTO.summaryFourOrgName" id="trialDTO.summaryFourOrgName" />
        <s:hidden name="trialDTO.programCodeText" id="trialDTO.programCodeText" />
    </td>
</tr>
<!--  summary4 information -->
<reg-web:titleRow titleKey="update.proprietary.trial.summary4Info"/>
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>
<tr>
    <td scope="row" class="label-noinput">
        Summary 4 Funding Sponsor Type:<span class="required">*</span>
    </td>
    <td class="value">
        <s:property value="trialDTO.summaryFourFundingCategoryCode"/>
    </td>
</tr>
<tr>
    <td scope="row" class="label-noinput">
        Summary 4 Funding Sponsor: <span class="required">*</span>
    </td>
    <td class="value">
        <s:property value="trialDTO.summaryFourOrgName"/>
    </td>
</tr>
<tr>
    <td scope="row" class="label-noinput">
        <fmt:message key="studyProtocol.summaryFourPrgCode"/>
    </td>
    <td class="value">
        <s:property value="trialDTO.programCodeText"/>
    </td>
</tr>
<tr>  
    <td colspan="2" class="space">&nbsp;</td>
</tr>