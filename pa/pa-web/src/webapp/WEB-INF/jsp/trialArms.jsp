<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="arms.main.title" /></title>
<s:head />
<script type="text/javascript"
    src='<c:url value="/scripts/js/coppa.js"/>'></script>
<script type="text/javascript"
    src='<c:url value="/scripts/js/scriptaculous.js"/>'></script>

</head>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
function handleEdit(rowId){
    document.armForm.selectedArmIdentifier.value = rowId;
    document.armForm.action="trialArmsedit.action";
    document.armForm.submit(); 
}
function handleDelete(rowId){
    input_box=confirm("Click OK to remove the arm from the Study.  Cancel to Abort.");
    if (input_box==true){
        document.armForm.selectedArmIdentifier.value = rowId;
        document.armForm.action="trialArmsdelete.action";
        document.armForm.submit();
    }
}
function handleCreate(){
    document.armForm.action="trialArmscreate.action";
    document.armForm.submit(); 
}
</SCRIPT>

<body onload="setFocusToFirstControl();">
<h1><fmt:message key="arms.main.title" /></h1>

<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box"><pa:sucessMessage /> <s:if
    test="hasActionErrors()">
    <div class="error_msg"><s:actionerror /></div>
</s:if> <s:form name="armForm"><s:hidden name="selectedArmIdentifier"/> 
    <h2><fmt:message
        key="arms.details.title" /></h2>
    <table class="form">
        <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
        <tr>
            <td colspan="2"><input type="hidden" name="cbValue" /> 
            <display:table name="armList" id="row" class="data" sort="list" pagesize="10">
                <display:column property="name" sortable="true"
                    titleKey="arms.name" 
                    headerClass="sortable"  />
                <display:column property="type" sortable="true"
                    titleKey="arms.type"
                    headerClass="sortable"  />
                <display:column property="description" sortable="true"
                    titleKey="arms.description"
                    headerClass="sortable" />
                <display:column property="interventions" titleKey="arms.interventions"/>
                <display:column titleKey="arms.edit" class="action">
                    <s:a href="#" onclick="handleEdit(%{#attr.row.identifier})">
                        <img src="<%=request.getContextPath()%>/images/ico_edit.gif"
                            alt="Edit" width="16" height="16" />
                    </s:a>
                </display:column>
                <display:column titleKey="arms.delete" class="action">
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
        <li><a href="#" class="btn"
            onclick="this.blur();"><span class="btn_img"><span
            class="back">Back</span></span></a></li>
        <li><a href="trialInterventions.action" class="btn"
            onclick="this.blur();"><span class="btn_img"><span
            class="next">Next</span></span></a></li>
    </ul>
    </del></div>
</s:form></div>
</body>
</html>