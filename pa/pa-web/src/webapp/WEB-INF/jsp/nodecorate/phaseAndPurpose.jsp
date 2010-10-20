<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<SCRIPT LANGUAGE="JavaScript">
function displayPhaseAdditonalCode(){
        var valSelect  = document.getElementById('gtdDTO.phaseCode').value;
        if (valSelect == 'NA') {
              document.getElementById('phaseOtherDiv').style.display='';
        } else {
            document.getElementById('phaseOtherDiv').style.display='none';
        }
    }

function displayPrimaryPurposeOtherCode() {
    var valSelect  = document.getElementById('gtdDTO.primaryPurposeCode').value;
    if (valSelect == 'Other') {
          document.getElementById('purposeOtherCodeDiv').style.display='';
    } else {
        document.getElementById('purposeOtherCodeDiv').style.display='none';
        document.getElementById('purposeOtherTextDiv').style.display='none';
    }
}

function displayPrimaryPurposeOtherText() {
   var valSelect  = document.getElementById('gtdDTO.primaryPurposeAdditionalQualifierCode').value;
   if (valSelect == 'Other') {
        document.getElementById('purposeOtherTextDiv').style.display='';
   } else {
    document.getElementById('purposeOtherTextDiv').style.display='none';
  }
}

</SCRIPT>
    
    <tr>
        <td scope="row" class="label"><label for="studyPhase">
                 <fmt:message key="studyProtocol.studyPhase"/><span class="required">*</span></label> </td>
        <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
        <td>
            <s:select headerKey="" headerValue="" name="gtdDTO.phaseCode" id="gtdDTO.phaseCode" list="#phaseCodeValues" 
                value="gtdDTO.phaseCode" cssStyle="width:120px" onchange="displayPhaseAdditonalCode()"/>
            <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>gtdDTO.phaseCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    <tr id ="phaseOtherDiv" style="display:'none'">
        <td   scope="row" class="label"><label><fmt:message key="isdesign.details.phase.comment"/></label></td>
        <td>
        <s:set name="phaseAdditionlQualiefierCodeValues" value="@gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode@getDisplayNames()" />
        <s:select headerKey="" headerValue="" name="gtdDTO.phaseAdditionalQualifierCode" list="#phaseAdditionlQualiefierCodeValues" 
                value="gtdDTO.phaseAdditionalQualifierCode" cssStyle="width:120px" />
        </td>
    </tr>
    <tr>
        <td  scope="row" class="label"><label>
            <fmt:message key="isdesign.details.primary.purpose"/><span class="required">*</span></label></td>
        <s:set name="primaryPurposeCodeValues" value="@gov.nih.nci.pa.enums.PrimaryPurposeCode@getDisplayNames()" />
        <td>
          <s:select headerKey="" headerValue="" name="gtdDTO.primaryPurposeCode" id="gtdDTO.primaryPurposeCode" list="#primaryPurposeCodeValues"  
               value="gtdDTO.primaryPurposeCode" cssStyle="width:150px"  onchange="displayPrimaryPurposeOtherCode();" />
          <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>gtdDTO.primaryPurposeCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    <tr id="purposeOtherCodeDiv" style="display:'none'">
        <td   scope="row" class="label"><label>
            <fmt:message key="isdesign.details.primary.purpose.otherCode"/></label></td>
        <td>
            <s:set name="primaryPurposeAdditionlQualiefierCodeValues" value="@gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode@getDisplayNames()" />
            <s:select headerKey="" headerValue="" name="gtdDTO.primaryPurposeAdditionalQualifierCode" id="gtdDTO.primaryPurposeAdditionalQualifierCode" 
            list="#primaryPurposeAdditionlQualiefierCodeValues" value="gtdDTO.primaryPurposeAdditionalQualifierCode" 
            cssStyle="width:120px" onchange="displayPrimaryPurposeOtherText();"/>
        </td>
    </tr>
      <tr id="purposeOtherTextDiv" style="display:'none'">
         <td scope="row" class="label">
            <label> <fmt:message key="isdesign.details.primary.purpose.otherText"/></label>
         </td>
         <td>
               <s:textarea name="gtdDTO.primaryPurposeOtherText"  cols="50" rows="2" />
               <span class="info">Required if Purpose equals &#39;Other&#39;</span>
               <span class="formErrorMsg"> 
               <s:fielderror>
               <s:param>gtdDTO.primaryPurposeOtherText</s:param>
               </s:fielderror>                            
               </span>
         </td>
      </tr>