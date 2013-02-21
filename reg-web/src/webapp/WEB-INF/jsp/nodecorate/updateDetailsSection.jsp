<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<tr style="display:none">
    <td>
        <s:hidden name="trialDTO.phaseCode" id="trialDTO.phaseCode"/>
        <s:hidden name="trialDTO.phaseAdditionalQualifier" id="trialDTO.phaseAdditionalQualifier"/>
        <s:hidden name="trialDTO.trialType" id="trialDTO.trialType"/>
        <s:hidden id="trialDTO.primaryPurposeCode" name="trialDTO.primaryPurposeCode"/>
        <s:hidden id="trialDTO.primaryPurposeAdditionalQualifierCode" name="trialDTO.primaryPurposeAdditionalQualifierCode"/>
        <s:hidden id="trialDTO.secondaryPurposesAsString" name="trialDTO.secondaryPurposesAsString"/>                
		<s:hidden id="trialDTO.studySubtypeCode" name="trialDTO.studySubtypeCode"/>
		<s:hidden id="trialDTO.studyModelCode" name="trialDTO.studyModelCode"/>
		<s:hidden id="trialDTO.studyModelOtherText" name="trialDTO.studyModelOtherText"/>
		<s:hidden id="trialDTO.timePerspectiveCode" name="trialDTO.timePerspectiveCode"/>
		<s:hidden id="trialDTO.timePerspectiveOtherText" name="trialDTO.timePerspectiveOtherText"/>         
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
        
    </td>
</tr>
<tr>
    <td  scope="row" class="label-noinput">

            <fmt:message key="submit.trial.phase"/><span class="required">*</span>
    </td>
    <td>
        <s:property value="trialDTO.phaseCode"/>
    </td>
</tr>
<s:if test="trialDTO.phaseCode == 'NA'">
    <tr>
        <td scope="row" class="label-noinput">
                <fmt:message key="submit.trial.otherPhaseText"/>
        </td>
        <td>            
            <c:out value="${trialDTO.phaseAdditionalQualifier=='Pilot'?'Yes':'No'}"/>
        </td>
    </tr>
</s:if>
<tr>
    <td  scope="row" class="label-noinput">
            <fmt:message key="submit.trial.type"/><span class="required">*</span>
    </td>
    <td>
         <s:property value="trialDTO.trialType"/>
    </td>
</tr>
<tr>
    <td  scope="row" class="label-noinput">
            <fmt:message key="submit.trial.purpose"/><span class="required">*</span>
    </td>
    <td>
        <s:property value="trialDTO.primaryPurposeCode"/>
    </td>
</tr>
<s:if test="trialDTO.primaryPurposeCode == 'Other'">
    <tr>
        <td scope="row" class="label">
            <label for="trialDTO.primaryPurposeOtherText">
                <fmt:message key="submit.trial.otherPurposeText"/>
            </label>
        </td>
         <td>
            <s:textarea id="trialDTO.primaryPurposeOtherText" name="trialDTO.primaryPurposeOtherText" cssClass="readonly" readonly="true" cols="50" rows="2" />
            <span class="info">Required if Purpose equals &#39;Other&#39;</span>
        </td>
    </tr>
</s:if>
<s:if test="trialDTO.trialType == 'InterventionalStudyProtocol' || trialDTO.trialType == 'Interventional'">
<tr>
    <td  scope="row" class="label">
        <label>
            <fmt:message key="view.trial.secondaryPurpose"/>
        </label>
    </td>
    <td>
        <s:property value="trialDTO.secondaryPurposeAsReadableString"/>
    </td>
</tr>
</s:if>
<s:if test="trialDTO.trialType != 'InterventionalStudyProtocol' && trialDTO.trialType != 'Interventional'">
<tr>
    <td  scope="row" class="label">
        <label>
            <fmt:message key="submit.trial.studySubtypeCode"/>
        </label>
    </td>
    <td>
        <s:property value="trialDTO.studySubtypeCode"/>
    </td>
</tr>
<tr>
    <td  scope="row" class="label">
        <label>
            <fmt:message key="submit.trial.studyModelCode"/>
        </label>
    </td>
    <td>
        <s:property value="trialDTO.studyModelCode"/>
    </td>
</tr>

<s:if test="trialDTO.studyModelCode == 'Other'">
	<tr>
	    <td  scope="row" class="label">
	        <label>
	            <fmt:message key="submit.trial.studyModelOtherText"/>
	        </label>
	    </td>
	    <td>
	        <s:property value="trialDTO.studyModelOtherText"/>
	    </td>
	</tr>
</s:if>

<tr>
    <td  scope="row" class="label">
        <label>
            <fmt:message key="submit.trial.timePerspectiveCode"/>
        </label>
    </td>
    <td>
        <s:property value="trialDTO.timePerspectiveCode"/>
    </td>
</tr>

<s:if test="trialDTO.timePerspectiveCode == 'Other'">
    <tr>
        <td  scope="row" class="label">
            <label>
                <fmt:message key="submit.trial.timePerspectiveOtherText"/>
            </label>
        </td>
        <td>
            <s:property value="trialDTO.timePerspectiveOtherText"/>
        </td>
    </tr>
</s:if>


</s:if>
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>