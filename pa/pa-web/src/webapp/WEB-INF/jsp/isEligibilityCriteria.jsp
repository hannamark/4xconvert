<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>
    <c:choose>
     <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'ObservationalStudyProtocol'}">
     <fmt:message key="osdesign.eligibilitycriteria.webtitle"/>
     </c:when>
     <c:otherwise><fmt:message key="isdesign.eligibilitycriteria.webtitle"/></c:otherwise> 
     </c:choose>
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
</SCRIPT>
<body onload="setFocusToFirstControl();">

 <h1>
 <c:choose>
     <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'ObservationalStudyProtocol'}">
     <fmt:message key="osdesign.eligibilitycriteria.webtitle"/>
     </c:when>
     <c:otherwise><fmt:message key="isdesign.eligibilitycriteria.webtitle"/></c:otherwise></c:choose> </h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form><s:actionerror/>    
    <h2><fmt:message key="isdesign.eligibilitycriteria.title"/></h2>
    <table class="form">
    <c:if test="${sessionScope.trialSummary.studyProtocolType  == 'ObservationalStudyProtocol'}">
        <tr>
            <td scope="row"  class="label">
            <label>
                <fmt:message key="osdesign.eligibilitycriteria.trialPopulationDescription"/>(Max 800 chars)<span class="required">*</span>
             </label>
             </td> <td class="value">               
                <s:textarea name="studyPopulationDescription" rows="3" cssStyle="width:250px" />
                <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>studyPopulationDescription</s:param>
                       </s:fielderror>                            
                 </span>
            </td>      
       </tr>
       <tr>
            <td scope="row"  class="label"><label>
                <fmt:message key="osdesign.eligibilitycriteria.samplingMethod"/><span class="required">*</span></label>
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
    </c:if>
	<tr>
		<td scope="row"  class="label"><label>
			<fmt:message key="isdesign.eligibilitycriteria.ahv"/><span class="required">*</span></label>
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
         <label for="fileName">
                <fmt:message key="isdesign.eligibilitycriteria.eligibleGender"/><span class="required">*</span>
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
         <label for="typeCode">
                <fmt:message key="isdesign.eligibilitycriteria.minimumAge"/><span class="required">*</span>
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
         <label for="typeCode">
                <fmt:message key="isdesign.eligibilitycriteria.unit"/><span class="required">*</span>
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
		<td/>
		<td class="info" colspan="2">Write 0 if no min age is indicated</td>
	</tr>                      
	<tr>
         <td scope="row" class="label">
         <label for="typeCode">
                <fmt:message key="isdesign.eligibilitycriteria.maximumAge"/><span class="required">*</span>
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
         <label for="typeCode">
                <fmt:message key="isdesign.eligibilitycriteria.unit"/><span class="required">*</span>
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
    <tr> 
		<td/>
		<td class="info" colspan="2">Write 999 if no max age is indicated and select 'Years' as Unit</td>
	</tr> 		                                     
    </table>
    <h2><fmt:message key="eligibilitycriteria.other" /></h2>
    <s:if test="eligibilityList != null">    
    	<input type="hidden" name="page" />
    	<input type="hidden" name="id" /> 
    	 <c:if test="${fn:length(eligibilityList) > 10}">
    	<div style="overflow:auto; height:356px;width:968px;">
    	</c:if>
    	<display:table name="${eligibilityList}" id="row" class="data" 
    	       sort="list"  pagesize="200" requestURI="eligibilityCriteriaquery.action" export="false">
            <display:column titleKey="isdesign.eligibilitycriteria.eligibilitycriteriatype" property="inclusionIndicator" sortable="true" headerClass="sortable" />
            <display:column titleKey="isdesign.eligibilitycriteria.eligibilitycriteriadescription" property="textDescription" sortable="true" headerClass="sortable" maxLength= "200"/>
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
         <c:if test="${fn:length(eligibilityList) > 10}">
        </div>
        </c:if>
    </s:if> 
	<div class="actionsrow">
        <del class="btnwrapper">
            <ul class="btnrow">
                <li><s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>
                <s:if test="list != null">
                <li><s:a href="eligibilityCriteriainput.action" cssClass="btn"><span class="btn_img"><span class="add">AddOtherCriterion</span></span></s:a></li>
                </s:if>
				<li><a href="interventionalStudyDesignoutcomeQuery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
				<li><a href="disease.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>                
            </ul>   
        </del>
    </div> 
    </s:form>
   </div>
 </body>
 </html>
