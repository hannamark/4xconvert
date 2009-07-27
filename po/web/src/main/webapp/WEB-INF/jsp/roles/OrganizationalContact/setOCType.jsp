<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:if test="%{organization.id != null}">
    <s:set name="ocType" value="%{'organizational'}"/>
</s:if>
<s:else>
    <s:set name="ocType" value="%{'person'}"/>
</s:else>
