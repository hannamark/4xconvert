<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="participatingOrganizations.title" /></title>
<s:head />
<script type="text/javascript" src="scripts/js/calendarpopup.js" />  
<script type="text/javascript" src="scripts/js/prototype.js"></script>
<script type="text/javascript" src="scripts/js/scriptaculous.js"></script>

<script type="text/javascript">
            var siteRecruitmentStatusDate = new CalendarPopup();
</script>
     
</head>
<SCRIPT LANGUAGE="JavaScript">

</SCRIPT>

<body onload="setFocusToFirstControl();">
<!-- <div id="contentwide"> -->
<h1><fmt:message key="participatingOrganizations.title" /></h1>

<!--Help Content-->
<!-- <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
<div class="box"><s:form name="studyOverallStatus">
    <s:actionerror />
<h2><fmt:message key="participatingOrganizations.title" /></h2>
    <table class="form">
        <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
    <tr><td colspan="2">
    <display:table name="organizationList" class="data">  
        <display:column property="name" titleKey="participatingOrganizations.name" class="sortable" />
        <display:column property="nciNumber" titleKey="participatingOrganizations.nciNumber" class="sortable" />
        <display:column property="recruitmentStatus" titleKey="participatingOrganizations.recruitmentStatus" class="sortable" />
        <display:column property="recruitmentStatusDate" titleKey="participatingOrganizations.recruitmentStatusDate" class="sortable" />
        <display:column titleKey="participatingOrganizations.edit" class="action">
        <s:a href="#"><img src="<%=request.getContextPath()%>/images/ico_edit.gif" alt="Edit" width="16" height="16"/></s:a>
        </display:column>
        <display:column titleKey="participatingOrganizations.unlink" class="action" >
        <s:a href="#"><img src="<%=request.getContextPath()%>/images/ico_cancel.gif" alt="Un-link" width="16" height="16"/></s:a>
        </display:column>
    </display:table>
    </td></tr>
    </table>
<div class="actionsrow">
    <del class="btnwrapper">
        <ul class="btnrow">
            <li><a href="participatingOrganizationscreate.action"                
                    class="btn" onclick="this.blur();"><span class="btn_img"><span class="add" >Add </span></span></a></li>
            <li><a href="trialFunding.action"                
                    class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
            <li><a href="nciSpecificInformationquery.action" 
                    class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
        </ul>   
    </del>
</div>
</s:form>
</div>
</body>
</html>