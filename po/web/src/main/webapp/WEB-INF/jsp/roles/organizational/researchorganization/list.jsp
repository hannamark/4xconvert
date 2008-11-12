<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:url value="/protected/ajax/roles/organizational/researchorganization/results/list.action" var="sortUrl" />
<ajax:displayTag id="researchOrganizationSearchResults" ajaxFlag="true" tableClass="data">
    <display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="row" name="results" requestURI="${sortUrl}" >
        <po:displayTagProperties/>
        <display:setProperty name="pagination.sort.param" value="results.sortCriterion" />
        <display:setProperty name="pagination.sortdirection.param" value="results.sortDirection" />
        <display:setProperty name="pagination.pagenumber.param" value="results.pageNumber" />
        <display:column titleKey="researchOrganization.id" property="id" sortable="true" sortProperty="ID"/>
        <display:column titleKey="researchOrganization.typeCode" sortable="true" sortProperty="TYPE_DESC">
            ${row.typeCode.description} (${row.typeCode.code})
        </display:column>
        <display:column titleKey="researchOrganization.fundingMechanism" property="fundingMechanism" sortable="true" sortProperty="FUNDING"/>
        <display:column titleKey="researchOrganization.status" sortable="true" sortProperty="ROLE_STATUS">
            <c:choose>
            <c:when test="${fn:length(row.changeRequests) > 0}">
               ${row.status} <br/>
                <div class="difference_found">Change Requests (${fn:length(row.changeRequests)})</div>
            </c:when>
            <c:otherwise>${row.status}</c:otherwise>
            </c:choose>
        </display:column> 
        <display:column titleKey="researchOrganization.statusDate" property="statusDate" sortable="true" sortProperty="STATUS_DATE" format="{0,date,yyyy-MM-dd}" />
        <display:column titleKey="th.action" class="action">
            <c:url var="editUrl" value="/protected/roles/organizational/researchorganization/addedit/input.action">
                <c:param name="organization.id" value="${organization.id}"/>
                <c:param name="role.id" value="${row.id}"/>
            </c:url>
            <po:buttonRow>
                <po:button href="${editUrl}" style="edit" text="Edit" id="edit_research_org_id_${row.id}"/>
            </po:buttonRow>
        </display:column>
    </display:table>
</ajax:displayTag>


