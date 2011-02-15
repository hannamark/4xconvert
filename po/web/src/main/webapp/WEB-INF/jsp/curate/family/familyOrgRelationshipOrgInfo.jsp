<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:url value="/protected/selector/organization/start.action" var="searchUrl"/>
<div class="box_outer">
    <div class="box_white">
        <po:inputRow>
            <po:inputRowElement><po:field labelKey="organization.name"><h2 style="background: none;">${familyOrgRelationship.organization.name}</h2></po:field></po:inputRowElement>
            <s:if test="%{perspective neq @gov.nih.nci.po.web.curation.CurateFamilyOrganizationRelationshipAction@ORGANIZATIONAL_PERSPECTIVE}">
            <po:inputRowElement>&nbsp;</po:inputRowElement>
            <po:inputRowElement>
                <c:url value="/protected/selector/organization/start.action" var="searchUrl"/>
                <po:button href="#" style="add" text="Search Again" 
                    onclick="showPopWin('${searchUrl}', 1000, 600, orgSelectionCallback);"/>
            </po:inputRowElement>
            </s:if>
        </po:inputRow>
    </div>
</div>