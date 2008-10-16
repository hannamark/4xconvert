<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:url value="/protected/ajax/duplicates/organization/results/search.action" var="sortUrl" />
<ajax:displayTag id="duplicateOrganizationSearchResults" ajaxFlag="true" tableClass="data">
    <display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="row" name="orgs" requestURI="${sortUrl}">
        <po:displayTagProperties/>
        <display:setProperty name="pagination.sort.param" value="orgs.sortCriterion" />
        <display:setProperty name="pagination.sortdirection.param" value="orgs.sortDirection" />
        <display:setProperty name="pagination.pagenumber.param" value="orgs.pageNumber" />
        
        <display:column titleKey="organization.id" sortable="true" sortProperty="ORGANIZATION_ID" >
            <c:url var="viewDetailsUrl" value="/protected/ajax/duplicates/organization/detail.action">
                <c:param name="organization.id" value="${row.id}"/>
            </c:url>
            <a href="javascript://nop/" onclick="$('findDuplicates').hide(); loadDiv('${viewDetailsUrl}','duplicateSearchResultDetails', true);">
                ${row.id}
            </a>
        </display:column>
        <display:column titleKey="organization.name" property="name" sortable="true" sortProperty="ORGANIZATION_NAME" maxLength="20"/>
        <display:column titleKey="organization.abbreviatedName" property="abbreviatedName" maxLength="10"/>
        <display:column titleKey="organization.description" property="description" maxLength="20"/>
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
            <po:buttonRow>
                <po:button id="mark_as_dup_${row.id}"href="javascript://nop/" onclick="markAsDuplicate('${row.id}');" style="reject" text="Mark As Duplicate" />
            </po:buttonRow>
        </display:column>
    </display:table>
</ajax:displayTag>
  