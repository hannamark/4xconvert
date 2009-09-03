<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="manageAccrualAccess.title" /></title>
<s:head />
<script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
</head>
<body onload="setFocusToFirstControl();">
<h1><fmt:message key="manageAccrualAccess.title" /></h1>
<c:set var="topic" scope="request" value="manage_accrual_access"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box">
    <pa:sucessMessage /> 
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
    <h2><fmt:message key="onhold.title"/></h2>
    <table class="form">
        <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%> 
    </table>
</div>
</body>
</html>