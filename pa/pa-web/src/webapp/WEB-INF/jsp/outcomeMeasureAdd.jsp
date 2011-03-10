<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><c:choose>
     <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'ObservationalStudyProtocol'}">
     <fmt:message key="osdesign.outcome.title"/>
     </c:when>
     <c:otherwise><fmt:message key="isdesign.outcome.title"/></c:otherwise></c:choose>
     </title>
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
</head>

<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
<SCRIPT LANGUAGE="JavaScript">

// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    setFocusToFirstControl();        
}

function handleAction(){
var page;
page=document.forms[0].page.value;
input_box=confirm("Click OK to save changes or Cancel to Abort.");
if (input_box==true){
	if (page == "Edit"){
 		document.forms[0].action="interventionalStudyDesignoutcomeupdate.action";
 		document.forms[0].submit();  	
	} else {
 		document.forms[0].action="interventionalStudyDesignoutcomecreate.action";
 		document.forms[0].submit();   
 	} 
 }
} 
function tooltip() {
BubbleTips.activateTipOn("acronym");
BubbleTips.activateTipOn("dfn"); 
} 
</SCRIPT>
<body>
<c:set var="topic" scope="request" value="abstractoutcome"/>
 <h1><c:choose>
     <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'ObservationalStudyProtocol'}">
     <fmt:message key="osdesign.outcome.title"/>
     </c:when>
     <c:otherwise><fmt:message key="isdesign.outcome.title"/></c:otherwise></c:choose></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form>
        <s:actionerror/>    
        <pa:studyUniqueToken/>
    <h2><fmt:message key="osdesign.outcome.subtitle"/></h2>
    <s:hidden name="page"/>
    <s:hidden name="id" />
    <table class="form">
    			<tr>
					<td scope="row"  class="label"><label>
						<fmt:message key="osdesign.outcome.primary"/><span class="required">*</span></label>
					</td>
					<td class="value">
						<s:select  name="webDTO.outcomeMeasure.primaryIndicator" list="#{' ':' ', 'false':'No', 'true':'Yes'}" />
						<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.outcomeMeasure.primaryIndicator</s:param>
                               </s:fielderror>                            
                         </span>
					</td>
				</tr>
				<tr>
                     <td scope="row" class="label">
                     <label for="fileName">
                            <fmt:message key="osdesign.outcome.name"/> (Max 254 chars)<span class="required">*</span>
                     </label>
                    </td>
                    <td class="value">
                        <s:textarea name="webDTO.outcomeMeasure.name" rows="4" cssStyle="width:400px"/>
                        <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.outcomeMeasure.name</s:param>
                               </s:fielderror>                            
                         </span>
                      </td>         
                </tr> 
                <tr>
                     <td scope="row" class="label">
                     <label for="typeCode">
                            <fmt:message key="osdesign.outcome.timeFrame"/> (Max 254 chars)<span class="required">*</span>
                     </label>
                    </td>
                    <td class="value">
                            <s:textarea name="webDTO.outcomeMeasure.timeFrame" rows="4" cssStyle="width:400px"/>
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.outcomeMeasure.timeFrame</s:param>
                               </s:fielderror>                            
                            </span> 
                      </td>         
                </tr>                      
				<tr>
                     <td scope="row" class="label">
                     <label for="fileName">
                            <fmt:message key="osdesign.outcome.description"/> (Max 600 chars)
                     </label>
                    </td>
                    <td class="value">
                        <s:textarea name="webDTO.outcomeMeasure.description" rows="4" cssStyle="width:400px"/>
                        <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.outcomeMeasure.description</s:param>
                               </s:fielderror>                            
                         </span>
                      </td>         
                </tr> 
				<tr>
					<td scope="row"  class="label"><label>
						<fmt:message key="osdesign.outcome.safety"/><span class="required">*</span></label>
					</td>
					<td class="value">
						<s:select name="webDTO.outcomeMeasure.safetyIndicator" list="#{' ':' ', 'false':'No', 'true':'Yes'}" /><span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.outcomeMeasure.safetyIndicator</s:param>
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
