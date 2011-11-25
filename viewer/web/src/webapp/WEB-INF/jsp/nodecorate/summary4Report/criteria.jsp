<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="criteriaSection">
    <table class="form-table">
        <tbody>
            <viewer:titleRow titleKey="summary4Report.criteria.dates"/>
            <viewer:valueRow labelFor="intervalStartDate" labelKey="summary4Report.criteria.intervalStartDate" required="true">
                <s:textfield id="intervalStartDate" name="criteria.intervalStartDate" maxlength="10" size="10" cssStyle="width:70px;"/>
            </viewer:valueRow>
            <viewer:valueRow labelFor="intervalEndDate" labelKey="summary4Report.criteria.intervalEndDate" required="true">
                <s:textfield id="intervalEndDate" name="criteria.intervalEndDate" maxlength="10" size="10" cssStyle="width:70px;"/>
            </viewer:valueRow>
            <viewer:titleRow titleKey="summary4Report.organizationName"/>
            <viewer:valueRow labelKey="summary4Report.byOrgName">
                <s:set name="orgSearchType" scope="page" value="orgSearchType"/>
                <input id="orgSearchTypebyOrgName" name="orgSearchType" type="radio" value="0" ${(orgSearchType == 0) ? 'checked' : '' }/>
                <s:textfield id="orgName" name="criteria.orgName" maxlength="100" size="100" cssStyle="width:200px;"/>
            </viewer:valueRow>
            <viewer:valueRow labelKey="summary4Report.byFamily">
                <input id="orgSearchTypebyFamily" name="orgSearchType" type="radio" value="1" ${(orgSearchType == 1) ? 'checked' : '' }/>
                <s:select id="familyId" name="criteria.familyId" list="families" headerKey="" headerValue="--Select--" disabled="orgSearchType == 0" cssStyle="display:inline"/>
            </viewer:valueRow>
            <viewer:valueRow labelKey="summary4Report.orgsByFamily">
                <div id="organization_choices">
                    <jsp:include page="/WEB-INF/jsp/nodecorate/summary4Report/organizationsByFamily.jsp"/>
                </div> 
            </viewer:valueRow>
        </tbody>
    </table>
</div>