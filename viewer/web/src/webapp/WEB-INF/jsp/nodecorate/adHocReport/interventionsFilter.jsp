<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<h2><a href="#"><fmt:message key="adHocReport.interventions.header" /></a></h2>
<div id="interventionsSection">
    <table class="form-table">
        <tbody>
            <viewer:valueRow labelFor="interventionType" labelKey="studyProtocol.interventionType">
                <s:set name="interventionTypeValues" value="@gov.nih.nci.pa.enums.ActivitySubcategoryCode@getDisplayNames()" />
                <s:select headerKey="" headerValue="All" id="interventionType" name="criteria.interventionType" list="#interventionTypeValues"  
                          value="criteria.interventionType" cssStyle="width:206px" />
            </viewer:valueRow>
        </tbody>
    </table> 
</div>