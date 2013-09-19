<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:text name="familyOrgRelationship.remove.confirmationMessage" var="famOrgRelRemoveMessage"/>
<div class="boxouter">
    <h2>Organization Family Members</h2>
    <div class="addbtn">
        <ul class="btnrow">
            <c:url value="/protected/family/organization/relationship/create/start.action" var="createFamilyOrgRelationshipUrl">
                <c:param name="familyOrgRelationship.family.id" value="${family.id}"/>
            </c:url>
            <li>
                <a href="${createFamilyOrgRelationshipUrl}" class="btn" id="add_family_member_id_${family.id}"><span class="btn_img"><span class="add">Add</span></span></a>
            </li>
        </ul>
    </div>
    <div class="box"><c:url value="/protected/ajax/search/family/organization/results/list.action" var="sortUrl" /> 
    <ajax:displayTag id="familySearchResults" tableClass="data">
        <display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="row"
            name="family.currentFamilyOrganizationRelationships" requestURI="${sortUrl}">
            <po:displayTagProperties />
            <display:setProperty name="pagination.sort.param" value="results.sortCriterion" />
            <display:setProperty name="pagination.sortdirection.param" value="results.sortDirection" />
            <display:setProperty name="pagination.pagenumber.param" value="results.pageNumber" />
    
            <display:column titleKey="organization.id" property="organization.id" sortable="false" />
            <display:column titleKey="organization.name" property="organization.name" sortable="false" />
            <display:column titleKey="familyOrgRelationship.functionalType" property="functionalType" sortable="false" />
            <display:column titleKey="familyOrgRelationship.effectiveDates" property="startDate" sortable="false" format="{0, date, MM-dd-yyyy}"/>
    
            <display:column titleKey="th.action" class="action">
                <c:url var="curateUrl" value="/protected/family/organization/relationship/curate/start.action">
                    <c:param name="familyOrgRelationship.id" value="${row.id}" />
                </c:url>
                <c:url var="removeUrl" value="/protected/family/organization/relationship/curate/remove.action">
                    <c:param name="familyOrgRelationship.id" value="${row.id}" />
                    <c:param name="selectedFamilyId" value="${family.id}" />
                </c:url>
                <po:buttonRow>
                    <po:button href="${curateUrl}" style="edit" text="Edit" id="fam_org_relationship_edit_id_${row.id}" />
                    <po:button href="${removeUrl}" style="delete" text="Remove" id="fam_org_relationship_remove_id_${row.id}" onclick="return confirm('${famOrgRelRemoveMessage}');"/>
                </po:buttonRow>
            </display:column>
        </display:table>
    </ajax:displayTag>
    </div>
</div>