<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:url value="/protected/ajax/duplicates/organization/search.action" var="sortUrl" />
<ajax:displayTag id="duplicateOrganizationSearchResults" ajaxFlag="true" tableClass="data">
    <display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="row" name="orgs" requestURI="${sortUrl}">
        <po:displayTagProperties/>
        <display:setProperty name="pagination.sort.param" value="orgs.sortCriterion" />
        <display:setProperty name="pagination.sortdirection.param" value="orgs.sortDirection" />
        <display:setProperty name="pagination.pagenumber.param" value="orgs.pageNumber" />

        <display:column titleKey="organization.name" property="name" sortable="true" sortProperty="ORGANIZATION_NAME" maxLength="30"/>
        <display:column titleKey="organization.abbreviatedName" property="abbreviatedName" maxLength="30"/>
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
            <po:buttonRow>
                <po:button href="javascript://nop/" onclick="window.top.document.getElementById('curateOrgForm_organization_duplicateOf_id').value = ${row.id}; window.top.hidePopWin(true);" style="select_person" text="Use As Duplicate" id="org_id_${row.id}" />
            </po:buttonRow>
        </display:column>
    </display:table>
</ajax:displayTag>