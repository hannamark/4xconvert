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
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
</head>
<SCRIPT LANGUAGE="JavaScript">

// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    setFocusToFirstControl();         
}

function handleAction(){
    document.forms[0].action="eligibilityCriteriasave.action";
    document.forms[0].submit(); 
} 
function handleReOrderAction(){
    document.forms[0].action="eligibilityCriteriareOrder.action";
    document.forms[0].submit(); 
} 
function tooltip() {
BubbleTips.activateTipOn("acronym");
BubbleTips.activateTipOn("dfn"); 
}
</SCRIPT>
<body>
<c:set var="topic" scope="request" value="abstracteligibility"/>
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
    <s:form>
        <s:token/>
        <s:actionerror/>
        <pa:studyUniqueToken/>    
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
             <s:hidden name="eligibleGenderCodeId"/>
          </td>
    </tr> 
    <tr>
         <td scope="row" class="label">
         <label for="typeCode">
                <fmt:message key="isdesign.eligibilitycriteria.minimumAge"/><span class="required">*</span>
         </label>
         </td> <td class="value" colspan="2">    
                <s:textfield name="minimumValue" maxlength="12" cssStyle="width:85px" />
                <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>minimumValue</s:param>
                   </s:fielderror>                            
                </span> 
          </td>
           <td scope="row" class="label" >
         <label for="typeCode">
                <fmt:message key="isdesign.eligibilitycriteria.unit"/><span class="required">*</span>
         </label>
         </td> 
        <s:set name="unitsValues" value="@gov.nih.nci.pa.enums.UnitsCode@getDisplayNames()" />
       <td class="value" colspan="2">   
            <s:select headerKey="" headerValue="" 
                    name="minValueUnit" 
                    list="#unitsValues"  
                    cssStyle="width:76px" /> 
            <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>minValueUnit</s:param>
                   </s:fielderror>                            
             </span></td>
          </tr>
          <tr>
          <td scope="row" class="label" >
         <label for="typeCode">
                <fmt:message key="isdesign.eligibilitycriteria.maximumAge"/><span class="required">*</span>
         </label>
         </td> <td class="value" colspan="2">   
                <s:textfield name="maximumValue" maxlength="12" cssStyle="width:85px" />
                <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>maximumValue</s:param>
                   </s:fielderror>                            
                </span> 
          </td>
         <td scope="row" class="label" >
         <label for="typeCode">
                <fmt:message key="isdesign.eligibilitycriteria.unit"/><span class="required">*</span>
         </label>
         </td> 
        <s:set name="unitsValues" value="@gov.nih.nci.pa.enums.UnitsCode@getDisplayNames()" />
       <td class="value" colspan="2">   
            <s:select headerKey="" headerValue="" 
                    name="maxValueUnit" 
                    list="#unitsValues"  
                    cssStyle="width:76px" /> 
            <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>maxValueUnit</s:param>
                   </s:fielderror>                            
             </span>
             <s:hidden name="valueId"/>
          </td>           
    </tr> 
    <tr> 
        <td/>
        <td class="info" colspan="2">Write 0 if no min age is indicated</td>
        <td/>
        <td class="info" colspan="2">Write 999 if no max age is indicated and select 'Years' as Unit</td>
    </tr>                                            
    </table>
    <h2><fmt:message key="eligibilitycriteria.other" /></h2>
    <s:if test="eligibilityList != null" >
      <s:hidden name="page" />
      <s:hidden name="id" /> 
       <table class="data">
                <tr><td>
                 <table class="form">
                 <tbody> 
                 <tr>
                 <th><label for="typeCode"><fmt:message key="isdesign.eligibilitycriteria.eligibilitycriteriatype"/></label></th>
                 <th><label for="typeCode"><fmt:message key="isdesign.eligibilitycriteria.eligibilitycriteriadescription"/></label></th>
                  <th><label for="typeCode"><fmt:message key="isdesign.eligibilitycriteria.eligibilitycriterianame"/></label></th>
                  <th><label for="typeCode"><fmt:message key="isdesign.eligibilitycriteria.operator"/></label></th>
                  <th><label for="typeCode"><fmt:message key="isdesign.eligibilitycriteria.value"/></label></th>
                  <th><label for="typeCode"><fmt:message key="isdesign.eligibilitycriteria.unit"/></label></th>
                  <th><label for="typeCode"><fmt:message key="isdesign.eligibilitycriteria.eligibilitycriteriaDisplayOrder"/></label></th>
                  <th>Edit</th>
                  <th>Delete</th>
                  </tr>
      
                      <s:iterator value="eligibilityList" id="eligibilityList" status="stat" >
                      <tr>
                       <td class="tdBoxed">
                       <s:textfield  name="eligibilityList[%{#stat.index}].inclusionIndicator" value="%{inclusionIndicator}" cssStyle="width:55px;border: 1px solid #FFFFFF" readonly="true"/>
                       </td>
                       <td class="tdBoxed">
                        <s:textarea   name="eligibilityList[%{#stat.index}].textDescription" value="%{textDescription}" cssStyle="width:250px;border: 1px solid #FFFFFF" readonly="true" rows="2"/>
                       </td>
                       <td class="tdBoxed">
                        <s:textarea name="eligibilityList[%{#stat.index}].criterionName" value="%{criterionName}" cssStyle="width:250px;border: 1px solid #FFFFFF" readonly="true" rows="2"/>
                       </td>
                       <td class="tdBoxed">
                        <s:textfield  name="eligibilityList[%{#stat.index}].operator" value="%{operator}" cssStyle="width:40px;border: 1px solid #FFFFFF" readonly="true" rows="2"/>
                       </td>
                       <td class="tdBoxed">
                        <s:if test="%{eligibilityList[#stat.index].valueText != null}">
                       <s:textfield  name="eligibilityList[%{#stat.index}].valueText" value="%{valueText}" cssStyle="width:50px;border: 1px solid #FFFFFF" readonly="true"/>
                       </s:if>
                       <s:elseif test="%{eligibilityList[#stat.index].valueIntegerMin != null || eligibilityList[#stat.index].valueIntegerMax != null}">
                        <s:textfield  name="eligibilityList[%{#stat.index}].valueIntegerMin" value="%{valueIntegerMin}" cssStyle="width:35px;border: 1px solid #FFFFFF" readonly="true"/> 
                        <s:if test="%{eligibilityList[#stat.index].valueIntegerMax != null}">
                        -<s:textfield  name="eligibilityList[%{#stat.index}].valueIntegerMax" value="%{valueIntegerMax}" cssStyle="width:35px;border: 1px solid #FFFFFF" readonly="true"/>
                        </s:if>
                       </s:elseif>
                       </td>
                       <td class="tdBoxed">
                        <s:textfield  name="eligibilityList[%{#stat.index}].unit" value="%{unit}" cssStyle="width:55px;border: 1px solid #FFFFFF" readonly="true"/>
                       </td>
                       <td class="tdBoxed">
                          <s:hidden  name="eligibilityList[%{#stat.index}].id" value="%{id}" />
                          <s:hidden  name="eligibilityList[%{#stat.index}].structuredType" value="%{structuredType}" />
                          <s:textfield  name="eligibilityList[%{#stat.index}].displayOrder" value="%{displayOrder}" cssStyle="width:50px" />
                         </td>
                         <td>
                            <pa:scientificAbstractorDisplayWhenCheckedOut>
                                <s:url id="url" action="eligibilityCriteriaedit">
                                    <s:param name="id" value="%{id}" />
                                    <s:param name="page" value="%{'Edit'}"/>
                                </s:url>
                                <s:a href="%{url}"><img src="<c:url value="/images/ico_edit.gif"/>" alt="Edit" width="16" height="16"/></s:a>
                            </pa:scientificAbstractorDisplayWhenCheckedOut>
                         </td>
                         <td>
                            <pa:scientificAbstractorDisplayWhenCheckedOut>
                                <s:url id="url" action="eligibilityCriteriadelete">
                                    <s:param name="id" value="%{id}" />
                                </s:url>
                                <s:a href="%{url}"><img src="<c:url value="/images/ico_delete.gif"/>" alt="Delete" width="16" height="16"/></s:a>
                            </pa:scientificAbstractorDisplayWhenCheckedOut>   
                         </td>
                     </tr>
                    </s:iterator>
            </tbody>   
              
           </table>
            <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>reOrder</s:param>
                       </s:fielderror>                            
                 </span>
          
           </td></tr></table>
       </s:if>
   
    <div class="actionsrow">
        <del class="btnwrapper">
            <ul class="btnrow">
                <pa:scientificAbstractorDisplayWhenCheckedOut>
                    <li><s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>
                    <s:if test="eligibilityList != null" >
                        <li><s:a href="#" onclick="handleReOrderAction()" cssClass="btn"><span class="btn_img"><span class="save">Re-Order</span></span></s:a></li>
                    </s:if>
                    <s:if test="list != null">
                        <li><s:a href="eligibilityCriteriainput.action" cssClass="btn"><span class="btn_img"><span class="add">Add Other Criterion</span></span></s:a></li>
                    </s:if>
                </pa:scientificAbstractorDisplayWhenCheckedOut>
            </ul>   
        </del>
    </div> 
    </s:form>
   </div>
 </body>
 </html>
