<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="participatingOrganizations.title" /></title>
<s:head />
<script type="text/javascript"
    src='<c:url value="/scripts/js/coppa.js"/>'></script>
<script type="text/javascript"
    src='<c:url value="/scripts/js/scriptaculous.js"/>'></script>

</head>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
function handleEdit(studyResourcingId){
    document.studyOverallStatus.cbValue.value = studyResourcingId;
    document.studyOverallStatus.action="trialInterventionsedit.action";
    document.studyOverallStatus.submit(); 
}
function handleDelete(studyResourcingId){
    input_box=confirm("Click OK to remove the intervention from the Study.  Cancel to Abort.");
    if (input_box==true){
        document.studyOverallStatus.cbValue.value = studyResourcingId;
        document.studyOverallStatus.action="trialInterventionsdelete.action";
        document.studyOverallStatus.submit();
    }
}
function handleCreate(){
    document.studyOverallStatus.action="trialInterventionscreate.action";
    document.studyOverallStatus.submit(); 
}
</SCRIPT>

<body onload="setFocusToFirstControl();">
<h1><fmt:message key="interventions.main.title" /></h1>

<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box"><pa:sucessMessage /> <s:if
    test="hasActionErrors()">
    <div class="error_msg"><s:actionerror /></div>
</s:if> <s:form name="studyOverallStatus">
    <h2><fmt:message
        key="interventions.details.title" /></h2>
    <table class="form">
        <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
        <tr>
            <td colspan="2"><input type="hidden" name="cbValue" /> <display:table
                name="interventionsList" id="row" class="data">
                <display:column property="name"
                    titleKey="interventions.name" class="sortable" />
                <display:column property="otherNames"
                    titleKey="interventions.otherNames" class="sortable" />
                <display:column property="description"
                    titleKey="interventions.description"
                    class="sortable" />
                <display:column property="leadIndicator"
                    titleKey="interventions.leadIndicator"
                    class="sortable" />
                <display:column titleKey="interventions.edit"
                    class="action">
                    <s:a href="#" onclick="handleEdit(%{#attr.row.id})">
                        <img src="<%=request.getContextPath()%>/images/ico_edit.gif"
                            alt="Edit" width="16" height="16" />
                    </s:a>
                </display:column>
                <display:column titleKey="interventions.delete"
                    class="action">
                    <s:a href="#" onclick="handleDelete(%{#attr.row.id})">
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
        <li><a href="interventionalStudyDesigndetailsQuery.action" class="btn"
            onclick="this.blur();"><span class="btn_img"><span
            class="back">Back</span></span></a></li>
        <li><a href="#" class="btn"
            onclick="this.blur();"><span class="btn_img"><span
            class="next">Next</span></span></a></li>
    </ul>
    </del></div>
</s:form></div>
</body>
</html>