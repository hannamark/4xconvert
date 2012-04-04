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
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}
function handleEdit(studyResourcingId){

    document.partOrgs.cbValue.value = studyResourcingId;
    document.partOrgs.action="participatingOrganizationsproprietaryEdit.action";
    document.partOrgs.submit();
}

</SCRIPT>

<body>
<h1><fmt:message key="participatingOrganizations.title" /></h1>
<c:set var="topic" scope="request" value="abstractsite"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
<div class="box">
    <pa:sucessMessage/>
    <pa:failureMessage/>
    <s:actionerror />
    <s:form name="partOrgs">
    <s:token/>
    <pa:studyUniqueToken/>
    <h2><fmt:message key="participatingOrganizations.title" /></h2>
    <table class="form">
    <tr><td colspan="2">
    <s:hidden name="cbValue" />
    <s:hidden name="proprietaryTrialIndicator" id="proprietaryTrialIndicator"></s:hidden>
    <s:set name="organizationList" value="organizationList" scope="request"/>
    <display:table name="organizationList" id="row" class="data" pagesize="200">
        <display:column escapeXml="true" property="nciNumber" titleKey="participatingOrganizations.nciNumber" class="sortable" />
        <display:column escapeXml="true" property="name" titleKey="participatingOrganizations.name" class="sortable" />
        <display:column escapeXml="true" property="status" titleKey="participatingOrganizations.status" class="sortable" />
        <display:column escapeXml="true" property="investigator" titleKey="participatingOrganizations.investigators"/>
        <pa:adminAbstractorDisplayWhenCheckedOut>
            <display:column titleKey="participatingOrganizations.edit" headerClass="centered" class="action">
                <s:a href="javascript:void(0)" onclick="handleEdit(%{#attr.row.id})"><img src='<c:url value="/images/ico_edit.gif"/>' alt="Edit" width="16" height="16"/></s:a>
            </display:column>
            <display:column titleKey="participatingOrganizations.unlink" headerClass="centered" class="action" >
                <s:checkbox name="objectsToDelete" fieldValue="%{#attr.row.id}" value="%{#attr.row.id in objectsToDelete}"/>
            </display:column>
        </pa:adminAbstractorDisplayWhenCheckedOut>
    </display:table>
    </td></tr>
    </table>
<div class="actionsrow">
    <del class="btnwrapper">
        <ul class="btnrow">
            <pa:adminAbstractorDisplayWhenCheckedOut>
                <li><a href="javascript:void(0)" class="btn" onclick="this.blur();submitXsrfForm('participatingOrganizationsproprietaryCreate.action');"><span class="btn_img"><span class="add" >Add </span></span></a></li>
                <s:if test="%{organizationList != null && !organizationList.isEmpty()}">
                    <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected participating site(s) from the study. Cancel to abort.', 'participatingOrganizationsdelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected participating site(s) from the study. Cancel to abort.', 'participatingOrganizationsdelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                    <li><pa:toggleDeleteBtn/></li>
                </s:if>
            </pa:adminAbstractorDisplayWhenCheckedOut>            
            <c:if test="${!sessionScope.trialSummary.proprietaryTrial}">
            <li><a href="javascript:void(0)"
                    class="btn" onclick="this.blur();submitXsrfForm('trialFundingquery.action');"><span class="btn_img"><span class="back">Back</span></span></a></li>
            <li><a href="javascript:void(0)"
                    class="btn" onclick="this.blur();submitXsrfForm('collaborators.action');"><span class="btn_img"><span class="next">Next</span></span></a></li>
            </c:if>
        </ul>
    </del>
</div>
</s:form>
</div>
</body>
</html>