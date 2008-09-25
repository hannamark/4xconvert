<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:url value="/protected/ajax/curate/search/organization/listOrgs.action" var="sortUrl"/>
<ajax:displayTag id="organizationSearchResults" ajaxFlag="true" tableClass="data">
	<display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="row" name="orgs" requestURI="${sortUrl}">
	    <po:displayTagProperties/>
		<display:setProperty name="pagination.sort.param" value="orgs.sortCriterion" />
		<display:setProperty name="pagination.sortdirection.param" value="orgs.sortDirection" />
		<display:setProperty name="pagination.pagenumber.param"	value="orgs.pageNumber" />

		<display:column titleKey="organization.id" property="id" sortable="true" sortProperty="ORGANIZATION_ID" />
		<display:column titleKey="organization.name" property="name" sortable="true" sortProperty="ORGANIZATION_NAME" maxLength="30"/>
		<display:column titleKey="organization.abbreviatedName" property="abbreviatedName" maxLength="10"/>
		<display:column titleKey="organization.description" property="description" maxLength="30"/>
        <display:column titleKey="organization.statusCode" sortable="false">
	        <c:choose>
	        <c:when test="${fn:length(row.changeRequests) > 0}">
	           ${row.statusCode} <br/>
	            <div class="difference_found">Change Requests (${fn:length(row.changeRequests)})</div>
	        </c:when>
	        <c:otherwise>${row.statusCode}</c:otherwise>
	        </c:choose>
        </display:column>
        <display:column titleKey="th.action" class="action">
            <c:url var="curateUrl" value="/protected/organization/curate/start.action">
                <c:param name="organization.id" value="${row.id}"/>
            </c:url>
            <po:buttonRow>
                <po:button href="${curateUrl}" style="select_person" text="Curate" id="org_id_${row.id}"/>
            </po:buttonRow>
        </display:column>
	</display:table>
</ajax:displayTag>