<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="onhold.title" /></title>
<s:head />
<script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>

<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
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
<body onload="setFocusToFirstControl();">
<h1><fmt:message key="onhold.title"/></h1>
<c:set var="topic" scope="request" value="trial_onhold"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box">
    <pa:sucessMessage /> 
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
    
    <s:form name="listForm">
        <s:hidden name="selectedRowIdentifier"/> 
    <h2>
        <fmt:message key="onhold.title"/>
    </h2>
    <table class="form">
        <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
        <tr><td colspan="2">
            <display:table name="onholdList" id="row" class="data" sort="list" pagesize="10" requestURI="onhold.action">
                <display:column property="reasonCode" sortable="false" titleKey="onhold.reason.code"/>
                <display:column property="reasonText" sortable="false" titleKey="onhold.reason.text"/>
                <display:column property="dateLow" sortable="false" titleKey="onhold.date.low"/>
                <display:column property="dateHigh" sortable="false" titleKey="onhold.date.high"/>
                <c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)
                					|| (sessionScope.role == 'SuAbstractor')}">
                <display:column titleKey="onhold.edit" headerClass="centered" class="action">
                    <c:if test="${(row.dateHigh==null)}">
                    <s:a href="#" onclick="handleEdit(%{#attr.row.identifier})">
                        <img src="<%=request.getContextPath()%>/images/ico_edit.gif"
                            alt="Edit" width="16" height="16" />
                    </s:a>
                    </c:if>
                </display:column>
                </c:if>
             </display:table>
        </td></tr>
    </table>
    <div class="actionsrow"><del class="btnwrapper">
    <ul class="btnrow">
    <c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)
    					|| (sessionScope.role == 'SuAbstractor')}">
        <li><a href="#" class="btn" onclick="this.blur();handleCreate();"><span class="btn_img"><span class="add">Add </span></span></a></li>
     </c:if>   
        <c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted'}">
            <li><a href="trialValidationquery.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
        </c:if>    
        <c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Accepted'}">
            <li><a href="milestone.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
        </c:if>    
        <li><a href="manageAccrualAccess.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li> 
    </ul>
    </del></div>
</s:form></div>
</body>
</html>