<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<SCRIPT LANGUAGE="JavaScript">
function displayPrimaryPurposeOtherCode(){
        var valSelect  = document.getElementById('trialDTO.primaryPurposeCode').value;
        if (valSelect == 'Other') {
              document.getElementById('purposeOtherCodeDiv').style.display='';
        } else {
            document.getElementById('purposeOtherCodeDiv').style.display='none';
            document.getElementById('purposeOtherTextDiv').style.display='none';
        }
    }
function displayPrimaryPurposeOtherText(){
    var valSelect  = document.getElementById('trialDTO.primaryPurposeAdditionalQualifierCode').value;
    if (valSelect == 'Other') {
          document.getElementById('purposeOtherTextDiv').style.display='';
    } else {
        document.getElementById('purposeOtherTextDiv').style.display='none';
    }
}

</SCRIPT>

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
                    <tr id ="purposeOtherCodeDiv" style="display:'none'">
                
                <td scope="row" class="label">
                    <reg-web:displayTooltip tooltip="tooltip.purpose_comment">
                        <label for="submitTrial_protocolWebDTO_otherPurposeCode">
                         <fmt:message key="submit.trial.otherPurposeCode"/></label>
                    </reg-web:displayTooltip>
                </td>
                <td>
                    <s:set name="primaryPurposeAdditionlQualiefierCodeValues" value="@gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode@getDisplayNames()" />
                    <s:select headerKey="" headerValue="" name="trialDTO.primaryPurposeAdditionalQualifierCode" list="#primaryPurposeAdditionlQualiefierCodeValues"
                    value="trialDTO.primaryPurposeAdditionalQualifierCode" cssStyle="width:120px" id="trialDTO.primaryPurposeAdditionalQualifierCode" onchange="displayPrimaryPurposeOtherText();"/>
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.primaryPurposeAdditionalQualifierCode</s:param>
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
                    <s:textarea name="trialDTO.primaryPurposeOtherText"  cols="50" rows="2" />
                    <span class="info">Required if Purpose equals &#39;Other&#39;</span>
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.primaryPurposeOtherText</s:param>
                       </s:fielderror>                            
                     </span>
                </td>
          </tr>
