<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<tr style="display:none">
    <td>
        <s:hidden name="trialDTO.phaseCode" id="trialDTO.phaseCode"/>
        <s:hidden name="trialDTO.phaseAdditionalQualifier" id="trialDTO.phaseAdditionalQualifier"/>
        <s:hidden name="trialDTO.trialType" id="trialDTO.trialType"/>
        <s:hidden id="trialDTO.primaryPurposeCode" name="trialDTO.primaryPurposeCode"/>
        <s:hidden id="trialDTO.primaryPurposeAdditionalQualifierCode" name="trialDTO.primaryPurposeAdditionalQualifierCode"/>
    </td>
</tr>
<tr>
    <th colspan="2"><fmt:message key="submit.trial.trialDetails"/></th>
</tr>
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>
<tr>
    <td scope="row" class="label">
        <label for="submitTrial_protocolWebDTO_trialTitle"> 
            <fmt:message key="submit.trial.title"/><span class="required">*</span>
        </label>
    </td>
    <td>
        <s:textarea id="submitTrial_protocolWebDTO_trialTitle" name="trialDTO.officialTitle" cssClass="readonly" readonly="true" cols="75" rows="4" />
        <span class="info">Max 4000 characters</span>
    </td>
</tr>
<tr>
    <td  scope="row" class="label">
        <label>
            <fmt:message key="submit.trial.phase"/><span class="required">*</span>
        </label>
    </td>
    <td>
        <s:property value="trialDTO.phaseCode"/>
    </td>
</tr>
<s:if test="trialDTO.phaseCode == 'NA'">
    <tr>
        <td scope="row" class="label">
            <label> 
                <fmt:message key="submit.trial.otherPhaseText"/>
            </label>
        </td>
        <td>
            <s:property value="trialDTO.phaseAdditionalQualifier"/>
        </td>
    </tr>
</s:if>
<tr>
    <td  scope="row" class="label">
        <label>
            <fmt:message key="submit.trial.type"/><span class="required">*</span>
        </label>
    </td>
    <td>
         Interventional
    </td>
</tr>
<tr>
    <td  scope="row" class="label">
        <label>
            <fmt:message key="submit.trial.purpose"/><span class="required">*</span>
        </label>
    </td>
    <td>
        <s:property value="trialDTO.primaryPurposeCode"/>
    </td>
</tr>
<s:if test="trialDTO.primaryPurposeCode == 'Other'">
    <tr>
        <td scope="row" class="label">
            <label>
                <fmt:message key="submit.trial.otherPurposeText"/>
            </label>
        </td>
         <td>
            <s:textarea id="trialDTO.primaryPurposeOtherText" name="trialDTO.primaryPurposeOtherText" cssClass="readonly" readonly="true" cols="50" rows="2" />
            <span class="info">Required if Purpose equals &#39;Other&#39;</span>
        </td>
    </tr>
</s:if>
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>