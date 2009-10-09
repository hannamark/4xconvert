<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="participatingOrganizations.title" /></title>
<s:head />
<script type="text/javascript" src="<c:url value="/scripts/js/calendarpopup.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/prototype.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/scriptaculous.js"/>"></script>

<script type="text/javascript">
            var siteRecruitmentStatusDate = new CalendarPopup();
</script>
     
</head>
<SCRIPT LANGUAGE="JavaScript">
function handleEdit(studyResourcingId){
    
    document.partOrgs.cbValue.value = studyResourcingId;
    document.partOrgs.action="participatingOrganizationsproprietaryEdit.action";
    document.partOrgs.submit(); 
}
function handleDelete(studyResourcingId){
    input_box=confirm("Click OK to delete the organization as a participant in the study.  Cancel to abort.");
    if (input_box==true){
        document.partOrgs.cbValue.value = studyResourcingId;
        document.partOrgs.action="participatingOrganizationsdelete.action";
        document.partOrgs.submit();
    } 
}
</SCRIPT>

<body onload="setFocusToFirstControl();">
<!-- <div id="contentwide"> -->
<h1><fmt:message key="participatingOrganizations.title" /></h1>
<c:set var="topic" scope="request" value="abstract_site"/>
<!--Help Content-->
<!-- <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
<div class="box">
    <pa:sucessMessage/>
    <s:actionerror />
    <s:form name="partOrgs">
<h2><fmt:message key="participatingOrganizations.title" /></h2>
    <table class="form">
        <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
    <tr><td colspan="2">
    <input type="hidden" name="cbValue" />
    <s:hidden name="proprietaryTrialIndicator" id="proprietaryTrialIndicator"></s:hidden>
    <display:table name="organizationList" id="row" class="data" pagesize="200">  
        <display:column property="nciNumber" titleKey="participatingOrganizations.nciNumber" class="sortable" />
        <display:column property="name" titleKey="participatingOrganizations.name" class="sortable" />
        <display:column property="status" titleKey="participatingOrganizations.status" class="sortable" />
        <display:column property="investigator" titleKey="participatingOrganizations.investigators"/>
        <display:column titleKey="participatingOrganizations.edit" headerClass="centered" class="action">
        <s:a href="#" onclick="handleEdit(%{#attr.row.id})"><img src="<%=request.getContextPath()%>/images/ico_edit.gif" alt="Edit" width="16" height="16"/></s:a>
        </display:column>
        <display:column titleKey="participatingOrganizations.unlink" headerClass="centered" class="action" >
        <s:a href="#" onclick="handleDelete(%{#attr.row.id})"><img src="<%=request.getContextPath()%>/images/ico_delete.gif" alt="Un-link" width="16" height="16"/></s:a>
        </display:column>
    </display:table>
    </td></tr>
    </table>
<div class="actionsrow">
    <del class="btnwrapper">
        <ul class="btnrow">
            <c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)
            					|| (sessionScope.role == 'SuAbstractor')}">
            <li><a href="participatingOrganizationsproprietaryCreate.action"                
                    class="btn" onclick="this.blur();"><span class="btn_img"><span class="add" >Add </span></span></a></li>
            </c:if>
            <li><a href="trialFundingquery.action"                
                    class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
            <li><a href="collaborators.action" 
                    class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
        </ul>   
    </del>
</div>
</s:form>
</div>
</body>
</html>