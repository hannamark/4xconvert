<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="trialFunding.title"/></title>
	<s:head />
</head>
<SCRIPT LANGUAGE="JavaScript">


function handleAction(studyResourcingId){   
	document.forms[0].cbValue.value = studyResourcingId;
    document.forms[0].page.value = "Edit";
    document.forms[0].action="trialFundingedit.action";
    document.forms[0].submit();  
}
function handleDelete(studyResourcingId){
	document.forms[0].cbValue.value = studyResourcingId;
 	document.forms[0].page.value = "Delete";
 	document.forms[0].action="trialFundingdelete.action";
    document.forms[0].submit(); 
}

</SCRIPT>

<body onload="setFocusToFirstControl();">
<c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted'}">
<c:set var="topic" scope="request" value="review_funding"/>
</c:if>
<c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  != 'Submitted'}">
<c:set var="topic" scope="request" value="abstract_funding"/>
</c:if>
 <h1><fmt:message key="trialFunding.title" /></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
  <pa:sucessMessage/>
    <s:form><s:actionerror/>
    <h2><fmt:message key="trialFunding.subtitle" /></h2>
    <s:if test="trialFundingList != null">
    <input type="hidden" name="page" />
    <input type="hidden" name="cbValue" />
	<display:table name="${trialFundingList}" id="row" class="data" sort="list"  pagesize="200" requestURI="trialFundingquery.action" export="false">    
	    <display:column titleKey="trialFunding.funding.mechanism" property="fundingMechanismCode" sortable="true" headerClass="sortable" />
	    <display:column titleKey="trialFunding.institution.code" property="nihInstitutionCode" sortable="true" headerClass="sortable" />
	    <display:column titleKey="trialFunding.serial.number" property="serialNumber"  sortable="true" headerClass="sortable" />
	    <display:column titleKey="studyProtocol.monitorCode" property="nciDivisionProgramCode" sortable="true" headerClass="sortable" />
	    <display:column title="Edit" class="action">
    		<s:a href="#" onclick="handleAction(%{#attr.row.id})"><img src="<%=request.getContextPath()%>/images/ico_edit.gif" alt="Edit" width="16" height="16"/></s:a>
		</display:column>
		<display:column title="Delete" class="action">
    		<s:a href="#" onclick="handleDelete(%{#attr.row.id})"><img src="<%=request.getContextPath()%>/images/ico_delete.gif" alt="Delete" width="16" height="16"/></s:a>
    	</display:column>
	</display:table>
  </s:if> 
		<div class="actionsrow">
			<del class="btnwrapper">
				<ul class="btnrow">
					<li><s:a href="trialFunding.action" cssClass="btn"><span class="btn_img"><span class="add">Add</span></span></s:a></li>
					<c:choose>
                    <c:when test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted'}">
						<li><a href="studyOverallStatus.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
						<li><a href="trialIndidequery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Next</span></span></a></li>
					</c:when>
					<c:otherwise>
						<li><a href="studyOverallStatus.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
						<li><a href="participatingOrganizations.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
					</c:otherwise>
					</c:choose>
				</ul>	
			</del>
		</div>
		           
  	</s:form>
   </div>
 </body>
 </html>