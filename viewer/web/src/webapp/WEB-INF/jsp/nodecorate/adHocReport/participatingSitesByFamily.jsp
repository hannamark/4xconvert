<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%> 
<s:if test="participatingSites.size != 0">
  <s:select id="siteNames" name="criteria.participatingSiteIds" list="participatingSites"  value="criteria.participatingSiteIds" listKey="key" 
                          listValue="value" multiple="true" size="participatingSites.size > 10 ? 10 : participatingSites.size"/>
  <input type="checkbox" id="participatingSitesSelectAllCheckbox" title="Select All" /><label for="participatingSitesSelectAllCheckbox">Select All</label>
</s:if>
<s:else>
  <fmt:message key="adHocReport.byFamilyNoResults"/>
</s:else>



