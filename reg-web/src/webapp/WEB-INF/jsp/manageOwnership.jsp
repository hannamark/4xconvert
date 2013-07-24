<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="${pageTitleKey}"/></title>
        <s:head/>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
    
        <script type="text/javascript" language="javascript">
            var trialIds = new Array();
            var checked = ${checked};
            <c:forEach var="item" items="${studyProtocols}">
                  trialIds = trialIds + <c:out value="${item.studyProtocol.id}"/> + ",";
            </c:forEach>
            function updateOwnership(assignOwnership) {
                var form = document.forms[0];
                form.action = (assignOwnership) ? "${actionName}assignOwnership.action" : "${actionName}unassignOwnership.action";
                form.submit();
            }
            
            function updateRegUser(regUserId) {
                var  url = '/registry/siteadmin/${actionName}setRegUser.action';
                var params = {
                    owner: $("chk" + regUserId).checked ? "true" : "false",
                    regUserId: regUserId
                };
                var aj = callAjaxPost(null, url, params);
                return false;
            }
            
            function updateTrial(trialId) {
                var  url = '/registry/siteadmin/${actionName}setTrial.action';
                var params = {
                    selected: $("chk" + trialId).checked ? "true" : "false",
                    trialId: trialId
                };
                var aj = callAjaxPost(null, url, params);
                return false;
            }
            
            function updateEmailPref(trialId) {
                var  url = '/registry/siteadmin/${actionName}updateEmailPref.action';
                var params = {
                    selected: $("chkEmail" + trialId).checked ? "true" : "false",
                    trialId: trialId
                };
                var aj = callAjaxPost(null, url, params);
                return false;
            }
            
            
            function check(field)
            {
                if (!checked) {
                
                    if(typeof field.length == 'undefined'){
                        field.checked = true ;
                    } else {
                        for (i = 0; i < field.length; i++)
                               field[i].checked = true ;
                    }
                    checked = true;
                    document.getElementById('checkButton').value = "Uncheck All";
                    
                    var  url = '/registry/siteadmin/${actionName}setTrial.action';
                    var params = {
                        selected: "true",
                        trialIds: trialIds,
                        checked: "true"
                    };
                    var aj = callAjaxPost(null, url, params);
                    return false;
                }
                else {
                    if(typeof field.length == 'undefined'){
                            field.checked = false ;
                    } else {
                        for (i = 0; i < field.length; i++)
                            field[i].checked = false ;
                    }
                    checked = false;
                    document.getElementById('checkButton').value = "Check All";
                    
                    var  url = '/registry/siteadmin/${actionName}setTrial.action';
                    var params = {
                    selected: "false",
                    trialIds: trialIds,
                    checked: "false"
                    };
                    var aj = callAjaxPost(null, url, params);
                    return false;
               }
            }

        
        </script>
    </head>
    <body>
        <!-- main content begins-->
        <c:set var="topic" scope="request" value="${topicValue}"/>
        <h1><fmt:message key="${pageHeaderKey}"/></h1>
        <div class="box" id="filters">
            <reg-web:failureMessage/>
            <reg-web:sucessMessage/>
            <s:form name="formManageTrialOwnership" action="%{#request.actionName}view.action">
            <s:hidden name="checked" id="checked"/>
            <s:token/>
            <table class="form">
                <tr>
                    <td scope="row" class="label">
                        <h3 id="search_results" align="left"><fmt:message key="${memberHeaderKey}"/></h3>
                    </td>
                    <td scope="row" class="label">
                        <h3 id="search_results"  align="left"><fmt:message key="${trialHeaderKey}"/></h3>
                    </td>
                </tr>
                <tr>
                    <td scope="row" class="label" style="text-align:left;">
                        <s:set name="orgMembers" value="registryUsers" scope="request"/>
                        <display:table class="data" summary="This table contains your search results." style="width:40%"
                                       decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" pagesize="10" id="regUserRow"
                                       name="orgMembers" requestURI="${actionName}view.action" export="false">
                            <display:column escapeXml="true" titleKey="managetrialownership.users.firstname" property="registryUser.firstName" sortable="true" headerClass="sortable" headerScope="col"/>
                            <display:column escapeXml="true" titleKey="managetrialownership.users.lastname" property="registryUser.lastName" sortable="true" headerClass="sortable" headerScope="col"/>
                            <display:column escapeXml="true" titleKey="managetrialownership.users.email" property="registryUser.emailAddress" sortable="true" headerClass="sortable" headerScope="col"/>
                            <display:column titleKey="managetrialownership.users.allow" sortable="true" headerClass="sortable">
                                <c:set var="chkRegUserId" value="chk${regUserRow.registryUser.id}" />
                                <c:choose>
                                    <c:when test="${regUserRow.selected}">
                                        <label for="${chkRegUserId}" class="hidden-label"><fmt:message key="managetrialownership.users.allow"/></label>
                                        <input type="checkbox" name="${chkRegUserId}" value="true" id="${chkRegUserId}" checked="checked" onclick="updateRegUser('${regUserRow.registryUser.id}')"/>
                                    </c:when>
                                    <c:otherwise>
                                        <label for="${chkRegUserId}" class="hidden-label"><fmt:message key="managetrialownership.users.allow"/></label>
                                        <input type="checkbox" name="${chkRegUserId}" value="true" id="${chkRegUserId}" onclick="updateRegUser('${regUserRow.registryUser.id}')"/>
                                    </c:otherwise>
                                </c:choose>
                            </display:column>
                        </display:table>
                    </td>
                    <td scope="row" class="label">
                        <c:choose>
                            <c:when test="${checked}">
                                <input type="button" id="checkButton" value="Uncheck All" onClick="check(document.formManageTrialOwnership.chkboxes)">
                            </c:when>
                            <c:otherwise>
                               <input type="button" id="checkButton" value="Check All" onClick="check(document.formManageTrialOwnership.chkboxes)">
                            </c:otherwise>
                        </c:choose>
                        <s:set name="orgTrials" value="studyProtocols" scope="request"/>
                        <display:table class="data" summary="This table contains your search results."
                                       decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" pagesize="10" id="studyProtocolRow"
                                       name="orgTrials" requestURI="${actionName}view.action" export="false">
                            <display:column titleKey="managetrialownership.trials.nciidentifier" property="nciIdentifier" sortable="true" headerClass="sortable" headerScope="col"/>
                            <display:column titleKey="managetrialownership.trials.leadOrgId" property="leadOrgId" sortable="true" headerClass="sortable" headerScope="col"/>
                            <display:column titleKey="managetrialownership.trials.allow" sortable="true" headerClass="sortable">
                                <c:set var="chkTrialId" value="chk${studyProtocolRow.studyProtocol.id}" />
                                <c:choose>
                                    <c:when test="${studyProtocolRow.selected}">
                                        <label for="${chkTrialId}" class="hidden-label"><fmt:message key="managetrialownership.trials.allow"/></label>
                                        <input type="checkbox" name="chkboxes" value="true" id="${chkTrialId}" checked="checked" onclick="updateTrial('${studyProtocolRow.studyProtocol.id}')"/>
                                    </c:when>
                                    <c:otherwise>
                                        <label for="${chkTrialId}" class="hidden-label"><fmt:message key="managetrialownership.trials.allow"/></label>
                                        <input type="checkbox" name="chkboxes" value="true" id="${chkTrialId}" onclick="updateTrial('${studyProtocolRow.studyProtocol.id}')"/>
                                    </c:otherwise>
                                </c:choose>
                            </display:column>
                            <c:if test="${enableEmailPrefs=='true'}">
                                <display:column titleKey="managetrialownership.trials.emails" headerClass="sortable">
	                                <c:set var="chkEmailTrialId" value="chkEmail${studyProtocolRow.studyProtocol.id}" />
	                                <c:choose>
	                                    <c:when test="${studyProtocolRow.emailSelected}">
	                                        <label for="${chkEmailTrialId}" class="hidden-label"><fmt:message key="managetrialownership.trials.emails"/></label>
                                            <input type="checkbox" name="chkboxes" value="true" id="${chkEmailTrialId}" checked="checked" onclick="updateEmailPref('${studyProtocolRow.studyProtocol.id}')"/>
	                                    </c:when>
	                                    <c:otherwise>
                                            <label for="${chkEmailTrialId}" class="hidden-label"><fmt:message key="managetrialownership.trials.emails"/></label>
	                                        <input type="checkbox" name="chkboxes" value="true" id="${chkEmailTrialId}" onclick="updateEmailPref('${studyProtocolRow.studyProtocol.id}')"/>
	                                    </c:otherwise>
	                                </c:choose>
                                </display:column>                            
                            </c:if>
                        </display:table>
                    </td>
                    <td scope="row" class="label">&nbsp;</td>
                </tr>
            </table>
            <div class="actionsrow">
                <del class="btnwrapper">
                    <ul class="btnrow">
                        <li>
                            <s:a href="javascript:void(0)" cssClass="btn" onclick="updateOwnership(true);"><span class="btn_img"><span class="save"><fmt:message key="managetrialownership.buttons.assign"/></span></span></s:a>
                            <s:a href="javascript:void(0)" cssClass="btn" onclick="updateOwnership(false);"><span class="btn_img"><span class="delete"><fmt:message key="managetrialownership.buttons.unassign"/></span></span></s:a>
                        </li>
                    </ul>
                </del>
            </div>
            <div class="line"></div>
            </s:form>
        </div>
    </body>
</html>
