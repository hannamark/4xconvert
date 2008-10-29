<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="isdesign.details.title"/></title>
<s:head />
</head>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
<SCRIPT LANGUAGE="JavaScript">
window.onload=activate;
function activate(){
	var input="webDTO.primaryPurposeCode";
  	var inputElement = document.forms[0].elements[input];
	
   		if (inputElement.options[inputElement.selectedIndex].value == "Other")
		{
   			document.getElementById("primaryPurposeOtherText").style.display = "inline";
   		}else
   		{
   			document.getElementById("primaryPurposeOtherText").style.display = "none";
   		}
	var input2="webDTO.blindingSchemaCode";
	var inputElement2 = document.forms[0].elements[input2];
		if ((inputElement2.options[inputElement2.selectedIndex].value == "Single Blind")
			|| (inputElement2.options[inputElement2.selectedIndex].value == "Double Blind"))
		{
   			document.getElementById("blindingRoleCode").style.display = "inline";
   		}else
   		{
   			document.getElementById("blindingRoleCode").style.display = "none";
   		}
	}
function showAlert() {
	var input="webDTO.phaseCode";
  	var inputElement = document.forms[0].elements[input];
	
   		if (inputElement.options[inputElement.selectedIndex].value == "Other")
		{
			alert("Please select a different Trial Phase");
		}
}
function handleAction(){
 document.forms[0].action="interventionalStudyDesignupdate.action";
 document.forms[0].submit(); 
} 
function tooltip() {
		BubbleTips.activateTipOn("acronym");
		BubbleTips.activateTipOn("dfn"); 
	}
</SCRIPT>
<body>
<h1><fmt:message key="isdesign.details.title"/></h1>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
<div class="box">
<pa:sucessMessage/>
   <pa:failureMessage/>
<s:form>
<s:actionerror/>
<h2><fmt:message key="isdesign.details.title"/></h2>
<table class="form">
	<tr>
	 	<td  scope="row" class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
	 		<fmt:message key="isdesign.details.primary.purpose"/></dfn><span class="required">*</span></label></td>
     	<s:set name="primaryPurposeCodeValues" value="@gov.nih.nci.pa.enums.PrimaryPurposeCode@getDisplayNames()" />
        <td>
          <s:select headerKey="" headerValue="All" name="webDTO.primaryPurposeCode" list="#primaryPurposeCodeValues"  
                   value="webDTO.primaryPurposeCode" cssStyle="width:206px" onchange="activate()"/>
          <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.primaryPurposeCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    <tr id="primaryPurposeOtherText">
		<td   scope="row" class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
	 		<fmt:message key="isdesign.details.primary.purpose.other"/></dfn></label></td>
		<td>
			<s:textarea name="webDTO.primaryPurposeOtherText" cssStyle="width:206px" rows="2"/>
			<span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.primaryPurposeOtherText</s:param>
             </s:fielderror>                            
          </span>
		</td>
	</tr>
    <tr>
        <td scope="row" class="label"><label for="studyPhase"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
        	 <fmt:message key="studyProtocol.studyPhase"/></dfn><span class="required">*</span></label> </td>
        <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
        <td>
        	<s:select headerKey="" headerValue="All" name="webDTO.phaseCode" list="#phaseCodeValues" 
				value="webDTO.phaseCode" cssStyle="width:206px" onchange="showAlert()" />
			<span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.phaseCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    <tr>
		<td   scope="row" class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
	 		<fmt:message key="isdesign.details.phase.comment"/></dfn></label></td>
		<td>
			<s:textfield name="webDTO.phaseOtherText" maxlength="200" cssStyle="width:206px" readonly="true"/>
		</td>
	</tr>	 
    <tr>
		<td scope="row" class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
	 		<fmt:message key="isdesign.details.intervention.model"/></dfn><span class="required">*</span></label></td>
		<s:set name="designConfigurationCodeValues" value="@gov.nih.nci.pa.enums.DesignConfigurationCode@getDisplayNames()" />
        <td>
           <s:select headerKey="" headerValue="All" name="webDTO.designConfigurationCode" list="#designConfigurationCodeValues"  value="webDTO.designConfigurationCode" cssStyle="width:206px" />
           <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.designConfigurationCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
	</tr>
	<tr>
		<td scope="row" class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
	 		<fmt:message key="isdesign.details.no.arms"/></dfn><span class="required">*</span></label></td>
		<td>
         	<s:textfield name="webDTO.numberOfInterventionGroups" maxlength="200" cssStyle="width:206px"/>
         	<span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.numberOfInterventionGroups</s:param>
             </s:fielderror>                            
          </span>
        </td>
	</tr>
    <tr>
		<td scope="row" class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
	 		<fmt:message key="isdesign.details.masking"/></dfn><span class="required">*</span></label></td>
		<s:set name="blindingSchemaCodeValues" value="@gov.nih.nci.pa.enums.BlindingSchemaCode@getDisplayNames()" />
        <td>
           <s:select headerKey="" headerValue="All" name="webDTO.blindingSchemaCode" list="#blindingSchemaCodeValues"  
	           value="webDTO.blindingSchemaCode" cssStyle="width:206px" onchange="activate()" />
	       <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.blindingSchemaCode</s:param>
             </s:fielderror>                            
          </span>
         </td>
	</tr>
	<tr id="blindingRoleCode">
		<td   scope="row" class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
	 		<fmt:message key="isdesign.details.masking.role"/></dfn><span class="required">*</span></label></td>
		<s:set name="blindingRoleCodeValues" value="@gov.nih.nci.pa.enums.BlindingRoleCode@getDisplayNames()" />
        <td>
           <s:select headerKey="" headerValue="All" name="webDTO.blindingRoleCode" list="#blindingRoleCodeValues"  value="webDTO.blindingRoleCode" cssStyle="width:206px" />           
         </td>
	</tr>
	<tr> 
        <td scope="row" class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
	 		<fmt:message key="isdesign.details.allocation"/></dfn><span class="required">*</span></label> </td>
        <s:set name="allocationCodeValues" value="@gov.nih.nci.pa.enums.AllocationCode@getDisplayNames()" />
        <td>
        	<s:select headerKey="" headerValue="All" name="webDTO.allocationCode" list="#allocationCodeValues"  value="webDTO.allocationCode" cssStyle="width:206px" />
        	<span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.allocationCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    <tr>
		<td scope="row" class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
	 		<fmt:message key="isdesign.details.trial.classification"/></dfn><span class="required">*</span></label></td>
		<s:set name="studyClassificationCodeValues" value="@gov.nih.nci.pa.enums.StudyClassificationCode@getDisplayNames()" />
        <td>
           <s:select headerKey="" headerValue="All" name="webDTO.studyClassificationCode" list="#studyClassificationCodeValues"  value="webDTO.studyClassificationCode" cssStyle="width:206px" />
           <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.studyClassificationCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
	</tr>
	<tr>
		<td scope="row" class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
	 		<fmt:message key="isdesign.details.target.enrollment"/></dfn><span class="required">*</span></label></td>
		<td>
         	<s:textfield name="webDTO.maximumTargetAccrualNumber" maxlength="200" cssStyle="width:206px"/>
         	<span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.maximumTargetAccrualNumber</s:param>
             </s:fielderror>                            
          </span>
        </td>
	</tr>
</table>
	
<div class="actionsrow">
	<del class="btnwrapper">
		<ul class="btnrow">			
			<li><s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>			
		</ul>	
	</del>
</div>
</s:form>
</div>
</body>
</html>
