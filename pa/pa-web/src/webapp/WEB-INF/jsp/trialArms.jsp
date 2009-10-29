<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="arms.details.title" /></title>
<s:head />

</head>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
// this function is called from body onload in main.jsp (decorator) 
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page' 
}
function handleEditArm(rowId){
    document.armForm.selectedArmIdentifier.value = rowId;
    document.armForm.action="trialArmsedit.action";
    document.armForm.submit(); 
}
function handleEditGroup(rowId){
    document.armForm.selectedArmIdentifier.value = rowId;
    document.armForm.action="trialArmseditGroup.action";
    document.armForm.submit(); 
}
function handleDelete(rowId){
    input_box=confirm("Click OK to remove the arm from the study.  Cancel to abort.");
    if (input_box==true){
        document.armForm.selectedArmIdentifier.value = rowId;
        document.armForm.action="trialArmsdelete.action";
        document.armForm.submit();
    }
}
function handleCreateArm(){
    document.armForm.action="trialArmscreate.action";
    document.armForm.submit(); 
}
function handleCreateGroup(){
    document.armForm.action="trialArmscreateGroup.action";
    document.armForm.submit(); 
}
</SCRIPT>

<body>
<h1><fmt:message key="arms.details.title" /></h1>
<c:set var="topic" scope="request" value="abstract_arms"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box"><pa:sucessMessage /> <s:if
    test="hasActionErrors()">
    <div class="error_msg"><s:actionerror /></div>
</s:if> <s:form name="armForm"><s:hidden name="selectedArmIdentifier"/>
                               <s:hidden name="currentAction"/> 
    <h2>
    <s:if test="%{currentAction == 'listArm'}">
        <fmt:message key="arms.details.title" /></s:if>
    <s:elseif test="%{currentAction == 'listGroup'}">
        <fmt:message key="arms.obs.details.title" /></s:elseif>
    </h2>
    <table class="form">
        <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
        <tr>
            <td colspan="2"><s:hidden name="cbValue" /> 
            <s:set name="armList" value="armList" scope="request"/>
            <display:table name="armList" id="row" class="data" sort="list" pagesize="200" requestURI="trialArms.action">
                <display:column property="name" sortable="true"
                    titleKey="arms.name" 
                    headerClass="sortable"  />
                <s:if test="%{currentAction == 'listArm'}"><display:column property="type" sortable="true"
                    titleKey="arms.type" headerClass="sortable"/></s:if>
                <display:column property="description" sortable="true"
                    titleKey="arms.description"
                    headerClass="sortable" />
                <display:column property="interventions" titleKey="arms.interventions"/>
                <c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)
                					|| (sessionScope.role == 'SuAbstractor')}">
                <display:column titleKey="arms.edit" headerClass="centered" class="action">
                    <s:if test="%{currentAction == 'listArm'}">
                    <s:a href="#" onclick="handleEditArm(%{#attr.row.identifier})">
                        <img src="<%=request.getContextPath()%>/images/ico_edit.gif"
                            alt="Edit" width="16" height="16" />
                    </s:a>
                    </s:if>
                    <s:elseif test="%{currentAction == 'listGroup'}">
                    <s:a href="#" onclick="handleEditGroup(%{#attr.row.identifier})">
                        <img src="<%=request.getContextPath()%>/images/ico_edit.gif"
                            alt="Edit" width="16" height="16" />
                    </s:a>
                    </s:elseif>
                </display:column>
                <display:column titleKey="arms.delete" headerClass="centered" class="action">
                    <s:a href="#" onclick="handleDelete(%{#attr.row.identifier})">
                        <img src="<%=request.getContextPath()%>/images/ico_delete.gif"
                            alt="Delete" width="16" height="16" />
                    </s:a>
                </display:column>
                </c:if>
            </display:table>
            
            
            </td>
        </tr>
    </table>
    <div class="actionsrow"><del class="btnwrapper">
    <ul class="btnrow">
    <c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)
    					|| (sessionScope.role == 'SuAbstractor')}">
        <s:if test="%{currentAction == 'listArm'}">
            <li><a href="#" class="btn" onclick="this.blur();handleCreateArm();"><span
                class="btn_img"><span class="add">Add </span></span></a></li>
        </s:if><s:elseif test="%{currentAction == 'listGroup'}">
            <li><a href="#" class="btn" onclick="this.blur();handleCreateGroup();"><span
                class="btn_img"><span class="add">Add </span></span></a></li>
        </s:elseif>
     </c:if>   
        <li><a href="trialInterventions.action" class="btn"
            onclick="this.blur();"><span class="btn_img"><span
            class="back">Back</span></span></a></li>
        <li><a href="subGroupsquery.action" class="btn"
            onclick="this.blur();"><span class="btn_img"><span
            class="next">Next</span></span></a></li>
    </ul>
    </del></div>
</s:form></div>
</body>
</html>