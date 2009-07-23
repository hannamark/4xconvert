<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="participatingOrganizations.collaborators.title" /></title>
<s:head />
<script type="text/javascript"
	src='<c:url value="/scripts/js/coppa.js"/>'></script>
<script type="text/javascript"
	src='<c:url value="/scripts/js/scriptaculous.js"/>'></script>

</head>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
function handleEdit(studyResourcingId){
    document.collaboratorsForm.cbValue.value = studyResourcingId;
    document.collaboratorsForm.action="collaboratorsedit.action";
    document.collaboratorsForm.submit(); 
}
function handleDelete(studyResourcingId){
    input_box=confirm("Click OK to delete the the organization as a collaborator in the study.  Cancel to abort.");
    if (input_box==true){
	    document.collaboratorsForm.cbValue.value = studyResourcingId;
	    document.collaboratorsForm.action="collaboratorsdelete.action";
	    document.collaboratorsForm.submit();
	}
}
function handleCreate(){
    document.collaboratorsForm.action="collaboratorscreate.action";
    document.collaboratorsForm.submit(); 
}
</SCRIPT>

<body onload="setFocusToFirstControl();">
<h1><fmt:message key="participatingOrganizations.collaborators.title" /></h1>

<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box"><pa:sucessMessage /> <s:if
	test="hasActionErrors()">
	<div class="error_msg"><s:actionerror /></div>
</s:if> <s:form name="collaboratorsForm">
	<h2><fmt:message
		key="participatingOrganizations.collaborators.title" /></h2>
	<table class="form">
		<%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
		<tr>
			<td colspan="2"><input type="hidden" name="cbValue" /> 
			<c:if test="${fn:length(organizationList) > 10}">
			<div style="overflow:auto; height:356px;width:968px;">
			</c:if>
			<display:table
				name="organizationList" id="row" class="data" pagesize="200">
				<display:column property="nciNumber"
                    titleKey="participatingOrganizations.nciNumber" class="sortable" />
				<display:column property="name"
					titleKey="participatingOrganizations.name" class="sortable" />
				<display:column property="status"
                    titleKey="participatingOrganizations.status" class="sortable" />	
			    <display:column property="functionalRole"
					titleKey="participatingOrganizations.functionalRole"
					class="sortable" />
				<display:column titleKey="participatingOrganizations.edit"
					headerClass="centered" class="action">
					<s:a href="#" onclick="handleEdit(%{#attr.row.id})">
						<img src="<%=request.getContextPath()%>/images/ico_edit.gif"
							alt="Edit" width="16" height="16" />
					</s:a>
				</display:column>
				<display:column titleKey="participatingOrganizations.unlink"
					headerClass="centered" class="action">
					<s:a href="#" onclick="handleDelete(%{#attr.row.id})">
						<img src="<%=request.getContextPath()%>/images/ico_delete.gif"
							alt="Un-link" width="16" height="16" />
					</s:a>
				</display:column>
			</display:table>
			<c:if test="${fn:length(organizationList) > 10}">
			</div>
			</c:if>
			</td>
		</tr>
	</table>
	<div class="actionsrow"><del class="btnwrapper">
	<ul class="btnrow">
		<li><a href="#" class="btn" onclick="this.blur();handleCreate();"><span
			class="btn_img"><span class="add">Add </span></span></a></li>
		<li><a href="participatingOrganizations.action" class="btn"
			onclick="this.blur();"><span class="btn_img"><span
			class="back">Back</span></span></a></li>
		<li><a href="trialDocumentquery.action" class="btn"
			onclick="this.blur();"><span class="btn_img"><span
			class="next">Next</span></span></a></li>
	</ul>
	</del></div>
</s:form></div>
</body>
</html>