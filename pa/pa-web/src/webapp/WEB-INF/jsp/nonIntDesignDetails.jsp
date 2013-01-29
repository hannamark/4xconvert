<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="osdesign.details.title"/></title>
<s:head />
</head>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
<SCRIPT LANGUAGE="JavaScript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    toggleFields();  
    displayPrimaryPurposeOtherText();
}

function toggleFields(){
	var input="webDTO.studyModelCode";
  	var inputElement = document.forms[0].elements[input];
	
   		if (inputElement.options[inputElement.selectedIndex].value == "Other")
		{
   			document.getElementById("studyModelOtherText").style.display = "";
   		}else
   		{
   			document.getElementById("studyModelOtherText").style.display = "none";
   		}
	var input2="webDTO.timePerspectiveCode";
	var inputElement2 = document.forms[0].elements[input2];
		if (inputElement2.options[inputElement2.selectedIndex].value == "Other")
		{
   			document.getElementById("timePerspectiveOtherText").style.display = "";
   		}else
   		{
   			document.getElementById("timePerspectiveOtherText").style.display = "none";
   		}
	}

function handleAction(){
 document.forms[0].action="noninterventionalStudyDesignupdateDesign.action";
 document.forms[0].submit(); 
} 


function changeStudyType() {
    displayWaitPanel();
    document.forms[0].action="noninterventionalStudyDesignchangeStudyType.action";
    document.forms[0].submit(); 
}

function tooltip() {
		BubbleTips.activateTipOn("acronym");
		BubbleTips.activateTipOn("dfn"); 
}

function displayPrimaryPurposeOtherText(){
	   if ($('webDTO.primaryPurposeCode').value == 'Other') {
	        $('purposeOtherTextDiv').show();
	   } else {
	        $('purposeOtherTextDiv').hide();
	  }
}

</SCRIPT>
<body>
<h1><fmt:message key="osdesign.details.title"/></h1>
<c:set var="topic" scope="request" value="abstractdesignnoninterventional"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
<div class="box">
<pa:sucessMessage/>
   <pa:failureMessage/>
<s:form>
 <s:token/>
