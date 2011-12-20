<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>  
<s:if test="organizations.size != 0">
  <s:select id="orgNames" name="criteria.leadOrganizationIds"  value="criteria.leadOrganizationIds" list="organizations"  listKey="key"
                          listValue="value"  multiple="true" size="organizations.size > 10 ? 10 : organizations.size"/>
  <fmt:message key="adHocReport.organizations.selectAll" var="selectAllMsg"/>                        
  <input type="checkbox" id="orgSelectAllCheckbox" title="${selectAllMsg}" /><label for="orgSelectAllCheckbox">${selectAllMsg}</label>
</s:if>
<s:else>
  <fmt:message key="adHocReport.byFamilyNoResults"/>
</s:else>



