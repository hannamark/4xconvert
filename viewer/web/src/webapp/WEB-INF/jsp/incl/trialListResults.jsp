<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="line"></div>
<h2>
    <fmt:message key="trialList.header.results"/>
</h2>
<display:table class="data" sort="list" pagesize="20" id="row" name="sessionScope.displayTagList" requestURI="trialListgetReport.action">
    <display:column titleKey="report.result.assignedIdentifier" property="assignedIdentifier" sortable="true" headerClass="sortable"/>
    <display:column titleKey="report.result.officialTitle" property="officialTitle" sortable="true" headerClass="sortable"/>
    <display:column titleKey="report.result.organization" property="organization" sortable="true" headerClass="sortable"/>
    <display:column titleKey="report.result.submissionType" property="submissionType" sortable="true" headerClass="sortable"/>
    <display:column titleKey="trialList.result.dateLastCreated" property="dateLastCreated" sortable="true" headerClass="sortable"/>
    <display:column titleKey="trialList.result.statusCode" property="statusCode" sortable="true" headerClass="sortable"/>
</display:table>
