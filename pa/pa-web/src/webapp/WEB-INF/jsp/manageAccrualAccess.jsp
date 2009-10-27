<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="manageAccrualAccess.title" /></title>
<s:head />

<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
// this function is called from body onload in main.jsp (decorator) 
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page' 
}
function handleCreate(){
    document.listForm.action="manageAccrualAccesscreate.action";
    document.listForm.submit(); 
}
function handleEdit(rowId){
    document.listForm.selectedRowIdentifier.value = rowId;
    document.listForm.action="manageAccrualAccessedit.action";
    document.listForm.submit(); 
}
</SCRIPT>
</head>
<body>
<h1><fmt:message key="manageAccrualAccess.title"/></h1>
<c:set var="topic" scope="request" value="accrual_access"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box">
    <pa:sucessMessage /> 
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
    
    <s:form name="listForm">
        <s:hidden name="selectedRowIdentifier"/> 
    <h2>
        <fmt:message key="manageAccrualAccess.list.title"/>
    </h2>
    <table class="form">
        <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
        <tr><td colspan="2">
            <s:set name="accessList" value="accessList" scope="request"/>
            <display:table name="accessList" id="row" class="data" sort="list" pagesize="10" requestURI="manageAccrualAccess.action">
                <display:column property="userName" sortable="false" titleKey="manageAccrualAccess.userName"/>
                <display:column property="email" sortable="false" titleKey="manageAccrualAccess.email"/>
                <display:column property="phone" sortable="false" titleKey="manageAccrualAccess.phone"/>
                <display:column property="siteName" sortable="false" titleKey="manageAccrualAccess.siteName"/>
                <display:column property="siteRecruitmentStatus" sortable="false" titleKey="manageAccrualAccess.siteRecruitmentStatus"/>
                <display:column property="statusCode.code" sortable="false" titleKey="manageAccrualAccess.statusCode"/>
                <c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)
                					|| (sessionScope.role == 'SuAbstractor')}">
                <display:column titleKey="magageAccrualAccess.edit" headerClass="centered" class="action">
                    <s:a href="#" onclick="handleEdit(%{#attr.row.id})">
                        <img src="<%=request.getContextPath()%>/images/ico_edit.gif"
                            alt="Edit" width="16" height="16" />
                    </s:a>
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
        <li><a href="onhold.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
        <c:choose>
        <c:when test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted'}">
        <li><a href="trialDocumentquery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
        </c:when>
        <c:otherwise>
        <li><a href="generalTrialDesignquery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
        </c:otherwise>
        </c:choose>
    </ul>
    </del></div>
</s:form></div>
</body>
</html>