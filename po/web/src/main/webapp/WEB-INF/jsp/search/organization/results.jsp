<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<c:url value="/protected/ajax/search/organization/results/search.action" var="sortUrl"/>
<ajax:displayTag id="organizationSearchResults" tableClass="data">
    <display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="row" name="results" requestURI="${sortUrl}">
        <po:displayTagProperties/>
        <display:setProperty name="pagination.sort.param" value="results.sortCriterion" />
        <display:setProperty name="pagination.sortdirection.param" value="results.sortDirection" />
        <display:setProperty name="pagination.pagenumber.param" value="results.pageNumber" />

        <display:column titleKey="search.organization.id" property="id" sortable="true" sortProperty="ORGANIZATION_ID"/>
        <display:column titleKey="organization.name" property="name" sortable="true" sortProperty="ORGANIZATION_NAME" />
        <display:column titleKey="family.name" sortable="false" >
            <c:forEach items="${row.familyOrganizationRelationships}" var="famOrgRel">
                <li><c:out value="${famOrgRel.family.name}"/> (<c:out value="${famOrgRel.functionalType}"/>)</li> 
            </c:forEach>
        </display:column>
        <display:column titleKey="organization.statusCode" sortable="true" sortProperty="ORGANIZATION_STATUS" >
            ${row.statusCode}
        </display:column>
        <display:column title="Change Request(s)" >
            <c:choose>
            <c:when test="${fn:length(row.changeRequests) > 0}">
               <div class="difference_found">${fn:length(row.changeRequests)}</div>
            </c:when>
            <c:otherwise>NONE</c:otherwise>
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