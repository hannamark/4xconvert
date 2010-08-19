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

    // this function is called from body onload in main.jsp (decorator)
    function callOnloadFunctions(){
        setFocusToFirstControl();
    }
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
    function cancel(){
        document.armForm.action="trialArms.action";
        document.armForm.submit();
    }
    function resetValues() {
        document.armForm.reset();
    }
    
</script>

</head>
<body>
<!-- <div id="contentwide"> -->
<h1><fmt:message key="arms.details.title" /></h1>
<c:set var="topic" scope="request" value="abstract_arms"/>
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
        <s:form name="armForm">
        <s:hidden name="checkBoxEntry"/>
        <s:hidden name="currentAction"/>
        <s:hidden name="selectedArmIdentifier"/>
        <td>
            <table>
                <tr>
                    <td class="label"><s:label for="armName">Label:</s:label><span class="required">*</span></td>
                    <td class="value">
                        <s:textfield name="armName" maxlength="62" size="62" cssStyle="width:280px;float:left"/>
                    </td>
                </tr>
                <s:if test="%{(currentAction == 'editArm')||(currentAction == 'editNewArm')}">
                    <s:set name="armTypeValues" value="@gov.nih.nci.pa.enums.ArmTypeCode@getDisplayNames()" />
                    <tr>
                        <td class="label"><s:label for="armType">Type:</s:label><span class="required">*</span></td>
                        <td class="value">
                        <s:select onchange="statusChange()" headerKey=""
                            headerValue="--Select--" name="armType" list="#armTypeValues" />
                            <span class="formErrorMsg">
                                <s:fielderror>
                                <s:param>armType</s:param>
                               </s:fielderror>
                         </span>
                        </td>
                    </tr>
                </s:if>
                <tr>
                    <td class="label"><s:label for="armDescription">Arm Description:</s:label></td>
                    <td class="value">
                        <s:textarea name="armDescription" rows="3" cssStyle="width:280px;float:left"/>
                        <span class="formErrorMsg">
                                <s:fielderror>
                                <s:param>armDescription</s:param>
                               </s:fielderror>
                         </span>
                    </td>
                </tr>
            </table>
        </td>
        <td>
            <s:set name="intList" value="intList" scope="request"/>
            <display:table name="intList" id="row" class="data">
                <display:column titleKey="arms.intervention.assignment" headerClass="centered" style="text-align: center">
                    <s:a href="#" onclick="interventionCheckboxClick(%{#attr.row.identifier})">
                        <s:checkbox onclick="radio(this)" name="userid" fieldValue="%{#attr.row.armAssignment}" value="%{#attr.row.armAssignment}"/>
                    </s:a>
                </display:column>
                <display:column escapeXml="true" property="name" titleKey="interventions.name"/>
                <display:column escapeXml="true" property="description" titleKey="interventions.description"/>
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
            <s:a href="#" cssClass="btn" onclick="resetValues();return false">
                <span class="btn_img"><span class="cancel">Reset</span></span>
            </s:a>
            <s:a href="#" cssClass="btn" onclick="cancel();">
               <span class="btn_img"><span class="cancel">Cancel</span></span>
            </s:a>
            </li>
        </ul>
        </del></div>
        </td>
    </tr>
</table>

</div>
</body>
</html>