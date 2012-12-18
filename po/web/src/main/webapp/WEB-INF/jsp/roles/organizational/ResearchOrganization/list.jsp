<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:url value="/protected/ajax/roles/organizational/ResearchOrganization/results/list.action" var="sortUrl" />
<ajax:displayTag id="researchOrganizationSearchResults" tableClass="data">
    <display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="ro_row" name="results" requestURI="${sortUrl}" >
        <po:displayTagProperties/>
        <display:setProperty name="pagination.sort.param" value="results.sortCriterion" />
        <display:setProperty name="pagination.sortdirection.param" value="results.sortDirection" />
        <display:setProperty name="pagination.pagenumber.param" value="results.pageNumber" />
        <display:column titleKey="researchOrganization.id" property="id" sortable="true" sortProperty="ID"/>
        <display:column titleKey="researchOrganization.name" property="name" sortable="true" sortProperty="NAME"/>
        <display:column titleKey="researchOrganization.typeCode" sortable="true" sortProperty="TYPE_DESC">
            ${ro_row.typeCode.description}
        </display:column>
        <display:column titleKey="researchOrganization.fundingMechanism" property="fundingMechanism.code" sortable="true" sortProperty="FUNDING"/>
        <display:column titleKey="researchOrganization.status" sortable="true" sortProperty="ROLE_STATUS">
            <c:choose>
            <c:when test="${fn:length(ro_row.changeRequests) > 0}">
               ${ro_row.status} <br/>
                <div class="difference_found">Change Requests (${fn:length(ro_row.changeRequests)})</div>
            </c:when>
            <c:otherwise>${ro_row.status}</c:otherwise>
            </c:choose>
        </display:column>
        <display:column titleKey="researchOrganization.statusDate" property="statusDate" sortable="true" sortProperty="STATUS_DATE" format="{0,date,yyyy-MM-dd}" />
        <display:column titleKey="th.action" class="action">
            <c:url var="editUrl" value="/protected/roles/organizational/ResearchOrganization/input.action">
                <c:param name="organization" value="${organization.id}"/>
                <c:param name="role.id" value="${ro_row.id}"/>
            </c:url>
            <po:buttonRow>
                <po:button href="${editUrl}" style="edit" text="Edit" id="edit_researchOrganization_id_${ro_row.id}"/>
            </po:buttonRow>
        </display:column>
    </display:table>
</ajax:displayTag>


