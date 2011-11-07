<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<h2><a href="#"><fmt:message key="adHocReport.summary4.header" /></a></h2>
<div id="summary4Section">
    <table class="form-table">
        <tbody>
            <viewer:valueRow labelFor="summ4Sponsor" labelKey="studyProtocol.summ4Sponsor">
                <s:select name="criteria.summ4FundingSourceId" id="summ4Sponsor" list="summ4FunsingSponsorsList" listKey="id"
                          listValue="name" headerKey="" headerValue="All" value="criteria.summ4FundingSourceId" />
            </viewer:valueRow>
            <viewer:valueRow labelFor="summ4FundingSourceTypeCode" labelKey="studyProtocol.summ4FundingSourceTypeCode">
                <s:set name="summ4FundingSourceTypeCodes" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
                 <s:select headerKey="" headerValue="All" id="summ4FundingSourceTypeCode" name="criteria.summ4FundingSourceTypeCode" 
                           list="#summ4FundingSourceTypeCodes"  value="criteria.summ4FundingSourceTypeCode" cssStyle="width:206px" />
            </viewer:valueRow>
            <viewer:valueRow labelFor="anatomicSites" labelKey="studyProtocol.summary4AnatomicSites">
                <s:select name="criteria.summary4AnatomicSites" id="anatomicSites" list="anatomicSitesList" listKey="id"
                          listValue="displayName" headerKey=""  headerValue="All" value="criteria.summary4AnatomicSites" multiple="true" size="3"  />
            </viewer:valueRow>
        </tbody>
    </table> 
</div>