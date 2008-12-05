<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="arms.main.title" /></title>
<s:head />
<script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>

</head>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
function handleEdit(rowId){
    document.diseaseForm.selectedStudyDiseaseIdentifier.value = rowId;
    document.diseaseForm.action="diseaseedit.action";
    document.diseaseForm.submit(); 
}
function handleDelete(rowId){
    input_box=confirm("Click OK to remove the disease from the study.  Cancel to abort.");
    if (input_box==true){
        document.diseaseForm.selectedStudyDiseaseIdentifier.value = rowId;
        document.diseaseForm.action="diseasedelete.action";
        document.diseaseForm.submit();
    }
}
function handleCreate(){
    document.diseaseForm.action="diseasecreate.action";
    document.diseaseForm.submit(); 
}
</SCRIPT>

<body onload="setFocusToFirstControl();">
<h1><fmt:message key="disease.main.title" /></h1>

<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box">
    <pa:sucessMessage /> 
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
    
    <s:form name="diseaseForm">
        <s:hidden name="selectedStudyDiseaseIdentifier"/> 
    <h2>
        <fmt:message key="disease.details.title"/>
    </h2>
    <table class="form">
        <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
        <tr>
            <td colspan="2">
            <display:table name="diseaseList" id="row" class="data" sort="list" pagesize="10">
                <display:column property="preferredName" sortable="true"
                    titleKey="disease.preferredName" headerClass="sortable"  />
                <display:column property="code" sortable="true"
                    titleKey="disease.code" headerClass="sortable" />
                <display:column property="conceptId" sortable="true"
                    titleKey="disease.conceptId" headerClass="sortable" />
                <display:column property="menuDisplayName" sortable="true"
                    titleKey="disease.menuDisplayName" headerClass="sortable" />
                <display:column property="parentPreferredName" sortable="true"
                    titleKey="disease.parentPreferredName" headerClass="sortable" />
                <display:column property="lead" sortable="true"
                    titleKey="disease.lead" headerClass="sortable" />
                <display:column titleKey="disease.edit" class="action">
                    <s:a href="#" onclick="handleEdit(%{#attr.row.studyDiseaseIdentifier})">
                        <img src="<%=request.getContextPath()%>/images/ico_edit.gif"
                            alt="Edit" width="16" height="16" />
                    </s:a>
                </display:column>
                <display:column titleKey="disease.delete" class="action">
                    <s:a href="#" onclick="handleDelete(%{#attr.row.studyDiseaseIdentifier})">
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
        <li><a href="#" class="btn"
            onclick="this.blur();"><span class="btn_img"><span
            class="next">Next</span></span></a></li>
    </ul>
    </del></div>
</s:form></div>
</body>
</html>