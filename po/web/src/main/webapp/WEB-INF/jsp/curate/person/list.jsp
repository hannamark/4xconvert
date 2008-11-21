<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:url value="/protected/ajax/curate/search/person/listPersons.action" var="sortUrl"/>
<ajax:displayTag id="personSearchResults" ajaxFlag="true" tableClass="data">
	<display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="row" name="persons" requestURI="${sortUrl}">
        <po:displayTagProperties/>
		<display:setProperty name="pagination.sort.param" value="persons.sortCriterion" />
		<display:setProperty name="pagination.sortdirection.param" value="persons.sortDirection" />
		<display:setProperty name="pagination.pagenumber.param"	value="persons.pageNumber" />

		<display:column titleKey="person.id" property="id" sortable="true" sortProperty="PERSON_ID" />
		<display:column titleKey="person.firstName" property="firstName" sortable="true" sortProperty="PERSON_FIRSTNAME" maxLength="30"/>
		<display:column titleKey="person.lastName" property="lastName" sortable="true" sortProperty="PERSON_LASTNAME" maxLength="30"/>
		<display:column titleKey="person.middleName" property="middleName" sortable="true" sortProperty="PERSON_MIDDLENAME" maxLength="30"/>
		<display:column titleKey="person.prefix" property="prefix" sortable="true" sortProperty="PERSON_PREFIX" maxLength="30"/>
		<display:column titleKey="person.suffix" property="suffix" sortable="true" sortProperty="PERSON_SUFFIX" maxLength="30"/>
        <display:column titleKey="person.statusCode" sortable="false">
            <c:choose>
            <c:when test="${fn:length(row.changeRequests) > 0}">
                ${row.statusCode} <br/>
                <div class="difference_found">Change Requests (${fn:length(row.changeRequests)})</div>
            </c:when>
            <c:otherwise>${row.statusCode}</c:otherwise>
            </c:choose>
        </display:column>
        <display:column titleKey="th.action" class="action">
            <c:url var="curateUrl" value="/protected/person/curate/start.action">
                <c:param name="person.id" value="${row.id}"/>
            </c:url>
            <po:buttonRow>
                <po:button href="${curateUrl}" style="select_person" text="Curate" id="person_id_${row.id}"/>
            </po:buttonRow>
        </display:column>        
	</display:table>
</ajax:displayTag>
