<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="line"></div>
<h2>
    <fmt:message key="milestones.header.results"/>
</h2>
<display:table class="data" sort="list" pagesize="20" id="row" name="sessionScope.displayTagList" requestURI="milestonesgetReport.action">
    <display:column titleKey="milestones.result.assignedIdentifier" property="assignedIdentifier" sortable="true" headerClass="sortable"/>
    <display:column titleKey="milestones.result.officialTitle" property="officialTitle" sortable="true" headerClass="sortable"/>
    <display:column titleKey="milestones.result.organization" property="organization" sortable="true" headerClass="sortable"/>
    <display:column titleKey="milestones.result.milestoneCode" property="milestoneCode" sortable="true" headerClass="sortable"/>
    <display:column titleKey="milestones.result.milestoneDate" property="milestoneDate" sortable="true" headerClass="sortable"/>
</display:table>
