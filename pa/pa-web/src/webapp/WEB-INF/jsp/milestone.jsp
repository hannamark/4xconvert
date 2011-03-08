<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="milestone.details.title" /></title>
<s:head />

<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}
function handleCreate(){
    document.milestoneForm.action="milestonecreate.action";
    document.milestoneForm.submit();
}
</SCRIPT>
</head>
<body>
<h1><fmt:message key="milestone.details.title"/></h1>
<c:set var="topic" scope="request" value="trialmilestones"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box">
    <pa:sucessMessage />
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>

    <s:form name="milestoneForm">
        <s:hidden name="selectedRowIdentifier"/>
    <h2>
        <fmt:message key="milestone.details.title"/>
    </h2>
    <table class="form">
        <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
        <tr>
            <td colspan="2">
            <s:set name="milestoneList" value="milestoneList" scope="request"/>
            <display:table name="milestoneList" id="row" class="data" sort="list" pagesize="200" requestURI="milestone.action">
                <display:column escapeXml="true" property="milestone" sortable="false" titleKey="milestone.milestone" />
                <display:column escapeXml="true" property="date" sortable="false" titleKey="milestone.date" />
                <display:column escapeXml="true" property="comment" sortable="false" titleKey="milestone.comment" />
            </display:table>
              </td>
        </tr>
    </table>
    <div class="actionsrow"><del class="btnwrapper">
    <ul class="btnrow">
        <c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)
        					|| (sessionScope.role == 'SuAbstractor')}">
        <li><a href="#" class="btn" onclick="this.blur();handleCreate();"><span class="btn_img"><span class="add">Add </span></span></a></li>
        </c:if>
        <!--
        <li><a href="studyProtocolview.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
        <li><a href="onhold.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
         -->
    </ul>
    </del></div>
</s:form></div>
</body>
</html>