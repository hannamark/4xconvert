<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:url value="/protected/ajax/roles/person/IdentifiedPerson/results/list.action" var="sortUrl" />
<ajax:displayTag id="ipRoleSearchResults" tableClass="data">
    <display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="ip_row" name="results" requestURI="${sortUrl}" >
        <po:displayTagProperties/>
        <display:setProperty name="pagination.sort.param" value="results.sortCriterion" />
        <display:setProperty name="pagination.sortdirection.param" value="results.sortDirection" />
        <display:setProperty name="pagination.pagenumber.param" value="results.pageNumber" />
        <display:column titleKey="identifiedPerson.id" property="id" sortable="true" sortProperty="ID"/>
        <display:column titleKey="identifiedPerson.scoper.id" property="scoper.id" sortable="true" sortProperty="SCOPER_ID" maxLength="10"/>
        <display:column titleKey="identifiedPerson.scoper.name" property="scoper.name" sortable="true" sortProperty="SCOPER_NAME" maxLength="30"/>
        <display:column titleKey="identifiedPerson.assignedIdentifier.root">
            ${ip_row.assignedIdentifier.root}
        </display:column>
        <display:column titleKey="identifiedPerson.assignedIdentifier.extension">
            ${ip_row.assignedIdentifier.extension}
        </display:column>
        <display:column titleKey="identifiedPerson.status" sortable="true" sortProperty="ROLE_STATUS">
            <c:choose>
            <c:when test="${fn:length(ip_row.changeRequests) > 0}">
               ${ip_row.status} <br/>
                <div class="difference_found">Change Requests (${fn:length(ip_row.changeRequests)})</div>
            </c:when>
            <c:otherwise>${ip_row.status}</c:otherwise>
            </c:choose>
        </display:column>
        <display:column titleKey="identifiedPerson.statusDate" property="statusDate" sortable="true" sortProperty="STATUS_DATE" format="{0,date,yyyy-MM-dd}" />
        <display:column titleKey="th.action" class="action">
            <c:url var="editUrl" value="/protected/roles/person/IdentifiedPerson/input.action">
                <c:param name="person" value="${person.id}"/>
                <c:param name="role.id" value="${ip_row.id}"/>
            </c:url>
            <po:buttonRow>
                <po:button href="${editUrl}" style="edit" text="Edit" id="edit_identifiedPerson_id_${ip_row.id}"/>
            </po:buttonRow>
        </display:column>
    </display:table>
</ajax:displayTag>


