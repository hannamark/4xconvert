<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<div class="boxouter">
    <h2>Organization Family Members</h2>
    <div class="addbtn">
        <ul class="btnrow">
            <li><a href="#" class="btn" onclick="showPopWin('submodal_search_org.php', 600, 400, null);"><span
                class="btn_img"><span class="add">Add</span></span></a></li>
        </ul>
    </div>
    <div class="box"><c:url value="/protected/ajax/search/family/results/list.action" var="sortUrl" /> <ajax:displayTag
        id="familySearchResults" tableClass="data">
        <display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="row"
            name="family.familyOrganizationRelationships" requestURI="${sortUrl}">
            <po:displayTagProperties />
            <display:setProperty name="pagination.sort.param" value="results.sortCriterion" />
            <display:setProperty name="pagination.sortdirection.param" value="results.sortDirection" />
            <display:setProperty name="pagination.pagenumber.param" value="results.pageNumber" />
    
            <display:column titleKey="organization.id" property="organization.id" sortable="false" />
            <display:column titleKey="organization.name" property="organization.name" sortable="false" />
            <display:column title="Functional Relatinship" sortable="false">
                <c:out value="${row.functionalType}" />
            </display:column>
            <display:column title="Effective Date" property="startDate" sortable="false" />
    
            <display:column titleKey="th.action" class="action">
                <c:url var="editUrl" value="/protected/family/edit/start.action">
                    <c:param name="family.id" value="${row.id}" />
                </c:url>
                <c:url var="removeUrl" value="/protected/family/remove/start.action">
                    <c:param name="family.id" value="${row.id}" />
                </c:url>
                <po:buttonRow>
                    <po:button href="${editUrl}" style="edit" text="Edit" id="org_id_${row.id}" />
                    <po:button href="${removeUrl}" style="delete" text="Remove" id="org_id_${row.id}" />
                </po:buttonRow>
            </display:column>
        </display:table>
    </ajax:displayTag>
    </div>
</div>