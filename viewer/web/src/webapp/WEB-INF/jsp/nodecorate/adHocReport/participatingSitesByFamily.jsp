<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:if test="criteria.participatingSiteFamilyId  == 0">             
  <s:select name="criteria.participatingSiteIds" id="participatingSiteIds" list="participatingSiteList" listKey="key"
                          listValue="value" value="criteria.participatingSiteIds" multiple="true" cssStyle="display:inline"/>
</s:if>
<s:else>  
  <s:if test="participatingSites.size != 0">
    <s:select id="siteNames" name="criteria.participatingSiteNames" list="participatingSites" multiple="true" size="participatingSites.size > 10 ? 10 : participatingSites.size"/>
     <input type="checkbox" id="participatingSitesSelectAllCheckbox" title="Select All" /><label for="participatingSitesSelectAllCheckbox">Select All</label>
  </s:if>
  <s:else>
    <fmt:message key="adHocReport.byFamilyNoResults"/>
   </s:else>
</s:else>


