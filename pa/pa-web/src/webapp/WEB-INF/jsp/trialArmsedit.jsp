<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message
    key="arms.details.title" /></title>
<s:head />
<script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>


<script type="text/javascript">
    function armAdd(){
        document.armForm.action="trialArmsadd.action";
        document.armForm.submit();     
    }
    function armUpdate(){
        input_box=confirm("Click OK to save changes.  Cancel to Abort.");
        if (input_box==true){
            document.armForm.action="trialArmsupdate.action";
            document.armForm.submit();
        }
    }
    function interventionCheckboxClick(rowId){
        document.armForm.checkBoxEntry.value += rowId;
        document.armForm.checkBoxEntry.value += ",";
    }
</script>

</head>
<body>
<!-- <div id="contentwide"> -->
<h1><fmt:message key="arms.details.title" /></h1>

<!--Help Content-->
<!-- <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box"><pa:sucessMessage /> <s:if
    test="hasActionErrors()">
    <div class="error_msg"><s:actionerror /></div>
</s:if>
<h2>
    <s:if test="%{currentAction == 'editArm'}">
        <fmt:message key="arms.edit.title" /></s:if>
    <s:elseif test="%{currentAction == 'editNewArm'}">
        <fmt:message key="arms.add.title" /></s:elseif>
    <s:elseif test="%{currentAction == 'editGroup'}">
        <fmt:message key="arms.obs.edit.title" /></s:elseif>
    <s:elseif test="%{currentAction == 'editNewGroup'}">
        <fmt:message key="arms.obs.add.title" /></s:elseif>
</h2>

<table class="form">
    <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
    <tr>
        <td colspan="2">
            <s:if test="%{(currentAction == 'editArm')||(currentAction == 'editNewArm')}">
                <h3>Arm</h3>
            </s:if>
            <s:elseif test="%{(currentAction == 'editGroup')||(currentAction == 'editNewGroup')}">
                <h3>Group</h3>
            </s:elseif>
        </td>
    </tr>
    <tr>
        <s:form name="armForm">
        <s:hidden name="checkBoxEntry"/>
        <s:hidden name="currentAction"/>
        <s:hidden name="selectedArmIdentifier"/>
        <td>
            <table>
                <tr>
                    <td class="label"><s:label for="armName">Label:</s:label><span class="required">*</span></td>
                    <td class="value">
                        <s:textfield name="armName"maxlength="62" size="62" cssStyle="width:280px;float:left"/> 
                    </td>
                </tr>
                <s:if test="%{(currentAction == 'editArm')||(currentAction == 'editNewArm')}">
                    <s:set name="armTypeValues" value="@gov.nih.nci.pa.enums.ArmTypeCode@getDisplayNames()" />
                    <tr>
                        <td class="label"><s:label for="armType">Type:</s:label><span class="required">*</span></td>
                        <td class="value">
                        <s:select onchange="statusChange()" headerKey=""
                            headerValue="--Select--" name="armType" list="#armTypeValues" />
                        </td>
                    </tr>
                </s:if>
                <tr>
                    <td class="label"><s:label for="armDescription">Arm Description:</s:label><span class="required">*</span></td>
                    <td class="value">
                        <s:textarea name="armDescription" rows="3" cssStyle="width:280px;float:left"/>
                    </td>
                </tr>
            </table>
        </td>
        <td>
            <display:table name="intList" id="row" class="data">
                <display:column titleKey="arms.intervention.assignment" headerClass="centered" style="text-align: center">
                    <s:a href="#" onclick="interventionCheckboxClick(%{#attr.row.identifier})">
                        <s:checkbox onclick="radio(this)" name="userid" fieldValue="%{#attr.row.armAssignment}" 
                                value="%{#attr.row.armAssignment}"/>
                    </s:a>
                </display:column>
                <display:column property="name" titleKey="interventions.name"/>
                <display:column property="description" titleKey="interventions.description"/>
            </display:table>
        </td>
        </s:form>
    </tr>
    <tr>
        <td colspan="2">
        <div class="actionsrow"><del class="btnwrapper">
        <ul class="btnrow">
            <li>
            <s:if test="%{(currentAction == 'editArm')||(currentAction == 'editGroup')}">
                <s:a href="#" cssClass="btn" onclick="armUpdate();">
                    <span class="btn_img"> <span class="save">Save</span></span>
                </s:a>
            </s:if> 
            <s:elseif test="%{(currentAction == 'editNewArm')||(currentAction == 'editNewGroup')}">
                <s:a href="#" cssClass="btn" onclick="armAdd();">
                    <span class="btn_img"> <span class="save">Save</span></span>
                </s:a>
            </s:elseif>
            </li>
        </ul>
        </del></div>
        </td>
    </tr>
</table>

<div class="actionsrow"><del class="btnwrapper">
<ul class="btnrow">
        <li><a href="eligibilityCriteriaquery.action" class="btn"
            onclick="this.blur();"><span class="btn_img"><span
            class="back">Back</span></span></a></li>
        <li><a href="trialInterventions.action" class="btn"
            onclick="this.blur();"><span class="btn_img"><span
            class="next">Next</span></span></a></li>
</ul>
</del></div>
</div>
</body>
</html>