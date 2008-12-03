<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="interventions.main.title" /></title>
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
    input_box=confirm("Click OK to remove the intervention from the Study.  Cancel to Abort.");
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
<h1><fmt:message key="interventions.main.title" /></h1>

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
            <display:table name="interventionsList" id="row" class="data" sort="list" pagesize="10" 
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
                <display:column titleKey="interventions.edit" class="action">
                    <s:a href="#" onclick="handleEdit(%{#attr.row.identifier})">
                        <img src="<%=request.getContextPath()%>/images/ico_edit.gif"
                            alt="Edit" width="16" height="16" />
                    </s:a>
                </display:column>
                <display:column titleKey="interventions.delete" class="action">
                    <s:a href="#" onclick="handleDelete(%{#attr.row.identifier})">
                        <img src="<%=request.getContextPath()%>/images/ico_cancel.gif"
                            alt="Delete" width="16" height="16" />
                    </s:a>
                </display:column>
            </display:table></td>
        </tr>
    </table>
    <div class="actionsrow"><del class="btnwrapper">
    <ul class="btnrow">
        <li><a href="#" class="btn" onclick="this.blur();handleCreate();"><span
            class="btn_img"><span class="add">Add </span></span></a></li>
        <li><a href="disease.action" class="btn"
            onclick="this.blur();"><span class="btn_img"><span
            class="next">Back</span></span></a></li>
		<s:if test="${sessionScope.trialSummary.studyProtocolType  == 'InterventionalStudyProtocol'}">
           <li><a href="trialArms.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Next</span></span></a></li>
        </s:if>
        <s:else>
          <li><a href="trialArmsobservational.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Next</span></span></a></li>
        </s:else>            
    </ul>
    </del></div>
</s:form></div>
</body>
</html>