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
<tr>
    <th colspan="2">Summary 4 Information (for trials at NCI-designated cancer centers)</th>
</tr>
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>
<tr>
    <td scope="row" class="label">
        <label>
            Summary 4 Funding Sponsor Type:<span class="required">*</span>
        </label>
    </td>
    <td class="value">
        <s:property value="trialDTO.summaryFourFundingCategoryCode"/>
    </td>
</tr>
<tr>
    <td scope="row" class="label">
        <label> 
            Summary 4 Funding Sponsor: <span class="required">*</span>
        </label>
    </td>
    <td class="value">
        <s:property value="trialDTO.summaryFourOrgName"/>
    </td>
</tr>
<tr>
    <td scope="row" class="label">
        <label for="summary4ProgramCode">
            <fmt:message key="studyProtocol.summaryFourPrgCode"/>
        </label>
    </td>
    <td class="value">
        <s:property value="trialDTO.programCodeText"/>
    </td>
</tr>
<tr>  
    <td colspan="2" class="space">&nbsp;</td>
</tr>