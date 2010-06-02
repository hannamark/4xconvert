<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="displaytrialownership.page.title"/></title>
    <s:head/>
</head>
<SCRIPT LANGUAGE="JavaScript">
function submitForm() {
    document.forms[0].action="displayTrialOwnershipsearch.action";
    document.forms[0].submit();
}

function resetSearch() {
    document.getElementById("firstName").value="";
    document.getElementById("lastName").value="";
    document.getElementById("emailAddress").value="";
    document.getElementById("trialIdentifier").value="";
    submitForm();
}

function removeOwnership(userId, trialId) {
    document.forms[0].action="displayTrialOwnershipremoveOwnership.action?userId=" + userId + "&trialId=" + trialId;
    document.forms[0].submit();
}

</SCRIPT>
<body>
<!-- main content begins-->
<c:set var="topic" scope="request" value="display_trial_ownership"/>
<h1><fmt:message key="displaytrialownership.page.header"/></h1>
<div class="box" id="filters">
    <reg-web:failureMessage/>
    <reg-web:sucessMessage/>
    <s:form name="formDisplayTrialOwnership" action="displayTrialOwnershipview.action">
        <table class="form">
            <tr>
                <td scope="row" class="label">
                    <label for="displayTrialOwnership_criteria_firstName"> <fmt:message key="displaytrialownership.criteria.firstname"/></label>
                </td>
                <td>
                    <s:textfield id="firstName" name="criteria.firstName" maxlength="200" size="100"  cssStyle="width:200px" />
                </td>
            </tr>
            <tr>
                <td scope="row" class="label">
                    <label for="displayTrialOwnership_criteria_lastName"> <fmt:message key="displaytrialownership.criteria.lastname"/></label>
                </td>
                <td>
                    <s:textfield id="lastName" name="criteria.lastName"   maxlength="200" size="100"  cssStyle="width:200px" />
                </td>
            </tr>
            <tr>
                <td scope="row" class="label">
                    <label for="displayTrialOwnership_criteria_email"> <fmt:message key="displaytrialownership.criteria.email"/></label>
                </td>
                <td>
                    <s:textfield id="emailAddress" name="criteria.emailAddress"  maxlength="200" size="100"  cssStyle="width:200px" />
                </td>
            </tr>
            <tr>
                <td scope="row" class="label">
                    <label for="displayTrialOwnership_criteria_trialIdentifier"> <fmt:message key="displaytrialownership.criteria.trialidentifier"/></label>
                </td>
                <td>
                    <s:textfield id="trialIdentifier" name="criteria.trialIdentifier"  maxlength="200" size="100"  cssStyle="width:200px" />
                </td>
            </tr>
        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li>
                        <s:a href="#" cssClass="btn" onclick="submitForm();"><span class="btn_img"><span class="search"><fmt:message key="displaytrialownership.buttons.search"/></span></span></s:a>
                        <s:a href="#" cssClass="btn" onclick="resetSearch();"><span class="btn_img"><span class="cancel"><fmt:message key="displaytrialownership.buttons.reset"/></span></span></s:a>
                    </li>
                </ul>
            </del>
        </div>
        <div class="line"></div>
        <s:set name="records" value="trialOwnershipInfo" scope="request"/>
        <h2 id="search_results">Search Results</h2>
        <display:table class="data" summary="This table contains your search results."
                    decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" pagesize="10" id="row"
                      name="records" requestURI="displayTrialOwnershipview.action" export="false">
            <display:column titleKey="displaytrialownership.results.firstname" property="firstName" maxLength= "200" sortable="true" headerClass="sortable" headerScope="col"/>
            <display:column titleKey="displaytrialownership.results.lastname" property="lastName" sortable="true" headerClass="sortable" headerScope="col"/>
            <display:column titleKey="displaytrialownership.results.email" property="emailAddress" sortable="true" headerClass="sortable" headerScope="col"/>
            <display:column titleKey="displaytrialownership.results.trialidentifier" property="officialTitle"  sortable="true" headerClass="sortable" headerScope="col"/>
            <display:column titleKey="displaytrialownership.results.action">
                <a href="#" class="btn" onclick="removeOwnership('${row.userId}', '${row.trialId}');">
                    <span class="btn_img"><span class="delete"><fmt:message key="displaytrialownership.buttons.remove"/></span></span>
                </a>
            </display:column>
        </display:table>
    </s:form>
</div>
</body>
</html>
