<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:if test="criteria.familyId  == 0">             
  <s:select name="criteria.leadOrganizationIds" id="leadOrganizationIds" list="leadOrgList" listKey="key"
                          listValue="value" headerKey="" headerValue="All" value="criteria.leadOrganizationIds" multiple="true" cssStyle="display:inline" />
</s:if>
<s:else>  
  <s:if test="organizations.size != 0">
    <s:select id="orgNames" name="criteria.leadOrganizationNames" list="organizations" multiple="true" size="organizations.size > 10 ? 10 : organizations.size"/>
     <input type="checkbox" id="orgSelectAllCheckbox" title="Select All" /><label for="orgSelectAllCheckbox">Select All</label>
  </s:if>
  <s:else>
    <fmt:message key="adHocReport.byFamilyNoResults"/>
   </s:else>
</s:else>


