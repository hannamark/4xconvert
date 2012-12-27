<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:url value="/protected/ajax/roles/person/HealthCareProvider/results/list.action" var="sortUrl" />
<ajax:displayTag id="hcpRoleSearchResults" tableClass="data">
    <display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="hcp_row" name="results" requestURI="${sortUrl}" >
        <po:displayTagProperties/>
        <display:setProperty name="pagination.sort.param" value="results.sortCriterion" />
        <display:setProperty name="pagination.sortdirection.param" value="results.sortDirection" />
        <display:setProperty name="pagination.pagenumber.param" value="results.pageNumber" />
        <display:column titleKey="healthCareProvider.id" property="id" sortable="true" sortProperty="ID"/>
        <display:column titleKey="healthCareProvider.scoper.id" property="scoper.id" sortable="true" sortProperty="SCOPER_ID" maxLength="10"/>
        <display:column titleKey="healthCareProvider.scoper.name" property="scoper.name" sortable="true" sortProperty="SCOPER_NAME" maxLength="30"/>
        <display:column titleKey="healthCareProvider.status" sortable="true" sortProperty="ROLE_STATUS">
            <c:choose>
            <c:when test="${fn:length(hcp_row.changeRequests) > 0}">
               ${hcp_row.status} <br/>
                <div class="difference_found">Change Requests (${fn:length(hcp_row.changeRequests)})</div>
            </c:when>
            <c:otherwise>${hcp_row.status}</c:otherwise>
            </c:choose>
        </display:column>
        <display:column titleKey="healthCareProvider.statusDate" property="statusDate" sortable="true" sortProperty="STATUS_DATE" format="{0,date,yyyy-MM-dd}" />
        <display:column titleKey="th.action" class="action">
            <c:url var="editUrl" value="/protected/roles/person/HealthCareProvider/input.action">
                <c:param name="person" value="${person.id}"/>
                <c:param name="role.id" value="${hcp_row.id}"/>
            </c:url>
            <po:buttonRow>
                <po:button href="${editUrl}" style="edit" text="Edit" id="edit_healthCareProvider_id_${hcp_row.id}"/>
            </po:buttonRow>
        </display:column>
    </display:table>
</ajax:displayTag>


