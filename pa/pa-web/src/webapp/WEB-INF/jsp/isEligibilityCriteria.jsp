<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>
     <s:if test="${sessionScope.trialSummary.studyProtocolType  == 'ObservationalStudyProtocol'}">
     <fmt:message key="osdesign.eligibilitycriteria.webtitle"/>
     </s:if>
     <s:else><fmt:message key="isdesign.eligibilitycriteria.webtitle"/></s:else> 
     </title>   
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
</head>
<SCRIPT LANGUAGE="JavaScript">

function handleAction(){
 	document.forms[0].action="eligibilityCriteriasave.action";
 	document.forms[0].submit(); 
} 
function tooltip() {
BubbleTips.activateTipOn("acronym");
BubbleTips.activateTipOn("dfn"); 
}
function maxLength(field,maxChars)
 {
 var input=field;
  	var inputElement = document.forms[0].elements[input];  	
       if(inputElement.value.length >= maxChars) {
		  alert("more than " +maxChars + " chars");
       }
 } 
</SCRIPT>
<body onload="setFocusToFirstControl();">

 <h1><fmt:message key="isdesign.eligibilitycriteria.title"/></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form><s:actionerror/>    
    <h2><fmt:message key="isdesign.eligibilitycriteria.title"/></h2>
    <table class="form">
    <s:if test="${sessionScope.trialSummary.studyProtocolType  == 'ObservationalStudyProtocol'}">
                <tr>
                    <td scope="row"  class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
                        <fmt:message key="osdesign.eligibilitycriteria.trialPopulationDescription"/> </dfn><span class="required">*</span></label>
                     </td> <td class="value">               
                        <s:textarea name="studyPopulationDescription" rows="3" cssStyle="width:250px" onblur='return maxLength("studyPopulationDescription","800");' />
                        <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>studyPopulationDescription</s:param>
                               </s:fielderror>                            
                         </span>
                    </td>      
               </tr>
               <tr>
                    <td scope="row"  class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
                        <fmt:message key="osdesign.eligibilitycriteria.samplingMethod"/> </dfn><span class="required">*</span></label>
                    </td>   
                       <s:set name="samplingMethodValues" value="@gov.nih.nci.pa.enums.SamplingMethodCode@getDisplayNames()" />
					  <td class="value">
                        <s:select headerKey="" headerValue="" 
                                name="samplingMethodCode" 
                                list="#samplingMethodValues"  
                                cssStyle="width:185px" />
                        <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>samplingMethodCode</s:param>
                               </s:fielderror>                            
                         </span>
                    </td>
                </tr>
    </s:if>
    			<tr>
					<td scope="row"  class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
						<fmt:message key="isdesign.eligibilitycriteria.ahv"/> </dfn><span class="required">*</span></label>
					 </td> <td class="value">   
						<s:select name="acceptHealthyVolunteersIndicator" list="#{'':'','false':'No', 'true':'Yes'}" />
						<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>acceptHealthyVolunteersIndicator</s:param>
                               </s:fielderror>                            
                         </span>
					</td>
				</tr>
				<tr>
                     <td scope="row" class="label">
                     <label for="fileName"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
                            <fmt:message key="isdesign.eligibilitycriteria.eligibleGender"/></dfn><span class="required">*</span>
                     </label>
                    </td> 
                    <s:set name="genderValues" value="@gov.nih.nci.pa.enums.EligibleGenderCode@getDisplayNames()" />
                   <td class="value">   
                        <s:select headerKey="" headerValue="" 
                           		name="eligibleGenderCode" 
                           		list="#genderValues"  
                           		cssStyle="width:75px" /> 
                        <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>eligibleGenderCode</s:param>
                               </s:fielderror>                            
                         </span>
                         <input type="hidden" name="eligibleGenderCodeId" value="${eligibleGenderCodeId}"/>
                      </td>
                </tr> 
                <tr>
                     <td scope="row" class="label">
                     <label for="typeCode"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
                            <fmt:message key="isdesign.eligibilitycriteria.minimumAge"/></dfn><span class="required">*</span>
                     </label>
                     </td> <td class="value">   
							<s:textfield name="minimumValue" maxlength="12" cssStyle="width:85px" />
                           	<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>minimumValue</s:param>
                               </s:fielderror>                            
                            </span> 
                      </td>
                      <td scope="row" class="label">
                     <label for="typeCode"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
                            <fmt:message key="isdesign.eligibilitycriteria.unit"/></dfn><span class="required">*</span>
                     </label>
                     </td> 
    				<s:set name="unitsValues" value="@gov.nih.nci.pa.enums.UnitsCode@getDisplayNames()" />
                   <td class="value">   
                        <s:select headerKey="" headerValue="" 
                           		name="minimumUnit" 
                           		list="#unitsValues"  
                           		cssStyle="width:76px" /> 
                        <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>minimumUnit</s:param>
                               </s:fielderror>                            
                         </span>
                         <input type="hidden" name="minimumValueId" value="${minimumValueId}"/>
                      </td>           
                </tr>                      
				<tr>
                     <td scope="row" class="label">
                     <label for="typeCode"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
                            <fmt:message key="isdesign.eligibilitycriteria.maximumAge"/></dfn><span class="required">*</span>
                     </label>
                     </td> <td class="value">   
							<s:textfield name="maximumValue" maxlength="12" cssStyle="width:85px" />
                           	<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>maximumValue</s:param>
                               </s:fielderror>                            
                            </span> 
                      </td>
                      <td scope="row" class="label">
                     <label for="typeCode"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
                            <fmt:message key="isdesign.eligibilitycriteria.unit"/></dfn><span class="required">*</span>
                     </label>
                    </td>
    				<s:set name="unitsValues" value="@gov.nih.nci.pa.enums.UnitsCode@getDisplayNames()" />
                     <td class="value">   
                        <s:select headerKey="" headerValue="" 
                           		name="maximumUnit" 
                           		list="#unitsValues"  
                           		cssStyle="width:76px" /> 
                        <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>maximumUnit</s:param>
                               </s:fielderror>                            
                         </span>
                         <input type="hidden" name="maximumValueId" value="${maximumValueId}"/>
                      </td>            
                </tr> 		                                     
        </table>
        <s:if test="eligibilityList != null">    
		<input type="hidden" name="page" />
   		<input type="hidden" name="id" /> 
   		<display:table name="${eligibilityList}" id="row" class="data" sort="list"  pagesize="5" requestURI="eligibilityCriteriaquery.action" export="false">
	    <display:column titleKey="isdesign.eligibilitycriteria.inclusionIndicator" property="inclusionIndicator" sortable="true" headerClass="sortable" />
	    <display:column titleKey="isdesign.eligibilitycriteria.eligibilitycriteriadescription" property="textDescription" sortable="true" headerClass="sortable" />
	    <display:column titleKey="isdesign.eligibilitycriteria.eligibilitycriterianame" property="criterionName"  sortable="true" headerClass="sortable" />
	    <display:column titleKey="isdesign.eligibilitycriteria.operator" property="operator" sortable="true" headerClass="sortable" />
	    <display:column titleKey="isdesign.eligibilitycriteria.value" property="value"  sortable="true" headerClass="sortable" />
	    <display:column titleKey="isdesign.eligibilitycriteria.unit" property="unit" sortable="true" headerClass="sortable" />
	    <display:column title="Edit" class="action">
    		<s:url id="url" action="eligibilityCriteriaedit"><s:param name="id" value="%{#attr.row.id}" /> <s:param name="page" value="%{'Edit'}"/></s:url>
    		<s:a href="%{url}"><img src="<%=request.getContextPath()%>/images/ico_edit.gif" alt="Edit" width="16" height="16"/></s:a>
    	</display:column>    	
    	<display:column title="Delete" class="action">
			<s:url id="url" action="eligibilityCriteriadelete"><s:param name="id" value="%{#attr.row.id}" /></s:url>
    		<s:a href="%{url}"><img src="<%=request.getContextPath()%>/images/ico_delete.gif" alt="Delete" width="16" height="16"/></s:a>
		</display:column>
	</display:table>
  </s:if> 
		<div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>
                    <li><s:a href="eligibilityCriteriainput.action" cssClass="btn"><span class="btn_img"><span class="add">AddOtherCriteria</span></span></s:a></li>
					<li><a href="interventionalStudyDesignoutcomeQuery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
					<li><a href="disease.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>                
                </ul>   
            </del>
        </div> 

                   
    </s:form>
   </div>
 </body>
 </html>
