<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:url value="/protected/ajax/curate/search/listPersons.action" var="sortUrl"/>
<ajax:displayTag id="personSearchResults" ajaxFlag="true" tableClass="data">
	<display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="row" name="persons" requestURI="${sortUrl}">

		<display:setProperty name="pagination.sort.param" value="persons.sortCriterion" />
		<display:setProperty name="pagination.sortdirection.param" value="persons.sortDirection" />
		<display:setProperty name="pagination.pagenumber.param"	value="persons.pageNumber" />

		<display:column titleKey="person.id" property="id" sortable="true" sortProperty="PERSON_ID" />
		<display:column titleKey="person.firstName" property="firstName" sortable="true" sortProperty="PERSON_FIRSTNAME" />
		<display:column titleKey="person.lastName" property="lastName" sortable="true" sortProperty="PERSON_LASTNAME" />
		<display:column titleKey="person.middleName" property="middleName" sortable="true" sortProperty="PERSON_MIDDLENAME" />
		<display:column titleKey="person.prefix" property="prefix" sortable="true" sortProperty="PERSON_PREFIX" />
		<display:column titleKey="person.suffix" property="suffix" sortable="true" sortProperty="PERSON_SUFFIX" />
        <display:column titleKey="person.statusCode" sortable="false">
            <c:choose>
            <c:when test="${fn:length(row.changeRequests) > 0}">
                <div class="difference_found">Change Requests (${fn:length(row.changeRequests)})</div>
            </c:when>
            <c:otherwise>${row.statusCode}</c:otherwise>
            </c:choose>
        </display:column>
	</display:table>
</ajax:displayTag>
