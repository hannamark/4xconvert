<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="disease.details.title" /></title>
<s:head />
<link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all" />
<link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
<script type="text/javascript" src="<c:url value='/scripts/js/scriptaculous.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>

<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
function handleView(diseaseId){
    var url = '/pa/protected/popupDiseaseDetails.action?diseaseId='+diseaseId;
    showPopWin(url, 900, 400, '', 'Disease');
}
function handleEdit(rowId){
    document.diseaseForm.selectedRowIdentifier.value = rowId;
    document.diseaseForm.action="diseaseedit.action";
    document.diseaseForm.submit(); 
}
function handleDelete(rowId){
    input_box=confirm("Click OK to remove the disease from the study.  Cancel to abort.");
    if (input_box==true){
        document.diseaseForm.selectedRowIdentifier.value = rowId;
        document.diseaseForm.action="diseasedelete.action";
        document.diseaseForm.submit();
    }
}
function handleCreate(){
    document.diseaseForm.action="diseasecreate.action";
    document.diseaseForm.submit(); 
}
</SCRIPT>
</head>
<body onload="setFocusToFirstControl();">
<h1><fmt:message key="disease.details.title"/></h1>

<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box">
    <pa:sucessMessage /> 
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
    
    <s:form name="diseaseForm">
        <s:hidden name="selectedRowIdentifier"/> 
    <h2>
        <fmt:message key="disease.details.title"/>
    </h2>
    <table class="form">
        <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
        <tr>
            <td colspan="2">
            <c:if test="${fn:length(diseaseList) > 10}">
            <div style="overflow:auto; height:356px;width:968px;">
            </c:if>
            <display:table name="diseaseList" id="row" class="data" sort="list" pagesize="200" requestURI="disease.action">
                <display:column property="preferredName" sortable="true"
                    titleKey="disease.preferredName" headerClass="sortable"/>
                <display:column titleKey="disease.view" headerClass="centered" class="action">
                    <s:a href="#" onclick="handleView(%{#attr.row.diseaseIdentifier})">
                        <img src="<%=request.getContextPath()%>/images/ico_search.gif"
                            alt="View" width="16" height="16" />
                    </s:a>
                </display:column>
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
                <display:column titleKey="disease.edit" headerClass="centered" class="action">
                    <s:a href="#" onclick="handleEdit(%{#attr.row.studyDiseaseIdentifier})">
                        <img src="<%=request.getContextPath()%>/images/ico_edit.gif"
                            alt="Edit" width="16" height="16" />
                    </s:a>
                </display:column>
                <display:column titleKey="disease.delete" headerClass="centered" class="action">
                    <s:a href="#" onclick="handleDelete(%{#attr.row.studyDiseaseIdentifier})">
                        <img src="<%=request.getContextPath()%>/images/ico_delete.gif"
                            alt="Delete" width="16" height="16" />
                    </s:a>
                </display:column>
            </display:table>
             <c:if test="${fn:length(diseaseList) > 10}">
            </div>
            </c:if></td>
        </tr>
    </table>
    <div class="actionsrow"><del class="btnwrapper">
    <ul class="btnrow">
        <li><a href="#" class="btn" onclick="this.blur();handleCreate();"><span
                class="btn_img"><span class="add">Add </span></span></a></li>
        <li>
            <a href="eligibilityCriteriaquery.action" class="btn" onclick="this.blur();"><span class="btn_img">
                <span class="back">Back</span></span></a>
        </li>
        <li>
            <a href="trialInterventions.action" class="btn" onclick="this.blur();">
                <span class="btn_img"><span class="next">Next</span></span></a>
        </li>
    </ul>
    </del></div>
</s:form></div>
</body>
</html>