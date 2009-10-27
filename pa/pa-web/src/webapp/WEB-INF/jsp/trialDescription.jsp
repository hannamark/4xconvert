<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="trial.description" /></title>
<s:head />
<link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all" />
<link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
<script type="text/javascript" src="<c:url value='/scripts/js/scriptaculous.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<c:url value="/protected/studyOverallStatushistorypopup.action" var="lookupUrl" />

<script type="text/javascript">
   
    // this function is called from body onload in main.jsp (decorator)
    function callOnloadFunctions(){
        setFocusToFirstControl();        
    }
    
    function handleAction() {
        input_box=confirm("Click OK to save changes or Cancel to Abort.");
        if (input_box==true){
          document.trialDescription.action="trialDescriptionupdate.action";
          document.trialDescription.submit();
        }
    }    
</script>
     
</head>
<body>
<h1><fmt:message key="trial.description" /></h1>
<c:set var="topic" scope="request" value="abstract_description"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
<div class="box">
<pa:sucessMessage/>
<pa:failureMessage/>
<s:form name="trialDescription" validate="true" method="POST">
    <s:hidden name="studyObjectiveIip" id="studyObjectiveIip"/>
    <s:hidden name="studyObjectiveIis" id="studyObjectiveIis"/>
    <s:hidden name="studyObjectiveIit" id="studyObjectiveIit"/>
     <s:if test="hasActionErrors()">
            <div class="error_msg"><s:actionerror/></div>
        </s:if>
    <h2><fmt:message key="trial.description" /></h2>
    <table class="form">
         <tr> 
                <td/>
                <td class="info" colspan="2">Madatory at Abstraction Validation</td>
       </tr>
        <tr>
            <td class="label">
            <s:label name="trialBriefTitleLabel" for="trialBriefTitle">
                <fmt:message key="trial.briefTitle"/> <span class="notRequired">*</span>
            </s:label>
            </td>
            <td colspan="2" class="value"><s:textarea name="trialBriefTitle" 
                cssStyle="width:606px" rows="4" /></td>
        </tr> 
        <tr> 
                <td/>
                <td class="info" colspan="2">Madatory at Abstraction Validation</td>
       </tr>
          <tr>
            <td class="label">
            <s:label name="trialBriefSummaryLabel" for="trialBriefSummary">
                <fmt:message key="trial.briefSummary"/><span class="notRequired">*</span>
            </s:label>
            </td>
            <td colspan="2" class="value">
            <s:textarea name="trialBriefSummary" cssStyle="width:606px" rows="4"/></td>
        </tr>        
        <tr>
        <th colspan="2"><fmt:message key="trial.detailedDescription"/></th>
        </tr>
     <tr>
        <td class="label">
           <label for="scientificDescription">
                    <fmt:message key="trial.scientificDescription"/>
           </label>
         </td>
         <td class="value">
        <s:textarea name="outline" cssStyle="width:606px" rows="10"/> (Max 32000 chars)
        <span class="formErrorMsg"> 
                <s:fielderror>
                    <s:param>outline</s:param>
                </s:fielderror>                            
            </span> 
        </td>
    </tr>
        <tr>
        <td class="label">
           <label for="primary">
                    <fmt:message key="trial.primary"/>
           </label>
         </td>
         <td class="value">
        <s:textarea readonly="true"  name="primary" cssStyle="width:606px" rows="2" cssClass="readonly"/> (Max 2000 chars)
            <span class="formErrorMsg"> 
                <s:fielderror>
                    <s:param>primary</s:param>
                </s:fielderror>                            
            </span>
        </td>
    </tr>
        <tr>
        <td class="label">
           <label for="secondary">
                    <fmt:message key="trial.secondary"/>
           </label>
         </td>
         <td class="value">
        <s:textarea readonly="true"  name="secondary" cssStyle="width:606px" rows="2" cssClass="readonly"/> (Max 2000 chars)
        <span class="formErrorMsg"> 
                <s:fielderror>
                    <s:param>secondary</s:param>
                </s:fielderror>                            
            </span>
        </td>
    </tr>
        <tr>
        <td class="label">
           <label for="ternary">
                    <fmt:message key="trial.ternary" />
           </label>
         </td>
         <td class="value">
        <s:textarea readonly="true"  name="ternary" cssStyle="width:606px" rows="2" cssClass="readonly"/> (Max 2000 chars)
        <span class="formErrorMsg"> 
                <s:fielderror>
                    <s:param>ternary</s:param>
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
                    <li><a href="#" class="btn" onclick="handleAction();"><span class="btn_img"><span class="save">Save</span></span></a></li>
                     </c:if>
                     <li><a href="trialDocumentquery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
                     <li><a href="interventionalStudyDesigndetailsQuery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>                
                </ul>   
            </del>

        </div>          
</s:form>
</div>
</body>
</html>
