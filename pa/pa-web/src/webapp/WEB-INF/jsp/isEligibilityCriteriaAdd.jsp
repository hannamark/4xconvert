<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><c:choose>
     <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'ObservationalStudyProtocol'}">
     <fmt:message key="osdesign.eligibilitycriteria.webtitle"/>
     </c:when>
     <c:otherwise><fmt:message key="isdesign.eligibilitycriteria.webtitle"/></c:otherwise></c:choose> </title>
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
</head>
<SCRIPT LANGUAGE="JavaScript">
window.onload=activate;
function handleAction(){
var page;
page=document.forms[0].page.value;
input_box=confirm("Click OK to save changes or Cancel to Abort.");
if (input_box==true){
	if (page == "Edit"){
 		document.forms[0].action="eligibilityCriteriaupdate.action";
 		document.forms[0].submit();  	
	} else {
 		document.forms[0].action="eligibilityCriteriacreate.action";
 		document.forms[0].submit();   
 	} 
 }
} 
function activate(){
	var input="webDTO.textDescription";
  	var inputElement = document.forms[0].elements[input];

	var criterionName="webDTO.criterionName";
  	var cnElement = document.forms[0].elements[criterionName];
	var operator="webDTO.operator";
  	var opElement = document.forms[0].elements[operator];
	var ageValue="webDTO.value";
  	var avElement = document.forms[0].elements[ageValue];
	var unit="webDTO.unit";
  	var uElement = document.forms[0].elements[unit];
	
   		if (inputElement.value != "")
		{
			cnElement.disabled = true;
			opElement.disabled = true;
			avElement.disabled = true;
			uElement.disabled = true;
   		}else
   		{
			cnElement.disabled = false;
			opElement.disabled = false;
			avElement.disabled = false;
			uElement.disabled = false;
   		}	
	}

function tooltip() {
BubbleTips.activateTipOn("acronym");
BubbleTips.activateTipOn("dfn"); 
}
</SCRIPT>
<body onload="setFocusToFirstControl();">
<c:set var="topic" scope="request" value="abstract_eligibility"/>
 <h1><c:choose>
     <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'ObservationalStudyProtocol'}">
     <fmt:message key="osdesign.eligibilitycriteria.webtitle"/>
     </c:when>
     <c:otherwise><fmt:message key="isdesign.eligibilitycriteria.webtitle"/></c:otherwise></c:choose> </h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form><s:actionerror/>    
    <h2><fmt:message key="isdesign.eligibilitycriteria.subtitle"/></h2>
    <input type="hidden" name="page" value="${page}" />
    <input type="hidden" name="id" value="${id}" />
    <table class="form">
    <tr>
					<td scope="row"  class="label"><label>
						<fmt:message key="isdesign.eligibilitycriteria.eligibilitycriteriatype"/></label>
					</td>
					<td class="value">
						<s:select name="webDTO.inclusionIndicator" list="#{' ':' ', 'Exclusion':'Exclusion', 'Inclusion':'Inclusion'}" cssStyle="width:106px"/>
						<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.inclusionIndicator</s:param>
                               </s:fielderror>                            
                         </span>
					</td>
				</tr> 
				<tr>
				<th colspan="2"><fmt:message key="isdesign.eligibilitycriteria.buildDescription"/><span class="required">*</span></th>				
				</tr>
				<tr>
						<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.mandatory</s:param>
                               </s:fielderror>                            
                         </span>
					<td scope="row"  class="label"><label>
						<fmt:message key="isdesign.eligibilitycriteria.eligibilitycriteriadescription"/>(Max 5,000 chars)</label>
					</td>
					<td class="value">
						<s:textarea name="webDTO.textDescription" rows="6" cssStyle="width:600px" onblur='activate();' />
						<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.TextDescription</s:param>
                               </s:fielderror>                            
                         </span>
					</td>
				</tr>  
				<tr>
				<th colspan="2"><fmt:message key="isdesign.eligibilitycriteria.buildCriterion"/></th>
				</tr>
    			<tr>
					<td scope="row"  class="label"><label>
						<fmt:message key="isdesign.eligibilitycriteria.eligibilitycriterianame"/></label>
					</td>
					<td class="value">
						<s:textfield name="webDTO.criterionName" maxlength="30" cssStyle="width:200px" />
					</td>
				</tr>
				<tr>
                     <td scope="row" class="label">
                     <label for="fileName">
                            <fmt:message key="isdesign.eligibilitycriteria.operator"/>
                     </label>
                    </td>
                    <td class="value">
                        <s:textfield name="webDTO.operator" maxlength="6" cssStyle="width:80px" />
                      </td>         
                </tr> 
                <tr>
                     <td scope="row" class="label">
                     	<label for="typeCode">
                            <fmt:message key="isdesign.eligibilitycriteria.value"/>
                     	</label>
                    </td>
                    <td class="value">
					 	<s:textfield name="webDTO.value" maxlength="12" cssStyle="width:100px" />
                      </td>
                 </tr>
                 <tr>
                      <td scope="row" class="label">
                     <label for="typeCode">
                            <fmt:message key="isdesign.eligibilitycriteria.unit"/>
                     </label>
                   </td>
                    <td class="value">
                    <s:textfield name="webDTO.unit" maxlength="12" cssStyle="width:100px" />
    				</td> 
                      <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.buldcriterion</s:param>
                               </s:fielderror>                            
                         </span>           
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
