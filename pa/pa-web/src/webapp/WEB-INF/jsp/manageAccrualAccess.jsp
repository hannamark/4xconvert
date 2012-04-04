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
<c:set var="topic" scope="request" value="accrualaccess"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box">
    <pa:sucessMessage />
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>

    <s:form name="listForm">
        <pa:studyUniqueToken/>
        <s:hidden name="selectedRowIdentifier"/>
    <h2>
        <fmt:message key="manageAccrualAccess.list.title"/>
    </h2>
    <table class="form">
        <tr><td colspan="2">
            <s:set name="accessList" value="accessList" scope="request"/>
            <display:table name="accessList" id="row" class="data" sort="list" pagesize="10" requestURI="manageAccrualAccess.action">
                <display:column escapeXml="true" property="userName" sortable="true" titleKey="manageAccrualAccess.userName"/>
                <display:column escapeXml="true" property="emailAddress" sortable="true" titleKey="manageAccrualAccess.email"/>
                <display:column escapeXml="true" property="phoneNumber" sortable="true" titleKey="manageAccrualAccess.phone"/>
                <display:column escapeXml="true" property="siteName" sortable="true" titleKey="manageAccrualAccess.siteName"/>
                <display:column escapeXml="true" property="siteRecruitmentStatus" sortable="true" titleKey="manageAccrualAccess.siteRecruitmentStatus"/>
                <display:column escapeXml="true" property="statusCode" sortable="true" titleKey="manageAccrualAccess.statusCode"/>
                <pa:displayWhenCheckedOut>
                    <display:column titleKey="manageAccrualAccess.edit" headerClass="centered" class="action">
                        <s:a href="javascript:void(0)" onclick="handleEdit(%{#attr.row.identifier})">
                            <img src='<c:url value="/images/ico_edit.gif"/>' alt="Edit" width="16" height="16" />
                        </s:a>
                    </display:column>
                </pa:displayWhenCheckedOut>
             </display:table>
        </td></tr>
    </table>
    <div class="actionsrow"><del class="btnwrapper">
    <ul class="btnrow">
        <pa:displayWhenCheckedOut>
            <li><a href="javascript:void(0)" class="btn" onclick="this.blur();handleCreate();"><span class="btn_img"><span class="add">Add </span></span></a></li>
        </pa:displayWhenCheckedOut>
    </ul>
    </del></div>
</s:form></div>
</body>
</html>