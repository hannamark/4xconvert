<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="setOCType.jsp" %>

<c:url value="/protected/ajax/roles/${ocType}/OrganizationalContact/results/list.action" var="sortUrl" />
<ajax:displayTag id="orgContactRoleSearchResults" tableClass="data">
    <display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="orgc_row" name="results" requestURI="${sortUrl}" >
        <po:displayTagProperties/>
        <display:setProperty name="pagination.sort.param" value="results.sortCriterion" />
        <display:setProperty name="pagination.sortdirection.param" value="results.sortDirection" />
        <display:setProperty name="pagination.pagenumber.param" value="results.pageNumber" />
            <display:column titleKey="organizationalContact.id" property="id" sortable="true" sortProperty="ID"/>
        <s:if test="%{#ocType == 'organizational'}">
            <display:column titleKey="organizationalContact.title" property="title" sortable="true" sortProperty="TITLE" maxLength="30"/>
        </s:if>
        <s:else>
            <display:column titleKey="organizationalContact.scoper.id" property="scoper.id" sortable="true" sortProperty="SCOPER_ID" maxLength="10"/>
            <display:column titleKey="organizationalContact.scoper.name" property="scoper.name" sortable="true" sortProperty="SCOPER_NAME" maxLength="30"/>
        </s:else>
        <display:column titleKey="organizationalContact.type" property="type.code" sortable="true" sortProperty="CONTACT_TYPE" maxLength="30"/>
        <display:column titleKey="organizationalContact.status" sortable="true" sortProperty="ROLE_STATUS">
            <c:choose>
            <c:when test="${fn:length(orgc_row.changeRequests) > 0}">
               ${orgc_row.status} <br/>
                <div class="difference_found">Change Requests (${fn:length(orgc_row.changeRequests)})</div>
            </c:when>
            <c:otherwise>${orgc_row.status}</c:otherwise>
            </c:choose>
        </display:column>
        <display:column titleKey="organizationalContact.statusDate" property="statusDate" sortable="true" sortProperty="STATUS_DATE" format="{0,date,yyyy-MM-dd}" />
        <display:column titleKey="th.action" class="action">
            <c:url var="editUrl" value="/protected/roles/${ocType}/OrganizationalContact/input.action">
                <s:if test="%{#ocType == 'organizational'}">
                    <c:param name="organization" value="${organization.id}"/>
                </s:if>
                <s:else>
                    <c:param name="person" value="${person.id}"/>
                </s:else>
                <c:param name="role.id" value="${orgc_row.id}"/>
            </c:url>
            <po:buttonRow>
                <po:button href="${editUrl}" style="edit" text="Edit" id="edit_organizationalContact_id_${orgc_row.id}"/>
            </po:buttonRow>
        </display:column>
    </display:table>
</ajax:displayTag>
