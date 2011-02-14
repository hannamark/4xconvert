<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:url value="/protected/selector/organization/start.action" var="searchUrl"/>
<div class="box_outer">
    <div class="box_white">
        <po:inputRow>
            <po:inputRowElement><h2 style="background: none;">${familyOrgRelationship.organization.name}</h2></po:inputRowElement>
            <po:inputRowElement>&nbsp;</po:inputRowElement>
            <po:inputRowElement>
                <c:url value="/protected/selector/organization/start.action" var="searchUrl"/>
                <po:button href="#" style="add" text="Search Again" 
                    onclick="showPopWin('${searchUrl}', 1000, 600, orgSelectionCallback);"/>
            </po:inputRowElement>
        </po:inputRow>
    </div>
</div>