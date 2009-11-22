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
    var len =  document.eligibilityCriteraiAdd.group3.length;
    var selElement;
    for (i = 0; i < len; i++)
     {
     if(document.eligibilityCriteraiAdd.group3[i].checked){
       selElement = document.eligibilityCriteraiAdd.group3[i];
       }
     }  
    activate(selElement);         
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
        document.forms["eligibilityCriteraiAdd"].action="eligibilityCriteriaupdate.action";
        document.forms["eligibilityCriteraiAdd"].submit();     
    } else {
        document.forms["eligibilityCriteraiAdd"].action="eligibilityCriteriacreate.action";
        document.forms["eligibilityCriteraiAdd"].submit();   
    } 
 }
} 
function activate(selected){
   
    var input="webDTO.textDescription";
    var inputElement = document.forms["eligibilityCriteraiAdd"].elements[input];
    
    
    var criterionName="webDTO.criterionName";
    var cnElement = document.forms["eligibilityCriteraiAdd"].elements[criterionName];
    var operator="webDTO.operator";
    var opElement = document.forms["eligibilityCriteraiAdd"].elements[operator];
    var min="webDTO.valueIntegerMin";
    var minElement = "";
    var max="webDTO.valueIntegerMax";
    var maxElement="";
    var txt="webDTO.valueText";
    var txtElement="";
    
    if (document.forms["eligibilityCriteraiAdd"].elements[min] != 'undefined' && document.forms["eligibilityCriteraiAdd"].elements[min] != null) {     
     minElement = document.forms["eligibilityCriteraiAdd"].elements[min];
    }
    if(document.forms["eligibilityCriteraiAdd"].elements[max] != 'undefined' && document.forms["eligibilityCriteraiAdd"].elements[max] !=null) {     
      maxElement = document.forms["eligibilityCriteraiAdd"].elements[max];
    }  
    if ( document.forms["eligibilityCriteraiAdd"].elements[txt] !='undefined' &&  document.forms["eligibilityCriteraiAdd"].elements[txt] != null) {     
     txtElement = document.forms["eligibilityCriteraiAdd"].elements[txt];
    }
    var unit="webDTO.unit";
    var uElement = document.forms["eligibilityCriteraiAdd"].elements[unit];
    
    
        if (selected.value == 'Unstructured')
        {
            cnElement.disabled = true;
            opElement.disabled = true;
            if (minElement != "") {
             minElement.disabled = true;
            } 
            if (maxElement != "") {
             maxElement.disabled = true;
            } 
            if (txtElement != "") {
             txtElement.disabled = true;
            } 
            if (uElement != "") {
             uElement.disabled = true;
            } 
            inputElement.disabled=false;
            document.getElementById("loadUOMDetails").disabled=true;
            document.getElementById("criteriaNameLookup").disabled=true;
            document.getElementById("generateTextButton").disabled=true;
            
        }else
        {
             cnElement.disabled = false;
             opElement.disabled = false;
             if (minElement != 'undefined'|| minElement != null) {
            minElement.disabled = false;
         } 
         if (maxElement != 'undefined'|| maxElement != null) {
            maxElement.disabled = false;
         } 
         if (txtElement != 'undefined'|| txtElement != null) {
             txtElement.disabled = false;
         } 
         if (uElement != 'undefined'|| uElement != null) {
            uElement.disabled = false;
            } 
             inputElement.disabled=false;
             document.getElementById("loadUOMDetails").disabled=false;
             document.getElementById("criteriaNameLookup").disabled=false;
             document.getElementById("generateTextButton").disabled=false;
           
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
 function loadDiv(deid) {
     window.top.hidePopWin(true);
     var url = '/pa/protected/ajaxEligibilityCriteriadisplaycde.action?cdeid='+deid;
     var div = document.getElementById('eligibility.build.criterion');   
     div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';    
     var aj = new Ajax.Updater(div, url, {
        asynchronous: true,
        method: 'get',
        evalScripts: false
     });
 }
 
 function activateMax(){
   
     var operate="webDTO.operator";
     var operateElement = document.forms["eligibilityCriteraiAdd"].elements[operate];
      var intMaxName="webDTO.valueIntegerMax";
      var intMaxElement = document.forms["eligibilityCriteraiAdd"].elements[intMaxName];
     if(operateElement.value != 'In') {
     intMaxElement.disabled = true;
     } else {
      intMaxElement.disabled = false;
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
    <s:form id="eligibilityCriteraiAdd" name="eligibilityCriteraiAdd"><s:actionerror/>    
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
                            <fmt:message key="isdesign.eligibilitycriteria.structuredOrUnstructured"/><span class="required">*</span>:</label>
                        </td>
                        <td class="value">   
                            <s:if test="%{webDTO.structuredType == 'Structured'}">
                            <input type="radio" id="group3" name="webDTO.structuredType" value="Structured"  checked="checked" onclick='activate(this)'/>Structured<br/>
                            <input type="radio" id="group3" name="webDTO.structuredType" value="Unstructured" onclick='activate(this)'/>Un-structured
                            </s:if>
                            <s:elseif test="%{webDTO.structuredType == 'Unstructured'}">
                            <input type="radio" id="group3" name="webDTO.structuredType" value="Structured"  onclick='activate(this)'/>Structured<br/>
                            <input type="radio" id="group3" name="webDTO.structuredType" value="Unstructured" checked="checked" onclick='activate(this)'/>Un-structured
                                
                            </s:elseif>
                             <s:else>
                           <input type="radio" id="group3" name="webDTO.structuredType" value="Structured" onclick='activate(this)' />Structured<br/>
                           <input type="radio" id="group3" name="webDTO.structuredType" value="Unstructured" onclick='activate(this)'/>Un-structured
                                                                       
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
                </table>
                 <div id="eligibility.build.criterion">
                        <jsp:include page="/WEB-INF/jsp/nodecorate/eligibilityBuildCriterion.jsp" />
                 </div>
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
