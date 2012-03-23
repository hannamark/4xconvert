<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../selectAndClose.jsp" %>
<c:url value="/protected/ajax/selector/family/results/list.action" var="sortUrl" />
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
            <c:forEach items="${row.familyOrganizationRelationships}" var="famOrgRel">
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
            <po:buttonRow>
                <po:button id="select_family_${row.id}" href="javascript://nop/" onclick="selectAndClose(new IdValue('${row.id}', '${func:escapeJavaScript(row.name)}'));" style="add" text="Select" />
            </po:buttonRow>
        </display:column>
    </display:table>
</ajax:displayTag>                                    
