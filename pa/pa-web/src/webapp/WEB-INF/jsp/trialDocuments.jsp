 <!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="trialDocument.title"/></title>
	<s:head />
</head>
<SCRIPT LANGUAGE="JavaScript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}
</SCRIPT>
 <body>
<c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted'}">
<c:set var="topic" scope="request" value="reviewdocs"/>
</c:if>
<c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  != 'Submitted'}">
<c:set var="topic" scope="request" value="abstractdocs"/>
</c:if>
 <h1><fmt:message key="trialDocument.title"/></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
 <div class="box">
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form>
        <s:actionerror/>
        <pa:studyUniqueToken/>
    <h2><fmt:message key="trialDocument.subtitle" /></h2>
    <s:if test="trialDocumentList != null">
    <s:set name="trialDocumentList" value="trialDocumentList" scope="request"/>
    <display:table name="${trialDocumentList}" id="row" class="data" sort="list"  pagesize="10" requestURI="trialDocumentquery.action" export="false">
	    <display:column titleKey="trialDocument.fileName" sortable="true" headerClass="sortable" >
	       <s:url id="url" action="trialDocumentsaveFile"><s:param name="id" value="%{#attr.row.id}" /></s:url>
	       <s:a href="%{url}"><s:property value="%{#attr.row.fileName}" /></s:a>
	    </display:column>
	    <display:column escapeXml="true" titleKey="trialDocument.type" property="typeCode" sortable="true" headerClass="sortable" />
	    <c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)
	    					|| (sessionScope.role == 'SuAbstractor')}">
	    <display:column title="Edit" class="action">
    		<s:url id="url" action="trialDocumentedit"><s:param name="id" value="%{#attr.row.id}" /> <s:param name="page" value="%{'Edit'}"/></s:url>
    		<s:a href="%{url}"><img src="<%=request.getContextPath()%>/images/ico_edit.gif" alt="Edit" width="16" height="16"/></s:a>
    	</display:column>
    	<display:column title="Delete" class="action">
		<s:if test="%{#attr.row.typeCode.equals('Protocol Document')}">
		</s:if>
		<s:elseif test="%{#attr.row.typeCode.equals('IRB Approval Document')}">
		</s:elseif>
		<s:elseif test="%{#attr.row.typeCode.equals('Change Memo Document')}">
        </s:elseif>
		<s:else>
			<s:url id="url" action="trialDocumentdelete"><s:param name="id" value="%{#attr.row.id}" /></s:url>
    		<s:a href="%{url}"><img src="<%=request.getContextPath()%>/images/ico_delete.gif" alt="Delete" width="16" height="16"/></s:a>
		</s:else>
    	</display:column>
    	</c:if>
    	</display:table>
  </s:if>
		<div class="actionsrow">
			<del class="btnwrapper">
				<ul class="btnrow">
					<c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)
										|| (sessionScope.role == 'SuAbstractor')}">
					<li><s:a href="trialDocumentinput.action" cssClass="btn"><span class="btn_img"><span class="add">Add</span></span></s:a></li>
					</c:if>
				</ul>
			</del>
		</div>
  	</s:form>
   </div>
 </body>
 </html>
