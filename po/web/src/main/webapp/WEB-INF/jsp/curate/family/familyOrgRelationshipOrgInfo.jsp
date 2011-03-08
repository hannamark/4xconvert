<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:set name="isCreate" id="isCreate" value="familyOrgRelationship.id == null"/>
<c:url value="/protected/selector/organization/start.action" var="searchUrl"/>
<div class="box_outer">
    <div class="box_white">
        <po:inputRow>
            <po:inputRowElement>
                <po:field labelKey="organization.name" fieldRequired="true"><h2 style="background: none;">${familyOrgRelationship.organization.name}</h2>
                    <s:fielderror>
                        <s:param>familyOrgRelationship.organization</s:param>
                    </s:fielderror>
                </po:field>
            </po:inputRowElement>
            <po:inputRowElement>&nbsp;</po:inputRowElement>
            <s:if test="%{isCreate}">
                <po:inputRowElement>
                    <c:url value="/protected/selector/organization/start.action" var="searchUrl"/>
                    <po:button href="#" style="add" text="Search" 
                        onclick="showPopWin('${searchUrl}', 1000, 600, orgSelectionCallback);"/>
                </po:inputRowElement>
            </s:if>
        </po:inputRow>
    </div>
</div>