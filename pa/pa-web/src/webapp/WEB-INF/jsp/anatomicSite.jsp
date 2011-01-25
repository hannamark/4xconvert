<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="anatomicSite.details.title" /></title>
<s:head />
<link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all" />
<link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
<script type="text/javascript" src="<c:url value='/scripts/js/scriptaculous.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>

<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}

function handleDelete(code){
    input_box=confirm("Click OK to remove the Summary 4 Anatomic Site from the study.  Cancel to abort.");
    if (input_box==true){
        document.anatomicSiteForm.selectedRowIdentifier.value = code;
        document.anatomicSiteForm.action="anatomicSitedelete.action";
        document.anatomicSiteForm.submit();
    }
}
function handleCreate(){
    document.anatomicSiteForm.action="anatomicSitecreate.action";
    document.anatomicSiteForm.submit();
}
</SCRIPT>
</head>
<body>
<h1><fmt:message key="anatomicSite.details.title"/></h1>
<c:set var="topic" scope="request" value="anatomic_site"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box">
    <pa:sucessMessage />
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>

    <s:form name="anatomicSiteForm">
        <s:hidden name="selectedRowIdentifier"/>
    <h2>
        <fmt:message key="anatomicSite.details.title"/>
    </h2>
    <table class="form">
        <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
        <tr>
            <td colspan="2">
            <s:set name="anatomicSiteList" value="anatomicSiteList" scope="request"/>
            <display:table name="anatomicSiteList" id="row" class="data" sort="list" 
                pagesize="200" requestURI="anatomicSite.action" defaultorder="ascending" defaultsort="1">
                <display:column escapeXml="true" property="code" sortable="true"
                    titleKey="anatomicSite.code" headerClass="sortable" />
                <c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)
                						|| (sessionScope.role == 'SuAbstractor')}">
                    <display:column titleKey="anatomicSite.delete" headerClass="centered" class="action">
                        <s:a href="#" onclick="handleDelete('%{#attr.row.code}')">
                            <img src="<%=request.getContextPath()%>/images/ico_delete.gif"
                                alt="Delete" width="16" height="16" />
                        </s:a>
                    </display:column>
                </c:if>
            </display:table>
        </td>
        </tr>
    </table>
    <div class="actionsrow"><del class="btnwrapper">
    <ul class="btnrow">
        <c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)
        						|| (sessionScope.role == 'SuAbstractor')}">
        <li><a href="#" class="btn" onclick="this.blur();handleCreate();"><span
                class="btn_img"><span class="add">Add </span></span></a></li>
        </c:if>
    </ul>
    </del></div>
</s:form></div>
</body>
</html>