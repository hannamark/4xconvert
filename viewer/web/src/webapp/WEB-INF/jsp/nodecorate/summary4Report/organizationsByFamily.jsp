<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<s:if test="organizations.size != 0">
    <s:select id="orgNames" name="criteria.orgNames" list="organizations" multiple="true" size="organizations.size > 10 ? 10 : organizations.size"/>
    <input type="checkbox" id="orgSelectAllCheckbox" title="Select All" /><label for="orgSelectAllCheckbox">Select All</label>
</s:if>
<s:else>
    <s:if test="orgSearchType == 1">
        <fmt:message key="summary4Report.byFamilyNoResults"/>
    </s:if>
</s:else>
