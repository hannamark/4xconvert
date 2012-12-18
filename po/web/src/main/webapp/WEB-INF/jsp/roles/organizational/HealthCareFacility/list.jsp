<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:url value="/protected/ajax/roles/organizational/HealthCareFacility/results/list.action" var="sortUrl" />
<ajax:displayTag id="hcfRoleSearchResults" tableClass="data">
    <display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="hcf_row" name="results" requestURI="${sortUrl}" >
        <po:displayTagProperties/>
        <display:setProperty name="pagination.sort.param" value="results.sortCriterion" />
        <display:setProperty name="pagination.sortdirection.param" value="results.sortDirection" />
        <display:setProperty name="pagination.pagenumber.param" value="results.pageNumber" />
        <display:column titleKey="healthCareFacility.id" property="id" sortable="true" sortProperty="ID"/>
        <display:column titleKey="healthCareFacility.name" property="name" sortable="true" sortProperty="NAME"/>
        <display:column titleKey="healthCareFacility.status" sortable="true" sortProperty="ROLE_STATUS">
            <c:choose>
            <c:when test="${fn:length(hcf_row.changeRequests) > 0}">
               ${hcf_row.status} <br/>
                <div class="difference_found">Change Requests (${fn:length(hcf_row.changeRequests)})</div>
            </c:when>
            <c:otherwise>${hcf_row.status}</c:otherwise>
            </c:choose>
        </display:column>
        <display:column titleKey="healthCareFacility.statusDate" property="statusDate" sortable="true" sortProperty="STATUS_DATE" format="{0,date,yyyy-MM-dd}" />
        <display:column titleKey="th.action" class="action">
            <c:url var="editUrl" value="/protected/roles/organizational/HealthCareFacility/input.action">
                <c:param name="organization" value="${organization.id}"/>
                <c:param name="role.id" value="${hcf_row.id}"/>
            </c:url>
            <po:buttonRow>
                <po:button href="${editUrl}" style="edit" text="Edit" id="edit_healthCareFacility_id_${hcf_row.id}"/>
            </po:buttonRow>
        </display:column>
    </display:table>
</ajax:displayTag>


