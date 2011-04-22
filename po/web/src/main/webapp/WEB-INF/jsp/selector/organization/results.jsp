<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:url value="/protected/ajax/selector/organization/results/search.action" var="sortUrl" />
<ajax:displayTag id="duplicateOrganizationSearchResults" tableClass="data">
    <display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="row" name="results" requestURI="${sortUrl}">
        <po:displayTagProperties/>
        <display:setProperty name="pagination.sort.param" value="results.sortCriterion" />
        <display:setProperty name="pagination.sortdirection.param" value="results.sortDirection" />
        <display:setProperty name="pagination.pagenumber.param" value="results.pageNumber" />

        <display:column titleKey="organization.id" sortable="true" sortProperty="ORGANIZATION_ID" >
            <c:url var="viewDetailsUrl" value="/protected/ajax/selector/organization/detail.action">
                <c:param name="organization.id" value="${row.id}"/>
            </c:url>
            <a href="javascript://nop/" onclick="$('findDuplicates').hide(); loadDiv('${viewDetailsUrl}','duplicateSearchResultDetails', true);">
                ${row.id}
            </a>
        </display:column>
        <display:column titleKey="organization.name" property="name" sortable="true" sortProperty="ORGANIZATION_NAME" />
        <display:column titleKey="family.name" sortable="false" >
            <c:forEach items="${row.familyOrganizationRelationships}" var="famOrgRel">
                <li><c:out value="${famOrgRel.family.name}"/> (<c:out value="${famOrgRel.functionalType}"/>)</li> 
            </c:forEach>
        </display:column>
        <display:column titleKey="organization.statusCode" sortable="true" sortProperty="ORGANIZATION_STATUS" >
            ${row.statusCode}
        </display:column>
        <display:column titleKey="organization.changeRequests" >
            <c:choose>
            <c:when test="${fn:length(row.changeRequests) > 0}">
               <div class="difference_found">${fn:length(row.changeRequests)}</div>
            </c:when>
            <c:otherwise><fmt:message key="none"/></c:otherwise>
            </c:choose>
        </display:column>
        <display:column titleKey="th.action" class="action">
            <po:buttonRow>
                <po:button id="mark_as_dup_${row.id}" href="javascript://nop/" onclick="selectAndClose(new IdValue('${row.id}', '${func:escapeJavaScript(row.name)}'));" style="add" text="Select" />
            </po:buttonRow>
        </display:column>
    </display:table>
</ajax:displayTag>
