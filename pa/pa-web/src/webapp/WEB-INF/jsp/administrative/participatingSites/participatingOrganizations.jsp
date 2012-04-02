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
    document.partOrgs.action="participatingOrganizationsedit.action";
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
        <pa:studyUniqueToken/>
        <h2><fmt:message key="participatingOrganizations.title" /></h2>
    <table class="form">
        <tr>
            <td colspan="2">
                <s:hidden name="cbValue" />
                <s:set name="organizationList" value="organizationList" scope="request" />
                <display:table name="organizationList" id="row" class="data" pagesize="200" sort="list" requestURI="participatingOrganizations.action">
                    <display:column escapeXml="true" property="nciNumber" titleKey="participatingOrganizations.nciNumber" sortable="true" />
                    <display:column escapeXml="true" property="name" titleKey="participatingOrganizations.name" sortable="true" />
                    <display:column escapeXml="true" property="status" titleKey="participatingOrganizations.status" sortable="true" />
                    <display:column escapeXml="true" property="recruitmentStatus" titleKey="participatingOrganizations.recruitmentStatus" sortable="true" />
                    <display:column escapeXml="true" property="recruitmentStatusDate" titleKey="participatingOrganizations.recruitmentStatusDate" sortable="true" />
                    <display:column property="investigator" titleKey="participatingOrganizations.investigators"/>
                    <display:column property="primarycontact" titleKey="participatingOrganizations.primarycontacts"/>
                    <pa:adminAbstractorDisplayWhenCheckedOut>
                        <display:column titleKey="participatingOrganizations.edit" headerClass="centered" class="action">
                            <s:a href="#" onclick="handleEdit(%{#attr.row.id})"><img src='<c:url value="/images/ico_edit.gif"/>' alt="Edit" width="16" height="16"/></s:a>
                        </display:column>
                        <display:column titleKey="participatingOrganizations.unlink" headerClass="centered" class="action" >
                            <s:checkbox name="objectsToDelete" fieldValue="%{#attr.row.id}" value="%{#attr.row.id in objectsToDelete}"/>
                        </display:column>
                    </pa:adminAbstractorDisplayWhenCheckedOut>
                </display:table>
            </td>
        </tr>
    </table>
<div class="actionsrow">
    <del class="btnwrapper">
        <ul class="btnrow">
            <pa:adminAbstractorDisplayWhenCheckedOut>
                <li><a href="participatingOrganizationscreate.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="add" >Add</span></span></a></li>
                <s:if test="%{organizationList != null && !organizationList.isEmpty()}">
                    <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected participating site(s) from the study. Cancel to abort.', 'participatingOrganizationsdelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected participating site(s) from the study. Cancel to abort.', 'participatingOrganizationsdelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                    <li><pa:toggleDeleteBtn/></li>
                </s:if>                
            </pa:adminAbstractorDisplayWhenCheckedOut>
        </ul>
    </del>
</div>
</s:form>
</div>
</body>
</html>
