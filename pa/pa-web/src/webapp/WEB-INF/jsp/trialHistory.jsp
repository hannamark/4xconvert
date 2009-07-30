<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="trialHistory.title" /></title>
<s:head />
<script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>

<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
function handleEdit(rowId){
    document.listForm.selectedRowIdentifier.value = rowId;
    document.listForm.action="trialHistoryedit.action";
    document.listForm.submit(); 
}
function handlePopup(a,b,c)  // write corresponding content to the popup window
  {
    document.listForm.action="trialHistoryopen.action?studyProtocolii="+a+"&docii="+b+"&docFileName="+c;
    document.listForm.submit(); 
 }

</SCRIPT>
</head>
<body onload="setFocusToFirstControl();">
<h1><fmt:message key="trialHistory.title"/></h1>
<c:set var="topic" scope="request" value="trial_history"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box">
    <pa:sucessMessage /> 
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
    
    <s:form name="listForm">
        <s:hidden name="selectedRowIdentifier"/> 
    <h2>
        <fmt:message key="trialHistory.title"/>
    </h2>
    <table class="form">
        <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
        <tr><td colspan="2">
            <display:table name="trialHistoryWebDTO" id="row" class="data" sort="list" defaultsort="1" defaultorder="ascending" pagesize="10" requestURI="trialHistory.action">
                <display:column property="submissionNumber" sortable="false" titleKey="trialHistory.submissionNumber"/>
                <display:column property="type" sortable="false" titleKey="trialHistory.type"/>
                <display:column property="amendmentNumber" sortable="false" titleKey="trialHistory.amendmentNumber"/>
                <display:column property="amendmentDate" sortable="false" titleKey="trialHistory.amendmentDate" format="{0,date,MM/dd/yyyy}" />
                <display:column property="submissionDate" sortable="false" titleKey="trialHistory.submissionDate" format="{0,date,MM/dd/yyyy}" />
                <display:column property="amendmentReasonCode" sortable="false" titleKey="trialHistory.amendmentReasonCode"/>
                <display:column property="documents" sortable="false" style="word-wrap: break-word"  titleKey="trialHistory.documents"/>
                <display:column title="Action" headerClass="centered" class="action">
                <s:if test="%{#attr.row.submissionNumber != 1}">
                     <s:a href="#" onclick="handleEdit(%{#attr.row.identifier})">
                        <img src="<%=request.getContextPath()%>/images/ico_edit.gif"
                            alt="Edit" width="16" height="16" />
                    </s:a>
                </s:if>    
                    </display:column>
             </display:table>
        </td></tr>
    </table>
   </s:form></div>
</body>
</html>