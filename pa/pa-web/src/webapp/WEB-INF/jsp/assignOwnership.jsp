<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="assignOwbership.title" /></title>
<s:head />

<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
// this function is called from body onload in main.jsp (decorator) 
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page' 
}

</SCRIPT>
</head>
<body>
<h1><fmt:message key="assignOwbership.title"/></h1>
<c:set var="topic" scope="request" value="assign_ownership"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box">
    <pa:sucessMessage /> 
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
    
    <s:form name="assignOwnershipForm">
        <s:hidden name="currentAction"/>
        <s:set name="users" value="users" scope="request"/>
        <display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" pagesize="10" id="row"
             name="users" export="false">
            <display:column titleKey="pending.userFirstName" property="regUser.firstName" headerClass="sortable"/>
            <display:column titleKey="pending.userLastName" property="regUser.lastName" headerClass="sortable"/>
            <display:column titleKey="pending.emailAddress" property="regUser.emailAddress" headerClass="sortable"/>
            <display:column class="title" titleKey="studyProtocol.action">
                <c:choose>
                    <c:when test="${row.owner == true}">
                        Trial Owner
                    </c:when>
                    <c:otherwise>
                    <a href="assignOwnershipsave.action?userId=${row.regUser.id}">Assign Ownership</a>
                    </c:otherwise>
                </c:choose>
            </display:column>
        </display:table>
</s:form></div>
</body>
</html>