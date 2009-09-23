<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:set var="topic" scope="request" value="list_patients"/> 
<SCRIPT LANGUAGE="JavaScript">
function handleSearchAction(){
    document.forms[0].action="patients.action";
    document.forms[0].submit();
}
function handleAddAction(){
    document.forms[0].action="patientsadd.action";
    document.forms[0].submit();
}
</SCRIPT>
<head>
    <s:if test="%{currentAction == 'create'}">
        <fmt:message key="patient.create.title" /></s:if>
    <s:elseif test="%{currentAction == 'retrieve'}">
        <fmt:message key="patient.retrieve.title" /></s:elseif>
    <s:elseif test="%{currentAction == 'update'}">
        <fmt:message key="patient.update.title" /></s:elseif>
    <s:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<h1>
    <s:if test="%{currentAction == 'create'}">
        <fmt:message key="patient.create.title" /></s:if>
    <s:elseif test="%{currentAction == 'retrieve'}">
        <fmt:message key="patient.retrieve.title" /></s:elseif>
    <s:elseif test="%{currentAction == 'update'}">
        <fmt:message key="patient.update.title" /></s:elseif>
    <s:head/>
</h1>
<s:form name="listForm">

    <div class="actionsrow">
        <del class="btnwrapper">
           <ul class="btnrow">
            <li>
            <s:a href="#" cssClass="btn" onclick="handleSearchAction()"><span class="btn_img"><span class="search">Search</span></span></s:a>
            <s:a href="#" cssClass="btn" onclick="handleAddAction()"><span class="btn_img"><span class="add">Add New Patient</span></span></s:a>
            </li>
           </ul>
        </del>
    </div>
</s:form>

<div class="line"></div>
</body>
</html>
