<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<ul>
<s:if test="organizations.size != 0">
<s:select id="orgNames" name="criteria.orgNames" list="organizations" multiple="true" size="organizations.size > 10 ? 10 : organizations.size"/>
<input type="checkbox" title="Select All" id="orgSelectAllCheckbox" onclick="selectAll(this, 'orgNames')"/> <label for="orgSelectAllCheckbox">Select All</label>
</s:if>
<s:else><fmt:message key="report.orgSearchType.byFamily.noResults"/></s:else>
</ul>
