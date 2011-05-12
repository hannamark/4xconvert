<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="onhold.title" /></title>
<s:head />

<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}
function handleCreate(){
    document.listForm.action="onholdcreate.action";
    document.listForm.submit();
}
function handleEdit(rowId){
    document.listForm.selectedRowIdentifier.value = rowId;
    document.listForm.action="onholdedit.action";
    document.listForm.submit();
}
</SCRIPT>
</head>
<body>
<h1><fmt:message key="onhold.title"/></h1>
<c:set var="topic" scope="request" value="trialonhold"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box">
    <pa:sucessMessage />
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>

    <s:form name="listForm">
        <pa:studyUniqueToken/>
        <s:hidden name="selectedRowIdentifier"/>
    <h2>
        <fmt:message key="onhold.title"/>
    </h2>
    <table class="form">
        <tr><td colspan="2">
            <s:set name="onholdList" value="onholdList" scope="request"/>
            <display:table name="onholdList" id="row" class="data" sort="list" pagesize="10" requestURI="onhold.action">
                <display:column escapeXml="true" property="reasonCode" sortable="false" titleKey="onhold.reason.code"/>
                <display:column escapeXml="true" property="reasonText" sortable="false" titleKey="onhold.reason.text"/>
                <display:column escapeXml="true" property="dateLow" sortable="false" titleKey="onhold.date.low"/>
                <display:column escapeXml="true" property="dateHigh" sortable="false" titleKey="onhold.date.high"/>
                <pa:displayWhenCheckedOut>
                    <display:column titleKey="onhold.edit" headerClass="centered" class="action">
                        <c:if test="${(row.dateHigh==null)}">
                            <s:a href="#" onclick="handleEdit(%{#attr.row.identifier})">
                                <img src="<c:url value='images/ico_edit.gif'/>" alt="Edit" width="16" height="16" />
                            </s:a>
                        </c:if>
                    </display:column>
                </pa:displayWhenCheckedOut>
             </display:table>
        </td></tr>
    </table>
    <div class="actionsrow"><del class="btnwrapper">
    <ul class="btnrow">
        <pa:displayWhenCheckedOut>
            <li><a href="#" class="btn" onclick="this.blur();handleCreate();"><span class="btn_img"><span class="add">Add </span></span></a></li>
        </pa:displayWhenCheckedOut>
        <c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted'}">
            <li><a href="trialValidationquery.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
        </c:if>
    </ul>
    </del></div>
</s:form></div>
</body>
</html>