<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:url value="/protected/ajax/roles/organizational/IdentifiedOrganization/results/list.action" var="sortUrl" />
<ajax:displayTag id="ioRoleSearchResults" tableClass="data">
    <display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="io_row" name="results" requestURI="${sortUrl}" >
        <po:displayTagProperties/>
        <display:setProperty name="pagination.sort.param" value="results.sortCriterion" />
        <display:setProperty name="pagination.sortdirection.param" value="results.sortDirection" />
        <display:setProperty name="pagination.pagenumber.param" value="results.pageNumber" />
        <display:column titleKey="identifiedOrganization.id" property="id" sortable="true" sortProperty="ID"/>
        <display:column titleKey="identifiedOrganization.scoper.id" property="scoper.id" sortable="true" sortProperty="SCOPER_ID" maxLength="10"/>
        <display:column titleKey="identifiedOrganization.scoper.name" property="scoper.name" sortable="true" sortProperty="SCOPER_NAME" maxLength="30"/>
        <display:column titleKey="identifiedOrganization.assignedIdentifier.root">
            ${io_row.assignedIdentifier.root}
        </display:column>
        <display:column titleKey="identifiedOrganization.assignedIdentifier.extension">
            ${io_row.assignedIdentifier.extension}
        </display:column>
        <display:column titleKey="identifiedOrganization.status" sortable="true" sortProperty="ROLE_STATUS">
            <c:choose>
            <c:when test="${fn:length(io_row.changeRequests) > 0}">
               ${io_row.status} <br/>
                <div class="difference_found">Change Requests (${fn:length(io_row.changeRequests)})</div>
            </c:when>
            <c:otherwise>${io_row.status}</c:otherwise>
            </c:choose>
        </display:column>
        <display:column titleKey="identifiedOrganization.statusDate" property="statusDate" sortable="true" sortProperty="STATUS_DATE" format="{0,date,yyyy-MM-dd}" />
        <display:column titleKey="th.action" class="action">
            <c:url var="editUrl" value="/protected/roles/organizational/IdentifiedOrganization/input.action">
                <c:param name="organization" value="${organization.id}"/>
                <c:param name="role.id" value="${io_row.id}"/>
            </c:url>
            <po:buttonRow>
                <po:button href="${editUrl}" style="edit" text="Edit" id="edit_identifiedOrganization_id_${io_row.id}"/>
            </po:buttonRow>
        </display:column>
    </display:table>
</ajax:displayTag>


