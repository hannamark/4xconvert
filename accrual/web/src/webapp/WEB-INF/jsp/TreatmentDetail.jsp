<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<script type="text/javascript">

	function handleSaveAction() {
        document.forms[0].action = "addTreatment.action";
        document.forms[0].submit();
    }

    function handleCancelAction() {
        document.forms[0].action = "executeTreatment.action";
        document.forms[0].submit();
    }

</script>
<title>
    <s:if test="%{currentAction == 'create'}">
        <c:set var="topic" scope="request" value="treatment_adding"/> 
        <fmt:message key="treatPlan.addPage" /></s:if>
    <s:elseif test="%{currentAction == 'update'}">
        <c:set var="topic" scope="request" value="treatment_update"/> 
        <fmt:message key="treatPlan.updatePage" /></s:elseif>
</title>        
    <s:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1>
   <s:if test="%{currentAction == 'create'}">
        <fmt:message key="treatPlan.addPage" /></s:if>
    <s:elseif test="%{currentAction == 'update'}">
        <fmt:message key="treatPlan.updatePage" /></s:elseif>
</h1>
<div class="box">
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
<s:form name="detailForm">
<table class="form">
<tr>
        <td scope="row" class="label"><label><fmt:message key="treatPlan.name"/><span class="required">*</span></label></td>
        <td>
            <s:textfield size="50" name="treatment.name" cssStyle="width:280px;float:left" />
            <s:fielderror cssClass="formErrorMsg"><s:param>treatment.name</s:param></s:fielderror>
        </td>
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="treatPlan.description"/><span class="required">*</span></label></td>
        <td class="value">
            <s:textarea name="treatment.description" cols="4" cssStyle="width:98%;max-width:250px"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>treatment.description</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="treatPlan.intervType"/><span class="required">*</span></label></td>
        <td>
        </td>
    </tr>
        
</table>
</s:form>

<div class="actionsrow">
   <del class="btnwrapper">
      <ul class="btnrow">
       <li>        
            <s:a href="#" cssClass="btn" onclick="handleSaveAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
            <s:a href="#" cssClass="btn" onclick="handleCancelAction()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
        </li>
      </ul>
   </del>
</div>

</div>
</body>
</html>
