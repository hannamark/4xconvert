<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:set name="isCreate" id="isCreate" value="familyOrgRelationship.id == null"/>
<c:url value="/protected/selector/family/list.action" var="searchUrl"/>
<div class="box_outer">
    <div class="box_white">
        <po:inputRow>
            <po:inputRowElement>
            <po:field labelKey="family.name" fieldRequired="true"><h2 id="chkFamilyName" style="background: none;">${familyOrgRelationship.family.name}</h2>
                <s:fielderror>
                    <s:param>familyOrgRelationship.family</s:param>
                </s:fielderror>
            </po:field></po:inputRowElement>
            <po:inputRowElement>&nbsp;</po:inputRowElement>
            <s:if test="%{isCreate}">
                <po:inputRowElement>
                    <po:button href="#" style="add" text="Search" 
                        onclick="showPopWin('${searchUrl}', 1000, 600, familySelectionCallback);"/>
                </po:inputRowElement>
            </s:if>
        </po:inputRow>
    </div>
</div>