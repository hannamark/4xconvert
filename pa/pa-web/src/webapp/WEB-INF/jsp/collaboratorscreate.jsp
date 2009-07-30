<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message
    key="participatingOrganizations.collaborators.title" /></title>
<s:head />
<link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet"
    type="text/css" media="all" />
<link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet"
    type="text/css" media="all" />
<script type="text/javascript"
    src="<c:url value='/scripts/js/scriptaculous.js'/>"></script>
<script type="text/javascript"
    src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript"
    src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript"
    src="<c:url value='/scripts/js/prototype.js'/>"></script>
<c:url value="/protected/popuplookuporgs.action" var="lookupUrl" />


<script type="text/javascript">
function facilitySave(){
     document.facility.action="collaboratorsfacilitySave.action";
     document.facility.submit();     
}
function facilityUpdate(){
     document.facility.action="collaboratorsfacilityUpdate.action";
     document.facility.submit();     
}
// do not remove these two callback methods!
function setpersid(persid){}
function setorgid(orgid){}
function lookup(){
    showPopWin('${lookupUrl}', 900, 400, '', 'Organization');
}   
function loadDiv(orgid){
     var url = '/pa/protected/ajaxptpOrgdisplayOrg.action?orgId='+orgid;
     var div = document.getElementById('loadOrgDetails');   
     div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';    
     var aj = new Ajax.Updater(div, url, {
        asynchronous: true,
        method: 'get',
        evalScripts: false
     });
     return false;
}   
</script>
</head>
<body>
<!-- <div id="contentwide"> -->
<h1><fmt:message key="participatingOrganizations.collaborators.title" /></h1>
<c:set var="topic" scope="request" value="abstract_collaborator"/>
<!--Help Content-->
<!-- <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box"><pa:sucessMessage /> <s:if
    test="hasActionErrors()">
    <div class="error_msg"><s:actionerror /></div>
</s:if>
<h2>Collaborator</h2>



<table class="form">
    <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
    <tr>
        <td colspan="2">
        <s:form name="facility">
            <div id="loadOrgDetails">
            <div id="orgDetailsDiv"><%@ include
                file="/WEB-INF/jsp/nodecorate/selectedOrgDetails.jsp"%>
            </div>
            </div>
            <table class="form">
                <tr>
                    <td class="label"><s:label for="functionalCode">Functional Role:</s:label><span class="required">*</span></td>
                    <s:set name="functionalCodeValues"
                        value="@gov.nih.nci.pa.enums.StudyParticipationFunctionalCode@getCollaboratorDisplayNames()" />
                    <td class="value" colspan="2"><s:select headerKey=""
                        headerValue="--Select--" name="functionalCode" list="#functionalCodeValues" /></td>
                </tr>
                <tr>
                    <td class="label"><s:label for="statusCode">Status:</s:label></td>
                    <td class="value" colspan="2">
                        <s:textfield name="statusCode" readonly="true" cssClass="readonly" maxlength="80" size="80" cssStyle="width: 200px"/>
                    </td>               
                </tr>
            </table>
        </s:form>
        <div class="actionsrow"><del class="btnwrapper">
        <ul class="btnrow">
            <li><s:if test="%{currentAction == 'edit'}">
                <s:a href="#" cssClass="btn" onclick="facilityUpdate();">
                    <span class="btn_img"> <span class="save">Save</span></span>
                </s:a>
            </s:if> <s:else>
                <s:a href="#" cssClass="btn" onclick="facilitySave();">
                    <span class="btn_img"> <span class="save">Save</span></span>
                </s:a>
            </s:else></li>
        </ul>
        </del></div>
        </td>
    </tr>
</table>
</div>
</body>
</html>