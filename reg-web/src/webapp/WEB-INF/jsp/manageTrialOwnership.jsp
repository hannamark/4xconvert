<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="managetrialownership.page.title"/></title>
    <s:head/>
</head>
<SCRIPT LANGUAGE="JavaScript">
function updateOwnership(assignOwnership) {
    var formAction = "manageTrialOwnershipassignOwnership.action";
    if (!assignOwnership) {
        formAction = "manageTrialOwnershipunassignOwnership.action";
    }
    document.forms[0].action= formAction;
    document.forms[0].submit();
}

function updateRegUser(regUserId) {
    var  url = '/registry/protected/manageTrialOwnershipsetRegUser.action?regUserId='+regUserId;
    var isOwner = document.getElementById("chk" + regUserId).checked ? "true" : "false";
    url += "&isOwner=" + isOwner;
    var aj = new Ajax.Request(url, {
        asynchronous: true,
        method: 'get',
        evalScripts: false
    });
    return false;
}

function updateTrial(trialId) {
    var  url = '/registry/protected/manageTrialOwnershipsetTrial.action?trialId='+trialId;
    var isChecked = document.getElementById("chk" + trialId).checked ? "true" : "false";
    url += "&isSelected=" + isChecked;
    var aj = new Ajax.Request(url, {
        asynchronous: true,
        method: 'get',
        evalScripts: false
    });
    return false;
}

</SCRIPT>
<body>
<!-- main content begins-->
<c:set var="topic" scope="request" value="manage_user_trial_ownership"/>
<h1><fmt:message key="managetrialownership.page.header"/></h1>
<div class="box" id="filters">
    <reg-web:failureMessage/>
    <reg-web:sucessMessage/>
    <s:form name="formManageTrialOwnership" action="manageTrialOwnershipview.action">
        <table class="form">
            <tr>
                <td scope="row" class="label">
                    <h3 id="search_results" align="left"><fmt:message key="managetrialownership.users.header"/></h3>
                </td>
                <td scope="row" class="label">
                    <h3 id="search_results"  align="left"><fmt:message key="managetrialownership.trials.header"/></h3>
                </td>
            </tr>
            <tr>
                <td scope="row" class="label">
                    <s:set name="orgMembers" value="registryUsers" scope="request"/>
                    <display:table class="data" summary="This table contains your search results." style="width:40%"
                                decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" pagesize="10" id="regUserRow"
                                  name="orgMembers" requestURI="manageTrialOwnershipview.action" export="false">
                        <display:column escapeXml="true" titleKey="managetrialownership.users.firstname" property="registryUser.firstName" sortable="true" headerClass="sortable" headerScope="col"/>
                        <display:column escapeXml="true" titleKey="managetrialownership.users.lastname" property="registryUser.lastName" sortable="true" headerClass="sortable" headerScope="col"/>
                        <display:column escapeXml="true" titleKey="managetrialownership.users.email" property="registryUser.emailAddress" sortable="true" headerClass="sortable" headerScope="col"/>
                        <display:column titleKey="managetrialownership.users.allow" sortable="true" headerClass="sortable">
                            <c:set var="chkRegUserId" value="chk${regUserRow.registryUser.id}" />
                            <c:choose>
                                <c:when test="${regUserRow.selected}">
                                    <input type="checkbox" name="${chkRegUserId}" value="true" id="${chkRegUserId}" checked="checked" onclick="updateRegUser('${regUserRow.registryUser.id}')"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="checkbox" name="${chkRegUserId}" value="true" id="${chkRegUserId}" onclick="updateRegUser('${regUserRow.registryUser.id}')"/>
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                    </display:table>
                </td>
                <td scope="row" class="label">
                    <s:set name="orgTrials" value="studyProtocols" scope="request"/>
                    <display:table class="data" summary="This table contains your search results."
                                decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" pagesize="10" id="studyProtocolRow"
                                  name="orgTrials" requestURI="manageTrialOwnershipview.action" export="false">
                        <display:column titleKey="managetrialownership.trials.nciidentifier" property="nciIdentifier" sortable="true" headerClass="sortable" headerScope="col"/>
                        <display:column titleKey="managetrialownership.trials.allow" sortable="true" headerClass="sortable">
                            <c:set var="chkTrialId" value="chk${studyProtocolRow.studyProtocol.id}" />
                            <c:choose>
                                <c:when test="${studyProtocolRow.selected}">
                                    <input type="checkbox" name="${chkTrialId}" value="true" id="${chkTrialId}" checked="checked" onclick="updateTrial('${studyProtocolRow.studyProtocol.id}')"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="checkbox" name="${chkTrialId}" value="true" id="${chkTrialId}" onclick="updateTrial('${studyProtocolRow.studyProtocol.id}')"/>
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                    </display:table>
                </td>
                <td scope="row" class="label">&nbsp;</td>
            </tr>
        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li>
                        <s:a href="#" cssClass="btn" onclick="updateOwnership(true);"><span class="btn_img"><span class="save"><fmt:message key="managetrialownership.buttons.assign"/></span></span></s:a>
                        <s:a href="#" cssClass="btn" onclick="updateOwnership(false);"><span class="btn_img"><span class="delete"><fmt:message key="managetrialownership.buttons.unassign"/></span></span></s:a>
                    </li>
                </ul>
            </del>
        </div>
        <div class="line"></div>
    </s:form>
</div>
</body>
</html>
