<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<c:url value="/protected/popupCadsr.action" var="lookupUrl" />
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><c:choose>
     <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'ObservationalStudyProtocol'}">
     <fmt:message key="osdesign.eligibilitycriteria.webtitle"/>
     </c:when>
     <c:otherwise><fmt:message key="isdesign.eligibilitycriteria.webtitle"/></c:otherwise></c:choose> </title>
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
</head>
<c:url value="/protected/popupTypeInterventiontype.action?className=UnitOfMeasurement&divName=loadUOMDetails" var="lookupUOMUrl" />
<SCRIPT LANGUAGE="JavaScript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    setFocusToFirstControl();
    activate();         
}
function lookup(){
    showPopWin('${lookupUrl}', 900, 400, '', 'CaDSR Lookup');
} 
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
    var min="webDTO.valueIntegerMin";
    var minElement = document.forms[0].elements[min];
    var max="webDTO.valueIntegerMax";
    var maxElement = document.forms[0].elements[max];
    var txt="webDTO.valueText";
    var txtElement = document.forms[0].elements[txt];
    var unit="webDTO.unit";
    var uElement = document.forms[0].elements[unit];
    
        if (inputElement.value != "")
        {
            cnElement.disabled = true;
            opElement.disabled = true;
            minElement.disabled = true;
            maxElement.disabled = true;
            txtElement.disabled = true;
            uElement.disabled = true;
        }else
        {
            cnElement.disabled = false;
            opElement.disabled = false;
             minElement.disabled = false;
         maxElement.disabled = false;
            txtElement.disabled = false;
            uElement.disabled = false;
        }   
    }

function tooltip() {
BubbleTips.activateTipOn("acronym");
BubbleTips.activateTipOn("dfn"); 
}

 function lookupUOM(){
        showPopWin('${lookupUOMUrl}', 900, 400, '', 'Unit Of Measure');
    }
function loadDetails(id, divName,className){
         var url = '/pa/protected/ajaxEligibilityCriteriadisplaySelectedType.action?id='+id+'&className='+className+'&divName='+divName;
         var div = document.getElementById(divName);   
         div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';    
         var aj = new Ajax.Updater(div, url, {
            asynchronous: true,
            method: 'get',
            evalScripts: false
         });
}   
 function handleGenerateCriteriaText() {
        document.forms[0].action="eligibilityCriteriagenerate.action";
        document.forms[0].submit();
 }   
 function inactivateText(){
    var min="webDTO.valueIntegerMin";
    var minElement = document.forms[0].elements[min];

    var max="webDTO.valueIntegerMax";
    var maxElement = document.forms[0].elements[max];
    var txt="webDTO.valueText";
    var txtElement = document.forms[0].elements[txt];
    
    
        if (txtElement.value != "")
        {
            minElement.disabled = true; 
            maxElement.disabled = true; 
        }else
        {
             minElement.disabled = false; 
             maxElement.disabled = false;            
        }   
    }
</SCRIPT>
<body>
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
    <s:hidden name="page"/>
    <s:hidden name="id"/>
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
                        <td scope="row"  class="label"><label>
                            <fmt:message key="isdesign.eligibilitycriteria.structuredOrUnstructured"/>:</label>
                        </td>
                        <td class="value">   
                            <s:if test="%{webDTO.structuredType == 'Structured'}">
                            <input type="radio" id="group3" name="webDTO.structuredType" value="Structured"  checked="checked"/>Structured<br/>
                            <input type="radio" id="group3" name="webDTO.structuredType" value="Unstructured"/>Un-structured
                            </s:if>
                            <s:elseif test="%{webDTO.structuredType == 'Unstructured'}">
                            <input type="radio" id="group3" name="webDTO.structuredType" value="Structured"  />Structured<br/>
                <input type="radio" id="group3" name="webDTO.structuredType" value="Unstructured" checked="checked"/>Un-structured
                                
                            </s:elseif>
                             <s:else>
                     <input type="radio" id="group3" name="webDTO.structuredType" value="Structured"  />Structured<br/>
                 <input type="radio" id="group3" name="webDTO.structuredType" value="Unstructured" />Un-structured
                                                                       
                            </s:else>
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.structuredType</s:param>
                               </s:fielderror>                            
                         </span>
                        </td>
                    </tr> 
                <tr>
                    <td scope="row"  class="label"><label>
                        <fmt:message key="isdesign.eligibilitycriteria.eligibilitycriteriaDisplayOrder"/><span class="required">*</span></label>
                    </td>
                   <td class="value">
                        <s:textfield name="webDTO.displayOrder" maxlength="3" cssStyle="width:150px" />
                        <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.displayOrder</s:param>
                               </s:fielderror>                            
                         </span>
                    </td>
                </tr>           
                
                <tr>
                <th colspan="2"><fmt:message key="isdesign.eligibilitycriteria.buildCriterion"/></th>
                </tr>
                <tr>
                <td>
                 <div id="loadCriteriaName">
                        <%@ include file="/WEB-INF/jsp/nodecorate/eligibilityCriteriaBuild.jsp"%>
                 </div>
                 </td>
                 </tr> 
                 <tr>
                      <td scope="row" class="label">
                     <label for="typeCode">
                            <fmt:message key="isdesign.eligibilitycriteria.unit"/>
                     </label>
                   </td>
                    <td class="value">
                    <div id="loadUOMDetails">
                        <%@ include file="/WEB-INF/jsp/nodecorate/displayUOM.jsp"%>
                    </div> 
                    </td> 
                      <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.buldcriterion</s:param>
                               </s:fielderror>                            
                         </span>           
                </tr> 
                  <tr>
                      <td scope="row" class="label">
                     
                   </td>
                    <td class="value">
                    <s:a href="#" cssClass="btn" onclick="handleGenerateCriteriaText()"><span class="btn_img"><span class="save">Generate Criteria Text</span></span></s:a>
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
