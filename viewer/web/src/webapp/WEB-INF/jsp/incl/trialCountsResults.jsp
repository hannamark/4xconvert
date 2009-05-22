<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="line"></div>
<h2>
    <fmt:message key="trialCounts.header.results"/>
</h2>
<display:table class="data" sort="list" pagesize="20" id="row" name="sessionScope.displayTagList" requestURI="trialCountsgetReport.action">
    <display:column titleKey="report.result.organization" property="organization" sortable="true" headerClass="sortable"/>
    <s:if test="%{(criteria.groupByTimeUnit=='YEAR') || (criteria.groupByTimeUnit=='MONTH') || (criteria.groupByTimeUnit=='DAY')}">
        <display:column titleKey="trialCounts.result.year" property="year"/>
    </s:if>
    <s:if test="%{(criteria.groupByTimeUnit=='MONTH') || (criteria.groupByTimeUnit=='DAY')}">
        <display:column titleKey="trialCounts.result.month" property="month"/>
    </s:if>
    <s:if test="%{criteria.groupByTimeUnit == 'DAY'}">
        <display:column titleKey="trialCounts.result.day" property="day"/>
    </s:if>
    <display:column titleKey="trialCounts.result.count" property="count" sortable="true" headerClass="sortable"/>
</display:table>

