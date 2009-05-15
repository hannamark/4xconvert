<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="line"></div>
<h2>
    <fmt:message key="summarySent.header.results"/>
</h2>
<display:table class="data" sort="list" pagesize="20" id="row" name="sessionScope.displayTagList" requestURI="milestonesgetReport.action">
    <display:column titleKey="report.result.assignedIdentifier" property="assignedIdentifier" sortable="true" headerClass="sortable"/>
    <display:column titleKey="report.result.officialTitle" property="officialTitle" sortable="true" headerClass="sortable"/>
    <display:column titleKey="report.result.organization" property="organization" sortable="true" headerClass="sortable"/>
    <display:column titleKey="summarySent.result.milestoneDate" property="milestoneDate" sortable="true" headerClass="sortable"/>
    <display:column titleKey="summarySent.result.feedbackDate" property="feedbackDate" sortable="true" headerClass="sortable"/>
</display:table>
