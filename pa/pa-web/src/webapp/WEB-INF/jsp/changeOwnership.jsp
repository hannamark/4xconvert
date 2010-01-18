<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="changeOwbership.title" /></title>
<s:head />

<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
// this function is called from body onload in main.jsp (decorator) 
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page' 
}
function handleAction() {
	var newOwnerId = document.getElementById('csmUserId').value;
	if (newOwnerId == null || newOwnerId == "") {
	    alert("Please Select a valid User Name");
		return;
	}
	confirm_box = confirm("Ownership is changing from FROM - <c:out value ='${sessionScope.trialSummary.userLastCreated}'/> TO "
			+ newOwnerId);
    if (confirm_box==true){
        document.changeOwnershipForm.action="changeOwnershipsave.action?csmUserId="+document.getElementById('csmUserId').value;
        document.changeOwnershipForm.submit();
    } 
}

</SCRIPT>
</head>
<body>
<h1><fmt:message key="changeOwbership.title"/></h1>
<c:set var="topic" scope="request" value="change_ownership"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box">
    <pa:sucessMessage /> 
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
    
    <s:form name="changeOwnershipForm">
        <s:hidden name="currentAction"/>
        <table class="form">
            <tr>
                <td class="label"><s:label><fmt:message key="changeOwnership.ownerUserName"/></s:label></td>
                <td class="value" style="width: 250px">
                <c:out value ='${sessionScope.trialSummary.userLastCreated}'/></td>
            </tr>
            <tr>
                <td class="label"><s:label><fmt:message key="changeOwnership.toUserName"/></s:label><span class="required">*</span></td>
                <td class="value" style="width: 250px">
                <s:select id="csmUserId" headerKey="" headerValue="--Select--" 
                                   list="csmUserNames" listKey="keyValue" listValue="name" />
                        
                </td>
            </tr>
        </table>
    <div class="actionsrow"><del class="btnwrapper">
    <ul class="btnrow">
        <c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)
                            || (sessionScope.role == 'SuAbstractor')}">
        <li><a href="#" class="btn" onclick="this.blur();handleAction();"><span class="btn_img"><span class="save">Save</span></span></a></li>
        </c:if>
    </ul>
    </del></div>
</s:form></div>
</body>
</html>