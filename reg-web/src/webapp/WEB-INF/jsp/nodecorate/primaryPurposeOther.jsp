<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<tr>
<s:hidden id="trialDTO.primaryPurposeAdditionalQualifierCode" name="trialDTO.primaryPurposeAdditionalQualifierCode"/>
                <td  scope="row" class="label">
                    <reg-web:displayTooltip tooltip="tooltip.primary_purpose">
                        <label for="trialDTO.primaryPurposeCode"><fmt:message key="submit.trial.purpose"/><span class="required">*</span></label>
                    </reg-web:displayTooltip>
                </td>
                    <s:set name="interventionalTypeCodeValues" 
                        value="@gov.nih.nci.pa.lov.PrimaryPurposeCode@getDisplayNames(@gov.nih.nci.pa.enums.StudyTypeCode@INTERVENTIONAL)" />
                    <s:set name="noninterventionalTypeCodeValues" 
                        value="@gov.nih.nci.pa.lov.PrimaryPurposeCode@getDisplayNames(@gov.nih.nci.pa.enums.StudyTypeCode@NON_INTERVENTIONAL)" />
                <td>
                    <s:select headerKey="" headerValue="--Select--" id ="trialDTO.primaryPurposeCode" name="trialDTO.primaryPurposeCode" list="#interventionalTypeCodeValues"  cssStyle="width:206px" 
                        cssClass="interventional interventional-input-ctr"
                        value="trialDTO.primaryPurposeCode" onchange="displayPrimaryPurposeOtherCode(this);"/>
                    <s:select headerKey="" headerValue="--Select--" id ="trialDTO.primaryPurposeCode2" name="trialDTO.primaryPurposeCode" list="#noninterventionalTypeCodeValues"  cssStyle="width:206px; display: none;"
                        cssClass="non-interventional non-interventional-input-ctr" 
                        value="trialDTO.primaryPurposeCode" onchange="displayPrimaryPurposeOtherCode(this);" disabled="true"/>                                        
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
                        <label for="trialDTO.primaryPurposeOtherText">
                         <fmt:message key="submit.trial.otherPurposeText"/></label>
                    </reg-web:displayTooltip>
                </td>
                 <td>
                    <s:textarea id="trialDTO.primaryPurposeOtherText" name="trialDTO.primaryPurposeOtherText"  cols="50" rows="2"
                        maxlength="200" cssClass="charcounter" /><br/>
                    <span class="info">Required if Purpose equals &#39;Other&#39;</span>
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.primaryPurposeOtherText</s:param>
                       </s:fielderror>                            
                     </span>
                </td>
          </tr>
          
          <tr class="interventional">        
                <td  scope="row" class="label">                    
                   <label for="trialDTO.secondaryPurposes"><fmt:message key="submit.trial.secondaryPurpose"/></label>                    
                </td>
                <s:set name="typeCodeValues" value="@gov.nih.nci.pa.service.util.PAServiceUtils@getSecondaryPurposeList()" />
                <td>
                    <s:select id ="trialDTO.secondaryPurposes" name="trialDTO.secondaryPurposes" list="#typeCodeValues"  cssStyle="width:206px"
                        headerKey="" headerValue="--Select--" onchange="displaySecondaryPurposeOtherCode(this);"
                        value="trialDTO.secondaryPurposes"/>                      
                </td>
          </tr>
          
          <tr id="secondaryPurposeOtherTextDiv" style="display:'none'">
                <td scope="row" class="label">                    
                    <label for="trialDTO.secondaryPurposeOtherText">
                        <fmt:message key="submit.trial.secOtherPurposeText"/>
                    </label>                    
                </td>
                <td>
                    <s:textarea id="trialDTO.secondaryPurposeOtherText" name="trialDTO.secondaryPurposeOtherText"  cols="50" rows="2"
                        maxlength="1000" cssClass="charcounter" /><br/>
                     <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.secondaryPurposeOtherText</s:param>
                       </s:fielderror>                            
                     </span>
                </td>
          </tr>
          
          
		<tr class="non-interventional">        
		      <td  scope="row" class="label">                    
		         <label for="trialDTO.studyModelCode"><fmt:message key="submit.trial.studyModelCode"/><span class="required">*</span></label>                    
		     </td>
		     <s:set name="typeCodeValues" value="@gov.nih.nci.pa.enums.StudyModelCode@getDisplayNames()" />
		     <td>
		         <s:select headerKey="" headerValue="--Select--" id ="trialDTO.studyModelCode" name="trialDTO.studyModelCode" list="#typeCodeValues"  cssStyle="width:206px" 
		                   onchange="displayStudyModelOtherTextDiv();" value="trialDTO.studyModelCode"/>
		          <span class="formErrorMsg">
		             <s:fielderror>
		             <s:param>trialDTO.studyModelCode</s:param>
		            </s:fielderror>
		           </span>
		      </td>
		</tr>
		
        <tr id="studyModelOtherTextDiv" style="display:none;">
            <td scope="row" class="label non-interventional">                
                <label for="trialDTO.studyModelOtherText">
                    <fmt:message key="submit.trial.studyModelOtherText"/></label>                
            </td>
             <td class="non-interventional">
                <s:textarea id="trialDTO.studyModelOtherText" name="trialDTO.studyModelOtherText"  cols="50" rows="2" 
                    maxlength="200" cssClass="charcounter" /><br/>
                <span class="info">Required if Study Model equals &#39;Other&#39;</span>
                <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>trialDTO.studyModelOtherText</s:param>
                   </s:fielderror>                            
                 </span>
            </td>
      </tr>
		
      <tr class="non-interventional">        
            <td  scope="row" class="label">                    
               <label for="trialDTO.timePerspectiveCode"><fmt:message key="submit.trial.timePerspectiveCode"/><span class="required">*</span></label>                    
           </td>
           <s:set name="typeCodeValues" value="@gov.nih.nci.pa.enums.TimePerspectiveCode@getDisplayNames()" />
           <td>
               <s:select headerKey="" headerValue="--Select--" id ="trialDTO.timePerspectiveCode" 
                    name="trialDTO.timePerspectiveCode" list="#typeCodeValues"  cssStyle="width:206px"
                    onchange="displayTimePerspectiveOtherTextDiv();" 
                         value="trialDTO.timePerspectiveCode"/>
                <span class="formErrorMsg">
                   <s:fielderror>
                   <s:param>trialDTO.timePerspectiveCode</s:param>
                  </s:fielderror>
                 </span>
            </td>
      </tr>
      
        <tr id="timePerspectiveOtherTextDiv" style="display:none;">
            <td scope="row" class="label non-interventional">                
                <label for="trialDTO.timePerspectiveOtherText">
                    <fmt:message key="submit.trial.timePerspectiveOtherText"/></label>                
            </td>
             <td class="non-interventional">
                <s:textarea id="trialDTO.timePerspectiveOtherText" name="trialDTO.timePerspectiveOtherText"  cols="50" rows="2" 
                    maxlength="200" cssClass="charcounter" /><br/>
                <span class="info">Required if Time Perspective equals &#39;Other&#39;</span>
                <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>trialDTO.timePerspectiveOtherText</s:param>
                   </s:fielderror>                            
                 </span>
            </td>
      </tr>
      
          
          
