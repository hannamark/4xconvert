<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:url value="/protected/ajax/curate/search/listOrgs.action" var="sortUrl"/>
<ajax:displayTag id="organizationSearchResults" ajaxFlag="true" tableClass="data">
	<display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="row" name="orgs" requestURI="${sortUrl}">
		<display:setProperty name="pagination.sort.param" value="orgs.sortCriterion" />
		<display:setProperty name="pagination.sortdirection.param" value="orgs.sortDirection" />
		<display:setProperty name="pagination.pagenumber.param"	value="orgs.pageNumber" />

		<display:column titleKey="organization.id" property="id" sortable="true" sortProperty="ORGANIZATION_ID" />
		<display:column titleKey="organization.name" property="name" sortable="true" sortProperty="ORGANIZATION_NAME" />
		<display:column titleKey="organization.abbreviatedName" property="abbreviatedName" sortable="true" sortProperty="ORGANIZATION_NAME" />
		<display:column titleKey="organization.description" property="description" sortable="true" sortProperty="ORGANIZATION_NAME" />
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