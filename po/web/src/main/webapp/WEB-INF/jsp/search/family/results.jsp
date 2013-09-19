<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<c:url value="/protected/ajax/search/family/results/list.action" var="sortUrl"/>
<ajax:displayTag id="familySearchResults" tableClass="data">
    <display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="row" name="results" requestURI="${sortUrl}">
        <po:displayTagProperties/>
        <display:setProperty name="pagination.sort.param" value="results.sortCriterion" />
        <display:setProperty name="pagination.sortdirection.param" value="results.sortDirection" />
        <display:setProperty name="pagination.pagenumber.param" value="results.pageNumber" />

        <display:column titleKey="family.id" property="id" sortable="true" sortProperty="FAMILY_ID"/>
        <display:column titleKey="family.name" property="name" sortable="true" sortProperty="FAMILY_NAME" />
        <display:column title="Organization Family Members" sortable="false" >
            <table class="data subtable"> 
            <c:forEach items="${row.currentFamilyOrganizationRelationships}" var="famOrgRel">
                <tr> 
                    <td><c:out value="${famOrgRel.organization.name}"/></td> 
                    <td>(<c:out value="${famOrgRel.functionalType}"/>)</td> 
                </tr> 
            </c:forEach>
            </table>
        </display:column>
        <display:column titleKey="family.statusCode" sortable="true" sortProperty="FAMILY_STATUS" >
            <c:out value="${row.statusCode}"/>
        </display:column>
        
        <display:column titleKey="th.action" class="action">
            <c:url var="curateUrl" value="/protected/family/curate/start.action">
                <c:param name="family.id" value="${row.id}"/>
            </c:url>
            <po:buttonRow>
                <po:button href="${curateUrl}" style="edit" text="Edit" id="edit_family_id_${row.id}"/>
            </po:buttonRow>
        </display:column>
    </display:table>
</ajax:displayTag>                                    