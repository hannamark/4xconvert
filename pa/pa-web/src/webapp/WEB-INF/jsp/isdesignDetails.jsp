<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="isdesign.details.title"/></title>
<s:head />
</head>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
<SCRIPT LANGUAGE="JavaScript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    setFocusToFirstControl();
    activate();         
}

function activate(){
	var input="webDTO.primaryPurposeCode";
  	var inputElement = document.forms[0].elements[input];
	
	var input2="webDTO.blindingSchemaCode";
	var inputElement2 = document.forms[0].elements[input2];
		if ((inputElement2.options[inputElement2.selectedIndex].value == "Single Blind")
			|| (inputElement2.options[inputElement2.selectedIndex].value == "Double Blind"))
		{
   			document.getElementById("blindingRoleCode").style.display = "";
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
function checkValue() {
    var input="webDTO.minimumTargetAccrualNumber";
    var conf = true;
    var inputElement = document.forms[0].elements[input];
    
        if (inputElement.value == "0")
        {
            conf = confirm("Please confirm if Target Enrollment 0 is OK");
            if(conf != true) {
              inputElement.value='';
              inputElement.focus();
            }
            
        }
       return conf; 
}
function ChecksCount(name) {

var input2="webDTO.blindingSchemaCode";
var inputElement2 = document.forms[0].elements[input2];

		if (inputElement2.options[inputElement2.selectedIndex].value == "Single Blind") 
		{
			var maxchecked = 1;
			var count = 0;
			if(document.forms[0].subject.checked == true) { count++; }
			if(document.forms[0].investigator.checked == true) { count++; }
			if(document.forms[0].caregiver.checked == true) { count++; }
			if(document.forms[0].outcomesassessor.checked == true) { count++; }
			if(count > maxchecked) {
				eval('document.forms[0].' + name + '.checked = false');
				alert('Only ' + maxchecked + ' needs to be checked for Single Blind.');
			}
		}else
		{
			var maxchecked = 2;
			var count = 0;
			if(document.forms[0].subject.checked == true) { count++; }
			if(document.forms[0].investigator.checked == true) { count++; }
			if(document.forms[0].caregiver.checked == true) { count++; }
			if(document.forms[0].outcomesassessor.checked == true) { count++; }
			if(count > maxchecked) {
				eval('document.forms[0].' + name + '.checked = false');
				alert('Only ' + maxchecked + ' needs to be checked for Double Blind.');
			}
		}

}



function handleAction(){
 var conf = checkValue();
 if(conf == true) {
  document.forms[0].action="interventionalStudyDesignupdate.action";
  document.forms[0].submit();
 } 
} 
function tooltip() {
		BubbleTips.activateTipOn("acronym");
		BubbleTips.activateTipOn("dfn"); 
	}
  	
</SCRIPT>
<body>
<h1><fmt:message key="isdesign.details.title"/></h1>
<c:set var="topic" scope="request" value="abstract_design"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
<div class="box">
<pa:sucessMessage/>
   <pa:failureMessage/>
<s:form>
<s:actionerror/>
<h2><fmt:message key="isdesign.details.title"/></h2>
<table class="form">
	<tr>
	 	<td  scope="row" class="label"><label>
	 		<fmt:message key="isdesign.details.primary.purpose"/><span class="required">*</span></label></td>
     	<s:set name="primaryPurposeCodeValues" value="@gov.nih.nci.pa.enums.PrimaryPurposeCode@getDisplayNames()" />
        <td>
          <s:select headerKey="" headerValue="" name="webDTO.primaryPurposeCode" list="#primaryPurposeCodeValues"  
                   value="webDTO.primaryPurposeCode" cssStyle="width:150px" onchange="activate()"/>
          <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.primaryPurposeCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    <tr >
		<td   scope="row" class="label"><label>
	 		Required if Primary Purpose equals 'Other' </label></td>
		<td>
			<s:textarea name="webDTO.primaryPurposeOtherText" cssStyle="width:350px" rows="3" />(Max 200 chars)
			<span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.primaryPurposeOtherText</s:param>
             </s:fielderror>                            
          </span>
		</td>
	</tr>
    <tr>
        <td scope="row" class="label"><label for="studyPhase">
        	 <fmt:message key="studyProtocol.studyPhase"/><span class="required">*</span></label> </td>
        <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
        <td>
        	<s:select headerKey="" headerValue="" name="webDTO.phaseCode" list="#phaseCodeValues" 
				value="webDTO.phaseCode" cssStyle="width:100px"  />
			<span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.phaseCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    <tr>
		<td   scope="row" class="label"><label>
	 		Required if Phase equals 'Other' </label></td>
		<td>
			<s:textarea name="webDTO.phaseOtherText" rows="3" cssStyle="width:350px" />(Max 200 chars)
			<span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.phaseOtherText</s:param>
             </s:fielderror>                            
		</td>
	</tr>	
    <tr>
		<td scope="row" class="label"><label>
	 		<fmt:message key="isdesign.details.intervention.model"/><span class="required">*</span></label></td>
		<s:set name="designConfigurationCodeValues" value="@gov.nih.nci.pa.enums.DesignConfigurationCode@getDisplayNames()" />
        <td>
           <s:select headerKey="" headerValue="" name="webDTO.designConfigurationCode" list="#designConfigurationCodeValues"  value="webDTO.designConfigurationCode" cssStyle="width:150px" />
           <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.designConfigurationCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
	</tr>
	<tr>
		<td scope="row" class="label"><label>
	 		<fmt:message key="isdesign.details.no.arms"/><span class="required">*</span></label></td>
		<td>
         	<s:textfield name="webDTO.numberOfInterventionGroups" maxlength="3" cssStyle="width:25px"/>
         	<span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.numberOfInterventionGroups</s:param>
             </s:fielderror>                            
          </span>
        </td>
	</tr>
    <tr>
		<td scope="row" class="label"><label>
	 		<fmt:message key="isdesign.details.masking"/><span class="required">*</span></label></td>
		<s:set name="blindingSchemaCodeValues" value="@gov.nih.nci.pa.enums.BlindingSchemaCode@getDisplayNames()" />
        <td>
           <s:select headerKey="" headerValue="" name="webDTO.blindingSchemaCode" list="#blindingSchemaCodeValues"  
	           value="webDTO.blindingSchemaCode" cssStyle="width:120px" onchange="activate()" />
	       <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.blindingSchemaCode</s:param>
             </s:fielderror>                            
          </span>
         </td>
	</tr>
	<tr id="blindingRoleCode">
		<td   scope="row" class="label"><label>
	 		<fmt:message key="isdesign.details.masking.role"/><span class="required">*</span></label></td>
		<td>
           <s:checkbox name="subject" fieldValue="Subject"  value="%{subjectChecked}" />
           <label>Subject</label>
		   <s:checkbox name="investigator" fieldValue="Investigator"  value="%{investigatorChecked}" />
		   <label>Investigator</label>
		   <s:checkbox name="caregiver" fieldValue="Caregiver"  value="%{caregiverChecked}" />
		   <label>Caregiver</label>
		   <s:checkbox name="outcomesassessor" fieldValue="Outcomes Assessor"  value="%{outcomesAssessorChecked}" />
		   <label>Outcomes Assessor</label>          
         </td>
	</tr>
	<tr> 
        <td scope="row" class="label"><label>
	 		<fmt:message key="isdesign.details.allocation"/><span class="required">*</span></label> </td>
        <s:set name="allocationCodeValues" value="@gov.nih.nci.pa.enums.AllocationCode@getDisplayNames()" />
        <td>
        	<s:select headerKey="" headerValue="" name="webDTO.allocationCode" list="#allocationCodeValues"  value="webDTO.allocationCode" cssStyle="width:206px" />
        	<span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.allocationCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    <tr>
		<td scope="row" class="label"><label>
	 		<fmt:message key="isdesign.details.trial.classification"/></label></td>
		<s:set name="studyClassificationCodeValues" value="@gov.nih.nci.pa.enums.StudyClassificationCode@getDisplayNames()" />
        <td>
           <s:select headerKey="" headerValue="" name="webDTO.studyClassificationCode" list="#studyClassificationCodeValues"  value="webDTO.studyClassificationCode" cssStyle="width:206px" />
           <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.studyClassificationCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
	</tr>
	<tr>
		<td scope="row" class="label"><label>
	 		<fmt:message key="isdesign.details.target.enrollment"/><span class="required">*</span></label></td>
		<td>
         	<s:textfield name="webDTO.minimumTargetAccrualNumber" maxlength="6" cssStyle="width:50px" />
         	<span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.minimumTargetAccrualNumber</s:param>
             </s:fielderror>                            
          </span>
        </td>
	</tr>
</table>
	
<div class="actionsrow">
	<del class="btnwrapper">
		<ul class="btnrow">	
			<c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)
								|| (sessionScope.role == 'SuAbstractor')}">		
			<li><s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>
			</c:if>
			<li><a href="trialDescriptionquery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
			<li><a href="interventionalStudyDesignoutcomeQuery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>			
		</ul>	
	</del>
</div>
</s:form>
</div>
</body>
</html>
