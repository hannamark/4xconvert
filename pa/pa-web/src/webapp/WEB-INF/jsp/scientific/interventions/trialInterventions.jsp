<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="interventions.details.title" /></title>
<s:head />
<script type="text/javascript"
    src='<c:url value="/scripts/js/scriptaculous.js"/>'></script>

</head>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}
function handleEdit(rowId, type){
    document.interventionForm.selectedRowIdentifier.value = rowId;
    document.interventionForm.selectedType.value = type;
    document.interventionForm.action="trialInterventionsedit.action";
    document.interventionForm.submit();
}

function handleCreate(){
    document.interventionForm.action="trialInterventionscreate.action";
    document.interventionForm.submit();
}
</SCRIPT>

<body>
<h1><fmt:message key="interventions.details.title" /></h1>
<c:set var="topic" scope="request" value="abstractinterventions"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box"><pa:sucessMessage /><pa:failureMessage/> <s:if
    test="hasActionErrors()">
    <div class="error_msg"><s:actionerror /></div>
</s:if> 
<s:form name="interventionForm">
    <pa:studyUniqueToken/>
    <s:hidden name="selectedRowIdentifier"/>
    <s:hidden name="selectedType"/>
    <h2><fmt:message
        key="interventions.details.title" /></h2>
    <div class="actionstoprow"><del class="btnwrapper">
    <ul class="btnrow">
        <pa:scientificAbstractorDisplayWhenCheckedOut>
            <li><a href="javascript:void(0)" class="btn" onclick="this.blur();handleCreate();"><span class="btn_img"><span class="add">Add </span></span></a></li>
            <s:if test="%{interventionsList != null && !interventionsList.isEmpty()}">
                <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected intervention(s) from the study. Cancel to abort.', 'trialInterventionsdelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected intervention(s) from the study. Cancel to abort.', 'trialInterventionsdelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                <li><pa:toggleDeleteBtn/></li>
            </s:if>            
        </pa:scientificAbstractorDisplayWhenCheckedOut>
    </ul>
    </del></div>
    <table class="form">
        <tr>
            <td colspan="2"><s:hidden name="cbValue" />
            <s:set name="interventionsList" value="interventionsList" scope="request"/>
            <display:table name="interventionsList" id="row" class="data" sort="list" pagesize="200"
                    requestURI="trialInterventions.action" export="false">
                <display:column escapeXml="true" property="name" sortable="true" titleKey="interventions.name" headerClass="sortable"  />
                <display:column escapeXml="true" property="otherNames" sortable="true" titleKey="interventions.otherNames" headerClass="sortable" />
                <display:column escapeXml="true" property="description" sortable="true" titleKey="interventions.description" headerClass="sortable" />
                <display:column escapeXml="true" property="type" sortable="true" titleKey="interventions.type" headerClass="sortable"  />
                <pa:scientificAbstractorDisplayWhenCheckedOut>
                    <display:column titleKey="interventions.edit" headerClass="centered" class="action">
                        <s:a href="javascript:void(0)" onclick="handleEdit(%{#attr.row.plannedActivityIdentifier},'%{#attr.row.type}')">
                            <img src="<c:url value='/images/ico_edit.gif'/>" alt="Edit" width="16" height="16" />
                        </s:a>
                    </display:column>
                    <display:column titleKey="interventions.delete" headerClass="centered" class="action">
                        <s:checkbox name="objectsToDelete" fieldValue="%{#attr.row.plannedActivityIdentifier}" value="%{#attr.row.plannedActivityIdentifier in objectsToDelete}"/>
                    </display:column>
                </pa:scientificAbstractorDisplayWhenCheckedOut>
            </display:table>
           </td>
        </tr>
    </table>
    <div class="actionsrow"><del class="btnwrapper">
    <ul class="btnrow">
        <pa:scientificAbstractorDisplayWhenCheckedOut>
            <li><a href="javascript:void(0)" class="btn" onclick="this.blur();handleCreate();"><span class="btn_img"><span class="add">Add </span></span></a></li>
            <s:if test="%{interventionsList != null && !interventionsList.isEmpty()}">
                <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected intervention(s) from the study. Cancel to abort.', 'trialInterventionsdelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected intervention(s) from the study. Cancel to abort.', 'trialInterventionsdelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                <li><pa:toggleDeleteBtn/></li>
            </s:if>            
        </pa:scientificAbstractorDisplayWhenCheckedOut>
    </ul>
    </del></div>
</s:form></div>
</body>
</html>
