<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:url
	value="/protected/ajax/search/organization/results/searchdt.action"
	var="sortUrl" />
<ajax:displayTag id="organizationSearchResults" tableClass="data">
	<%@ include file="resultsTable.jsp"%>
</ajax:displayTag>