<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:url value="/protected/ajax/curate/search/person/listPersons.action"
	var="sortUrl" />
<display:table class="data" sort="list"
	pagesize="${initParam['defaultPageSize']}" uid="row" name="persons"
	requestURI="${sortUrl}" export="true">
	<po:displayTagProperties />
	<display:setProperty name="export.xml" value="false" />
	<display:setProperty name="export.excel.filename"
		value="person_inbox.xls" />
	<display:setProperty name="export.excel.include_header" value="true" />
	<display:setProperty name="export.csv.filename"
		value="person_inbox.csv" />
	<display:setProperty name="export.csv.include_header" value="true" />

	<display:setProperty name="pagination.sort.param"
		value="persons.sortCriterion" />
	<display:setProperty name="pagination.sortdirection.param"
		value="persons.sortDirection" />
	<display:setProperty name="pagination.pagenumber.param"
		value="persons.pageNumber" />

	<%@ include file="/WEB-INF/jsp/search/person/resultsTableBaseCols.jsp"%>

	<display:column titleKey="th.action" class="action" media="html">
		<c:url var="curateUrl" value="/protected/person/curate/start.action">
			<c:param name="person.id" value="${row.id}" />
		</c:url>
		<po:buttonRow>
			<po:button href="${curateUrl}" style="select_person" text="Curate"
				id="person_id_${row.id}" />
		</po:buttonRow>
	</display:column>
</display:table>