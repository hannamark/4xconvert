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
   
    function handleAction() {
        input_box=confirm("Click OK to save changes or Cancel to Abort.");
        if (input_box==true){
          document.trialDescription.action="trialDescriptionupdate.action";
          document.trialDescription.submit();
        }
    }    
</script>
     
</head>
<body onload="setFocusToFirstControl();">
<h1><fmt:message key="trial.description" /></h1>

<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
<div class="box">
<pa:sucessMessage/>
<s:form name="trialDescription">
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
            <td colspan="2" class="value"><s:textarea name="trialDescriptionDTO.trialBriefTitle" 
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
            <s:textarea name="trialDescriptionDTO.trialBriefSummary" cssStyle="width:606px" rows="4"/></td>
        </tr>        
        <tr>
        <th colspan="2"><fmt:message key="trial.detailedDescription"/></th>
        </tr>
        <tr>
        <td class="label">
           <label for=scientificDescrption>
                    <fmt:message key="trial.scientificDescription"/>
           </label>
         </td>
         <td class="value">
        <s:textarea name="trialDescriptionDTO.scientificDescription" cssStyle="width:606px" rows="4"/> 
        </td>
    </tr>
    
    </table>
   
<div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><a href="#" class="btn" onclick="handleAction();"><span class="btn_img"><span class="save">Save</span></span></a></li>
                     <li><a href="trialDocumentquery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
                     <li><a href="interventionalStudyDesigndetailsQuery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>                
                </ul>   
            </del>

        </div>          
</s:form>
</div>
</body>
</html>