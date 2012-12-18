<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:url value="/protected/ajax/roles/organizational/OversightCommittee/results/list.action" var="sortUrl" />
<ajax:displayTag id="oversightCommitteeSearchResults" tableClass="data">
    <display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="oc_row" name="results" requestURI="${sortUrl}" >
        <po:displayTagProperties/>
        <display:setProperty name="pagination.sort.param" value="results.sortCriterion" />
        <display:setProperty name="pagination.sortdirection.param" value="results.sortDirection" />
        <display:setProperty name="pagination.pagenumber.param" value="results.pageNumber" />
        <display:column titleKey="oversightCommittee.id" property="id" sortable="true" sortProperty="ID"/>
        <display:column titleKey="oversightCommittee.typeCode" sortable="true" sortProperty="TYPE_CODE">
            ${oc_row.typeCode.code}
        </display:column>
        <display:column titleKey="oversightCommittee.status" sortable="true" sortProperty="ROLE_STATUS">
            <c:choose>
            <c:when test="${fn:length(oc_row.changeRequests) > 0}">
               ${oc_row.status} <br/>
                <div class="difference_found">Change Requests (${fn:length(oc_row.changeRequests)})</div>
            </c:when>
            <c:otherwise>${oc_row.status}</c:otherwise>
            </c:choose>
        </display:column>
        <display:column titleKey="oversightCommittee.statusDate" property="statusDate" sortable="true" sortProperty="STATUS_DATE" format="{0,date,yyyy-MM-dd}" />
        <display:column titleKey="th.action" class="action">
            <c:url var="editUrl" value="/protected/roles/organizational/OversightCommittee/input.action">
                <c:param name="organization" value="${organization.id}"/>
                <c:param name="role.id" value="${oc_row.id}"/>
            </c:url>
            <po:buttonRow>
                <po:button href="${editUrl}" style="edit" text="Edit" id="edit_oversightCommittee_id_${oc_row.id}"/>
            </po:buttonRow>
        </display:column>
    </display:table>
</ajax:displayTag>


