<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<SCRIPT LANGUAGE="JavaScript">
function displayPhaseAdditonalCode(){
        var valSelect  = document.getElementById('trialDTO.phaseCode').value;
        if (valSelect == 'NA') {
              document.getElementById('phaseOtherDiv').style.display='';
        } else {
            document.getElementById('phaseOtherDiv').style.display='none';
        }
    }
</SCRIPT>
          <tr>
                <td  scope="row" class="label">
                    <reg-web:displayTooltip tooltip="tooltip.phase">
                        <label for="submitTrial_protocolWebDTO_trialPhase"> <fmt:message key="submit.trial.phase"/><span class="required">*</span></label>
                    </reg-web:displayTooltip>
                </td>
                <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
                <td>
                    <s:select headerKey="" headerValue="--Select--" name="trialDTO.phaseCode" id ="trialDTO.phaseCode" 
                    list="#phaseCodeValues" cssStyle="width:206px" value="trialDTO.phaseCode" onchange="displayPhaseAdditonalCode();"/>
                    <span class="formErrorMsg">
                        <s:fielderror>
                        <s:param>trialDTO.phaseCode</s:param>
                       </s:fielderror>
                     </span>
                </td>
          </tr>
          <tr id ="phaseOtherDiv" style="display:'none'">
                <td scope="row" class="label">
                    <label for="submitTrial_protocolWebDTO_otherPhaseText"> <fmt:message key="submit.trial.otherPhaseText"/></label>
                </td>
                <td>
                    <s:set name="phaseAdditionlQualiefierCodeValues" value="@gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode@getDisplayNames()" />
                    <s:select headerKey="" headerValue="" name="trialDTO.phaseAdditionalQualifier" list="#phaseAdditionlQualiefierCodeValues"
                    value="trialDTO.phaseAdditionalQualifier" cssStyle="width:120px" />
                </td>
          </tr>