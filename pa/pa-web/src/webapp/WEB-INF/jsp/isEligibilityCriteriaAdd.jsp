<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="isdesign.eligibilitycriteria.webtitle"/></title>
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

 <h1><fmt:message key="isdesign.eligibilitycriteria.title"/></h1>
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
					<td scope="row"  class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
						<fmt:message key="isdesign.eligibilitycriteria.eligibilitycriteriatype"/> </dfn><span class="required">*</span></label>
					</td>
					<td class="value">
						<s:select name="webDTO.inclusionIndicator" list="#{' ':' ', 'false':'Exclusion', 'true':'Inclusion'}" cssStyle="width:106px"/>
						<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.inclusionIndicator</s:param>
                               </s:fielderror>                            
                         </span>
					</td>
				</tr> 
				<tr>
				<th colspan="2"><fmt:message key="isdesign.eligibilitycriteria.buildDescription"/></th>
				</tr>
				<tr>
					<td scope="row"  class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
						<fmt:message key="isdesign.eligibilitycriteria.eligibilitycriteriadescription"/> </dfn></label>
					</td>
					<td class="value">
						<s:textarea name="webDTO.textDescription" rows="4" cssStyle="width:400px" onblur="activate()" />
					</td>
				</tr>  
				<tr>
				<th colspan="2"><fmt:message key="isdesign.eligibilitycriteria.buildCriterion"/></th>
				</tr>
    			<tr>
					<td scope="row"  class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
						<fmt:message key="isdesign.eligibilitycriteria.eligibilitycriterianame"/> </dfn></label>
					</td>
					<td class="value">
						<s:textfield name="webDTO.criterionName" maxlength="30" cssStyle="width:200px" />
					</td>
				</tr>
				<tr>
                     <td scope="row" class="label">
                     <label for="fileName"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
                            <fmt:message key="isdesign.eligibilitycriteria.operator"/></dfn>
                     </label>
                    </td>
                    <td class="value">
                        <s:textfield name="webDTO.operator" maxlength="6" cssStyle="width:80px" />
                      </td>         
                </tr> 
                <tr>
                     <td scope="row" class="label">
                     	<label for="typeCode"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
                            <fmt:message key="isdesign.eligibilitycriteria.value"/></dfn>
                     	</label>
                    </td>
                    <td class="value">
					 	<s:textfield name="webDTO.value" maxlength="12" cssStyle="width:100px" />
                      </td>
                 </tr>
                 <tr>
                      <td scope="row" class="label">
                     <label for="typeCode"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
                            <fmt:message key="isdesign.eligibilitycriteria.unit"/></dfn>
                     </label>
                   </td>
                    <td class="value">
    				<s:set name="unitsValues" value="@gov.nih.nci.pa.enums.UnitsCode@getDisplayNames()" />
                    
                    <s:select headerKey="" headerValue="" name="webDTO.unit" list="#unitsValues" cssStyle="width:106px" /> 
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