<SCRIPT LANGUAGE="JavaScript">

displayPrimaryPurposeOtherCode(null);
displaySecondaryPurposeOtherCode($('trialDTO.secondaryPurposes'));

function displayPrimaryPurposeOtherCode(el) {
	if (el==null) {
		el = $('trialDTO.primaryPurposeCode');
		if (el.disabled==true) {
			el = $('trialDTO.primaryPurposeCode2');
		}
	}
    if (el.value == 'Other') {
        $('purposeOtherTextDiv').show();
        document.getElementById('trialDTO.primaryPurposeOtherText').disabled = false;
        document.getElementById('trialDTO.primaryPurposeAdditionalQualifierCode').value = 'Other';
    } else {
        $('purposeOtherTextDiv').hide();
        document.getElementById('trialDTO.primaryPurposeOtherText').disabled = true;
        document.getElementById('trialDTO.primaryPurposeAdditionalQualifierCode').value = null;
    }
}

function displaySecondaryPurposeOtherCode(el) { 
    if (el.value == 'Other') {
        $('secondaryPurposeOtherTextDiv').show();
        document.getElementById('trialDTO.secondaryPurposeOtherText').disabled = false;        
    } else {
        $('secondaryPurposeOtherTextDiv').hide();
        document.getElementById('trialDTO.secondaryPurposeOtherText').disabled = true;        
    }
}

displayStudyModelOtherTextDiv();
function displayStudyModelOtherTextDiv() {
    if ($('trialDTO.studyModelCode').value == 'Other') {
        $('studyModelOtherTextDiv').show();
        document.getElementById('trialDTO.studyModelOtherText').disabled = false;        
    } else {
        $('studyModelOtherTextDiv').hide();
        document.getElementById('trialDTO.studyModelOtherText').disabled = true;        
    }	
}

displayTimePerspectiveOtherTextDiv();
function displayTimePerspectiveOtherTextDiv() {
    if ($('trialDTO.timePerspectiveCode').value == 'Other') {
        $('timePerspectiveOtherTextDiv').show();
        document.getElementById('trialDTO.timePerspectiveOtherText').disabled = false;        
    } else {
        $('timePerspectiveOtherTextDiv').hide();
        document.getElementById('trialDTO.timePerspectiveOtherText').disabled = true;        
    }   
}

</SCRIPT>

          