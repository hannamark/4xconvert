<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<tr>
                <td  scope="row" class="label">
                    <reg-web:displayTooltip tooltip="tooltip.primary_purpose">
                        <label for="submitTrial_protocolWebDTO_trialPurpose"><fmt:message key="submit.trial.purpose"/><span class="required">*</span></label>
                    </reg-web:displayTooltip>
                </td>
                    <s:set name="typeCodeValues" value="@gov.nih.nci.pa.enums.PrimaryPurposeCode@getDisplayNames()" />
                <td>
                    <s:select headerKey="" headerValue="--Select--" id ="trialDTO.primaryPurposeCode" name="trialDTO.primaryPurposeCode" list="#typeCodeValues"  cssStyle="width:206px" 
                    value="trialDTO.primaryPurposeCode" onchange="displayPrimaryPurposeOtherCode();"/>
                     <span class="formErrorMsg">
                        <s:fielderror>
                        <s:param>trialDTO.primaryPurposeCode</s:param>
                       </s:fielderror>
                     </span>
                </td>
          </tr>
            <tr id="purposeOtherTextDiv" style="display:'none'">
                <td scope="row" class="label">
                    <reg-web:displayTooltip tooltip="tooltip.purpose_comment">
                        <label for="submitTrial_protocolWebDTO_otherPurposeText">
                         <fmt:message key="submit.trial.otherPurposeText"/></label>
                    </reg-web:displayTooltip>
                </td>
                 <td>
                    <s:textarea id="trialDTO.primaryPurposeOtherText" name="trialDTO.primaryPurposeOtherText"  cols="50" rows="2" />
                    <span class="info">Required if Purpose equals &#39;Other&#39;</span>
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.primaryPurposeOtherText</s:param>
                       </s:fielderror>                            
                     </span>
                </td>
          </tr>
<SCRIPT LANGUAGE="JavaScript">
displayPrimaryPurposeOtherCode();
function displayPrimaryPurposeOtherCode(){
    if ($('trialDTO.primaryPurposeCode').value == 'Other') {
        $('purposeOtherTextDiv').show();
        document.getElementById('trialDTO.primaryPurposeOtherText').disabled = false;
    } else {
        $('purposeOtherTextDiv').hide();
        document.getElementById('trialDTO.primaryPurposeOtherText').disabled = true;
    }
}
</SCRIPT>

          