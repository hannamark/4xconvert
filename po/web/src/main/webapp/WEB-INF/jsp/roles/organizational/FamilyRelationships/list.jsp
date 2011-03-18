<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:text name="familyOrgRelationship.remove.confirmationMessage" var="famOrgRelRemoveMessage"/>
<div class="addbtn">
    <ul class="btnrow">
        <c:url value="/protected/organization/family/relationship/create/start.action" var="createFamilyOrgRelationshipUrl">
            <c:param name="familyOrgRelationship.organization.id" value="${organization.id}"/>
            <c:param name="perspective" value="organization" />
        </c:url>
        <li>
            <a href="${createFamilyOrgRelationshipUrl}" class="btn" id="add_family_member_id_${organization.id}"><span class="btn_img"><span class="add">Add</span></span></a>
        </li>
    </ul>
</div>
<s:set name="organizational" value="%{@gov.nih.nci.po.web.curation.CurateFamilyOrganizationRelationshipAction@ORGANIZATIONAL_PERSPECTIVE}" scope="request" />
<ajax:displayTag id="roleSearchResults" tableClass="data">
    <display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="row" name="organization.familyOrganizationRelationships" requestURI="${sortUrl}" >
        <po:displayTagProperties/>
        <display:setProperty name="pagination.sort.param" value="results.sortCriterion" />
        <display:setProperty name="pagination.sortdirection.param" value="results.sortDirection" />
        <display:setProperty name="pagination.pagenumber.param" value="results.pageNumber" />
        <display:column titleKey="family.id" property="family.id" sortable="true" />
        <display:column titleKey="family.name" property="family.name" sortable="true" />
        <display:column titleKey="familyOrgRelationship.functionalType" property="functionalType" sortable="true" />
        <display:column titleKey="familyOrgRelationship.effectiveDates" property="startDate" sortable="false" format="{0, date, MM-dd-yyyy}"/>
        <display:column titleKey="th.action" class="action">
            <c:url var="curateUrl" value="/protected/organization/family/relationship/curate/start.action">
                <c:param name="familyOrgRelationship.id" value="${row.id}" />
                <c:param name="perspective" value="${organizational}" />
            </c:url>
            <c:url var="removeUrl" value="/protected/organization/family/relationship/curate/remove.action">
                <c:param name="familyOrgRelationship.id" value="${row.id}" />
                <c:param name="perspective" value="${organizational}" />
                <c:param name="selectedFamilyId" value="${row.family.id}" />
            </c:url>
            <po:buttonRow>
                <po:button href="${curateUrl}" style="edit" text="Edit" id="fam_org_relationship_edit_id_${row.id}" />
                <po:button href="${removeUrl}" style="delete" text="Remove" id="fam_org_relationship_remove_id_${row.id}" onclick="return confirm('${famOrgRelRemoveMessage}');"/>
            </po:buttonRow>
        </display:column>
    </display:table>
</ajax:displayTag>


