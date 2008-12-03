<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="arms.main.title" /></title>
<s:head />
<script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>

</head>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
function handleEdit(rowId){
    document.diseaseForm.selectedArmIdentifier.value = rowId;
    document.diseaseForm.action="diseaseedit.action";
    document.diseaseForm.submit(); 
}
function handleDelete(rowId){
    input_box=confirm("Click OK to remove the disease from the study.  Cancel to abort.");
    if (input_box==true){
        document.diseaseForm.selectedArmIdentifier.value = rowId;
        document.diseaseForm.action="diseasedelete.action";
        document.diseaseForm.submit();
    }
}
function handleCreate(){
    document.diseaseForm.action="diseasecreate.action";
    document.diseaseForm.submit(); 
}
</SCRIPT>

<body onload="setFocusToFirstControl();">
<h1><fmt:message key="disease.main.title" /></h1>

<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box">
    <pa:sucessMessage /> 
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
    
    <s:form name="diseaseForm">
        <s:hidden name="selectedDiseaseIdentifier"/> 
    <h2>
        <s:if test="%{currentAction == 'edit'}"> 
            <fmt:message key="disease.edit.details.title"/>
        </s:if>
        <s:elseif test="%{currentAction == 'create'}">
            <fmt:message key="disease.add.details.title"/>
        </s:elseif>
    </h2>
    <table class="form">
        <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
        <tr>
        </tr>
    </table>
    <div class="actionsrow"><del class="btnwrapper">
    <ul class="btnrow">
        <li><a href="#" class="btn"
            onclick="this.blur();"><span class="btn_img"><span
            class="back">Back</span></span></a></li>
        <li><a href="#" class="btn"
            onclick="this.blur();"><span class="btn_img"><span
            class="next">Next</span></span></a></li>
    </ul>
    </del></div>
</s:form></div>
</body>
</html>