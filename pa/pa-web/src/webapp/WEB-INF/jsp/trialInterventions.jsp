<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="interventions.details.title" /></title>
<s:head />
<script type="text/javascript"
    src='<c:url value="/scripts/js/coppa.js"/>'></script>
<script type="text/javascript"
    src='<c:url value="/scripts/js/scriptaculous.js"/>'></script>

</head>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
function handleEdit(rowId){
    document.interventionForm.selectedRowIdentifier.value = rowId;
    document.interventionForm.action="trialInterventionsedit.action";
    document.interventionForm.submit(); 
}
function handleDelete(rowId){
    input_box=confirm("Click OK to remove the intervention from the study.  Cancel to abort.");
    if (input_box==true){
        document.interventionForm.selectedRowIdentifier.value = rowId;
        document.interventionForm.action="trialInterventionsdelete.action";
        document.interventionForm.submit();
    }
}
function handleCreate(){
    document.interventionForm.action="trialInterventionscreate.action";
    document.interventionForm.submit(); 
}
</SCRIPT>

<body onload="setFocusToFirstControl();">
<h1><fmt:message key="interventions.details.title" /></h1>
<c:set var="topic" scope="request" value="abstract_interventions"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box"><pa:sucessMessage /> <s:if
    test="hasActionErrors()">
    <div class="error_msg"><s:actionerror /></div>
</s:if> <s:form name="interventionForm"><s:hidden name="selectedRowIdentifier"/> 
    <h2><fmt:message
        key="interventions.details.title" /></h2>
    <table class="form">
        <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
        <tr>
            <td colspan="2"><input type="hidden" name="cbValue" /> 
             <c:if test="${fn:length(interventionsList) > 10}">
            <div style="overflow:auto; height:356px;width:968px;">
            </c:if>
            <display:table name="interventionsList" id="row" class="data" sort="list" pagesize="200" 
                    requestURI="trialInterventions.action" export="false">
                <display:column property="name" sortable="true"
                    titleKey="interventions.name" headerClass="sortable"  />
                <display:column property="otherNames" sortable="true"
                    titleKey="interventions.otherNames" headerClass="sortable" />
                <display:column property="description" sortable="true"
                    titleKey="interventions.description"
                    headerClass="sortable" />
                <display:column property="type" sortable="true"
                    titleKey="interventions.type"
                    headerClass="sortable"  />
                <display:column property="leadIndicator" sortable="true"
                    titleKey="interventions.leadIndicator"
                    headerClass="sortable"  />
                <display:column titleKey="interventions.edit" headerClass="centered" class="action">
                    <s:a href="#" onclick="handleEdit(%{#attr.row.plannedActivityIdentifier})">
                        <img src="<%=request.getContextPath()%>/images/ico_edit.gif"
                            alt="Edit" width="16" height="16" />
                    </s:a>
                </display:column>
                <display:column titleKey="interventions.delete" headerClass="centered" class="action">
                    <s:a href="#" onclick="handleDelete(%{#attr.row.plannedActivityIdentifier})">
                        <img src="<%=request.getContextPath()%>/images/ico_delete.gif"
                            alt="Delete" width="16" height="16" />
                    </s:a>
                </display:column>
            </display:table>
            <c:if test="${fn:length(interventionsList) > 10}">
            </div>
            </c:if></td>
        </tr>
    </table>
    <div class="actionsrow"><del class="btnwrapper">
    <ul class="btnrow">
        <li><a href="#" class="btn" onclick="this.blur();handleCreate();"><span
            class="btn_img"><span class="add">Add </span></span></a></li>
        <li><a href="disease.action" class="btn"
            onclick="this.blur();"><span class="btn_img"><span
            class="back">Back</span></span></a></li>
		<c:choose>
        <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'InterventionalStudyProtocol'}">
           <li><a href="trialArms.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
        </c:when>
       <c:otherwise>
          <li><a href="trialArmsobservational.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
        </c:otherwise>
        </c:choose>         
    </ul>
    </del></div>
</s:form></div>
</body>
</html>