<pa:studyUniqueToken/>
<s:actionerror/>
<h2><fmt:message key="osdesign.details.title"/></h2>
<table class="form">

    <tr>
        <td  scope="row" class="label"><label>
            <fmt:message key="osdesign.details.study.type"/><span class="required">*</span></label></td>        
        <td>
          <s:select name="webDTO.studyType" list="#{'Interventional':'Interventional','NonInterventional':'Non-Interventional'}"  
                    onchange="changeStudyType();"
                   value="webDTO.studyType" cssStyle="width:200px"/>
          <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.studyType</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>

 <tr>
        <td style="padding: 0 0 0 0;"></td>
        <td style="padding: 0 0 0 0;">
            <p class="info" style="margin-top: 0px;">
                Changing Study Type will immediately reload this page in order to display
                data fields that apply to Interventional Trials. 
            </p>
        </td>
    </tr>

    <tr>
        <td  scope="row" class="label"><label>
            <fmt:message key="osdesign.details.study.subtype"/><span class="required">*</span></label></td>
        <s:set name="StudySubtypeCodeValues" value="@gov.nih.nci.pa.enums.StudySubtypeCode@getDisplayNames()" />
        <td>
          <s:select headerKey="" headerValue="" name="webDTO.studySubtypeCode" list="#StudySubtypeCodeValues"  
                   value="webDTO.studySubtypeCode" cssStyle="width:200px" onchange="toggleFields()"/>
          <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.studySubtypeCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>



    <tr>
        <td  scope="row" class="label"><label>
            <fmt:message key="isdesign.details.primary.purpose"/><span class="required">*</span></label></td>
        <s:set name="primaryPurposeCodeValues" value="@gov.nih.nci.pa.lov.PrimaryPurposeCode@getDisplayNames()" />
        <td>
          <s:select headerKey="" headerValue="" name="webDTO.primaryPurposeCode" id="webDTO.primaryPurposeCode" list="#primaryPurposeCodeValues"  
                   value="webDTO.primaryPurposeCode" cssStyle="width:150px" onchange="toggleFields();displayPrimaryPurposeOtherText()"/>
          <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.primaryPurposeCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    <tr id="purposeOtherTextDiv" style="display:'none'">
         <td scope="row" class="label">
            <label><fmt:message key="isdesign.details.primary.purpose.otherText"/></label>
         </td>
         <td>
               <s:textarea name="webDTO.primaryPurposeOtherText"  cols="50" rows="2" maxlength="200" cssClass="charcounter"/><br/>
               <span class="info">Required if Purpose equals &#39;Other&#39;</span>
               <span class="formErrorMsg"> 
               <s:fielderror>
               <s:param>webDTO.primaryPurposeOtherText</s:param>
               </s:fielderror>                            
               </span>
         </td>
      </tr>


	<tr>
	 	<td  scope="row" class="label"><label>
	 		<fmt:message key="osdesign.details.study.model"/><span class="required">*</span></label></td>
     	<s:set name="studyModelCodeValues" value="@gov.nih.nci.pa.enums.StudyModelCode@getDisplayNames()" />
        <td>
          <s:select headerKey="" headerValue="" name="webDTO.studyModelCode" list="#studyModelCodeValues"  
                   value="webDTO.studyModelCode" cssStyle="width:200px" onchange="toggleFields();"/>
          <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.studyModelCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    <tr id="studyModelOtherText">
		<td   scope="row" class="label"><label>
	 		<fmt:message key="osdesign.details.study.model.other"/><span class="required">*</span></label></td>
		<td>
			<s:textarea name="webDTO.studyModelOtherText" cssStyle="width:150px" rows="2" maxlength="200" cssClass="charcounter"/>
			<span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.studyModelOtherText</s:param>
             </s:fielderror>                            
          </span>
		</td>
	</tr>
    <tr>
        <td scope="row" class="label"><label for="studyPhase">
        	 <fmt:message key="osdesign.details.time.perspective"/><span class="required">*</span></label> </td>
        <s:set name="timePerspectiveCodeValues" value="@gov.nih.nci.pa.enums.TimePerspectiveCode@getDisplayNames()" />
        <td>
        	<s:select headerKey="" headerValue="" name="webDTO.timePerspectiveCode" list="#timePerspectiveCodeValues" 
				value="webDTO.timePerspectiveCode" cssStyle="width:126px" onchange="toggleFields()" />
			<span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.timePerspectiveCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    <tr id="timePerspectiveOtherText">
		<td   scope="row" class="label"><label>
	 		<fmt:message key="osdesign.details.time.perspective.comment"/><span class="required">*</span></label></td>
		<td>
			<s:textarea name="webDTO.timePerspectiveOtherText" rows="2" cssStyle="width:150px"  maxlength="200" cssClass="charcounter"/>
			<span class="formErrorMsg"> 
				<s:fielderror>
               		<s:param>webDTO.timePerspectiveOtherText</s:param>
             	</s:fielderror>
             </span> 
		</td>
	</tr>	 
    <tr>
		<td scope="row" class="label"><label>
	 		<fmt:message key="osdesign.details.specimen.retention"/></label></td>
		<s:set name="biospecimenRetentionCodeValues" value="@gov.nih.nci.pa.enums.BiospecimenRetentionCode@getDisplayNames()" />
        <td>
           <s:select headerKey="" headerValue="" name="webDTO.biospecimenRetentionCode" list="#biospecimenRetentionCodeValues"  value="webDTO.biospecimenRetentionCode" cssStyle="width:150px" />
           <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.biospecimenRetentionCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
	</tr>
	<tr>
		<td scope="row" class="label"><label>
	 		<fmt:message key="osdesign.details.specimen.description"/></label></td>
		<td>
         	<s:textfield name="webDTO.biospecimenDescription" maxlength="800" cssStyle="width:206px"/>
         	<span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.biospecimenDescription</s:param>
             </s:fielderror>                            
          </span>
        </td>
	</tr>	
    <tr>
		<td scope="row" class="label"><label>
	 		<fmt:message key="osdesign.details.groups"/><span class="required">*</span></label></td>
		<td>
           <s:textfield name="webDTO.numberOfGroups"  maxlength="5" cssStyle="width:50px" />
           <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.numberOfGroups</s:param>
             </s:fielderror>                            
          </span>
        </td>
	</tr>
	<tr>
		<td scope="row" class="label"><label>
	 		<fmt:message key="isdesign.details.target.enrollment"/><span class="required">*</span></label></td>
		<td>
         	<s:textfield name="webDTO.minimumTargetAccrualNumber" maxlength="6" cssStyle="width:50px"/>
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
            <pa:scientificAbstractorDisplayWhenCheckedOut>
                <li><s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>
            </pa:scientificAbstractorDisplayWhenCheckedOut>         
		</ul>	
	</del>
</div>
</s:form>
</div>
</body>
</html>
