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
 	//openPI('trialFundingDelete.action', 'popup');
 	document.forms[0].action="trialFundingDelete.action";
    document.forms[0].submit(); 
}

</SCRIPT>

<body onload="setFocusToFirstControl();">

 <h1><fmt:message key="trialFunding.title" /></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
    <s:form><s:actionerror/>
    <h2><fmt:message key="trialFunding.subtitle" /></h2>
    <s:if test="trialFundingList != null">
    <input type="hidden" name="page" />
    <input type="hidden" name="cbValue" />
	<display:table name="${trialFundingList}" id="row" class="data" sort="list"  pagesize="5" requestURI="trialFundingquery.action" export="false">    
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
					<li><a href="studyOverallStatus.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
					<li><a href="studyProtocolview.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
				</ul>	
			</del>
		</div>
		           
  	</s:form>
   </div>
 </body>
 </html>