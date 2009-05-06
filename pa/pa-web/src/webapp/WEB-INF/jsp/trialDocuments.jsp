 <!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="trialDocument.title"/></title>
	<s:head />
</head>
 <body onload="setFocusToFirstControl();">

 <h1><fmt:message key="trialDocument.title"/></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
 <div class="box"> 
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form><s:actionerror/>
    <h2><fmt:message key="trialDocument.subtitle" /></h2>
    <s:if test="trialDocumentList != null">
    <display:table name="${trialDocumentList}" id="row" class="data" sort="list"  pagesize="10" requestURI="trialDocumentquery.action" export="false">    
	    <display:column titleKey="trialDocument.fileName" sortable="true" headerClass="sortable" >
	    <s:url id="url" action="trialDocumentsaveFile"><s:param name="id" value="%{#attr.row.id}" /></s:url>
	     <s:a href="%{url}"><s:property value="%{#attr.row.fileName}" /></s:a>
	    </display:column> 
	    <display:column titleKey="trialDocument.type" property="typeCode" sortable="true" headerClass="sortable" />
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
    	</display:table>
  </s:if> 
		<div class="actionsrow">
			<del class="btnwrapper">
				<ul class="btnrow">
					<li><s:a href="trialDocumentinput.action" cssClass="btn"><span class="btn_img"><span class="add">Add</span></span></s:a></li>
					<s:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted'}">
						<li><a href="studyProtocolview.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
						<li><a href="studyOverallStatus.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Next</span></span></a></li>
				</s:if>
				<s:else>
					<li><a href="collaborators.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
					<s:if test="${sessionScope.trialSummary.studyProtocolType  == 'InterventionalStudyProtocol'}">
           				<li><a href="interventionalStudyDesigndetailsQuery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
        			</s:if>
        			<s:else>
          				<li><a href="observationalStudyDesigndetailsQuery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
        			</s:else>
				</s:else>
				</ul>	
			</del>
		</div>
		           
  	</s:form>
   </div>
 </body>
 </html>
