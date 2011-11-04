<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="run_submitted_institution"/> 
<head>
<title><fmt:message key="submissionByInstitution.header" /></title>
<s:head />
<link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all" />
<link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<c:url value="/ctro/popupWaitDialog.action" var="waitDialogUrl"/>
<script type="text/javascript">
addCalendar("Cal1", "Select Date", "criteria.intervalStartDate", "criteria");
addCalendar("Cal2", "Select Date", "criteria.intervalEndDate", "criteria");
setWidth(90, 1, 15, 1);
setFormat("mm/dd/yyyy");

function handleAction(){
    document.forms[0].action="resultsSubmissionByInstitution.action";
    document.forms[0].submit();
    showPopWin('${waitDialogUrl}', 600, 200, '', 'Trials Submitted by Institution');
}
function handleReset(){
    document.forms[0].action="criteriaSubmissionByInstitution.action";
    document.forms[0].submit();
}
</script>
</head>
<body>
<!-- main content begins-->
    <h1><fmt:message key="submissionByInstitution.header"/></h1>
    <s:form name="criteria">
        <s:token/>
        <table class="form">
        <s:if test="hasActionErrors()"><tr><td colspan="2"><div class="error_msg"><s:actionerror /></div></td></tr></s:if> 
        <tr><td>
        <table>
            <tr><td colspan="2">
                <p style="margin:0; padding:20"><fmt:message key="report.dates"/></p>
            </td></tr>
            <tr> 
                <td class="label">
                    <label><fmt:message key="report.criteria.intervalStartDate"/></label>
                    <span class="required">*</span>
                </td>
                <td class="value">
                    <s:textfield name="criteria.intervalStartDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                    <a href="javascript:showCal('Cal1')">
                        <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                    </a>
                </td>
            </tr>
            <tr> 
                <td class="label">
                    <label><fmt:message key="report.criteria.intervalEndDate"/></label>
                    <span class="required">*</span>
                </td>
                <td class="value">
                    <s:textfield name="criteria.intervalEndDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                    <a href="javascript:showCal('Cal2')">
                        <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                    </a>
                </td>
            </tr>
            <tr><td class="space"><br/></td></tr>
            <tr><td colspan="2">
                <p style="margin:0; padding:20"><fmt:message key="report.submissionType"/></p>
            </td></tr>
            <tr> 
                <td class="label">
                    <label><fmt:message key="report.criteria.submissionType"/></label>
                    <span class="required">*</span>
                </td>
                <td class="value"><s:select
                    name="criteria.submissionType"
                    list="#{'ORIGINAL':'Original', 'AMENDMENT':'Amendment', 'BOTH':'Both'}" />
                </td>
            </tr>
            <tr><td class="space"><br/></td></tr>
            <tr> 
                <td class="label">
                    <label><fmt:message key="report.criteria.ctep"/></label>
                    <span class="required">*</span>
                </td>
                <td class="value">
                    <s:checkbox name="criteria.ctep" />
                </td>
            </tr>
        </table>
        </td>
        <td>
        <table>
            <tr><td colspan="2">
                <p style="margin:0; padding:20"><fmt:message key="submissionByInstitution.institutions"/></p>
            </td></tr>
            <tr> 
                <td class="label">
                    <label><fmt:message key="submissionByInstitution.criteria.institutions"/></label>
                    <span class="required">*</span>
                </td>
                <td class="value">
                    <s:select name="criteria.institutions" multiple="true" list="submitterOrganizations" 
                              size="12" headerKey="1" headerValue="All"/>
                </td>
            </tr>
        </table>
        </td>
        </tr>
        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li>
                        <s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="search">Run report</span></span></s:a>  
                    </li>
                    <li>
                        <s:a href="#" cssClass="btn" onclick="handleReset()"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>  
                    </li>
                </ul>   
            </del>
        </div>
        <s:if test="%{resultList != null}">
            <table width="100%">
            <tr><td colspan="2">
                <h2><fmt:message key="submissionByInstitution.header"/></h2>
            </td></tr>
            <tr><td colspan="2">
                <fmt:message key="submissionByInstitution.header.institutions"/>
                <s:label name="selectedInstitutions"/>
            </td></tr>
            <tr>
                <td>
                    <fmt:message key="report.header.date1"/>
                    <s:label name="criteria.intervalStartDate"/>
                    <fmt:message key="report.header.date2"/>
                    <s:label name="criteria.intervalEndDate"/>
                </td>
                <td>
                    <fmt:message key="report.header.user"/>
                    <viewer:displayUser />
                </td>
            </tr>
            <tr><td><br/></td></tr>
            <tr>
            <td colspan="2">
            <display:table class="data" pagesize="20" id="row" name="sessionScope.displayTagList" 
                    requestURI="resultsSubmissionByInstitution.action" export="true">
                <display:setProperty name="export.excel" value="true" />
                <display:column titleKey="report.result.assignedIdentifier" property="assignedIdentifier"/>
                <display:column titleKey="report.result.submissionType" property="submissionType"/>
                <display:column titleKey="report.result.submitterOrg" property="submitterOrg"/>
                <display:column titleKey="report.result.leadOrgTrialIdentifier" property="leadOrgTrialIdentifier"/>
                <display:column titleKey="report.result.leadOrg" property="leadOrg"/>
                <display:column titleKey="report.result.dateLastCreated" property="dateLastCreated"/>
                <display:column titleKey="report.result.dws" property="dws"/>
                <display:column titleKey="report.result.dwsDate" property="dwsDate"/>
                <display:column escapeXml="true" titleKey="report.result.milestone" sortable="true" headerClass="sortable">
                    <s:if test="%{#attr.row.adminMilestone == null && #attr.row.scientificMilestone == null}">
                        <c:out value="${row.milestone}" />
                        <c:out value="${row.milestoneDate}"/>
                    </s:if>
                </display:column>
                <display:column escapeXml="true" titleKey="report.result.adminMilestone" sortable="true" headerClass="sortable">
                    <c:out value="${row.adminMilestone}" />
                    <c:out value="${row.adminMilestoneDate}"/>
                </display:column>
                <display:column escapeXml="true" titleKey="report.result.scientificMilestone" sortable="true" headerClass="sortable">
                    <c:out value="${row.scientificMilestone}" />
                    <c:out value="${row.scientificMilestoneDate}"/>
                </display:column>
            </display:table>
            </td>
            </tr>
            </table>
        </s:if>
    </s:form>
</body>
