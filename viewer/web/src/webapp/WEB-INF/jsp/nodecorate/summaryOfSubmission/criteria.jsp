<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="criteriaSection">
    <table class="form-table">
        <tbody>
            <viewer:titleRow titleKey="sosReport.criteria.dates"/>
            <viewer:valueRow labelFor="intervalStartDate" labelKey="sosReport.criteria.intervalStartDate" required="true">
                <s:textfield id="intervalStartDate" name="criteria.intervalStartDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
            </viewer:valueRow>
            <viewer:valueRow labelFor="intervalEndDate" labelKey="sosReport.criteria.intervalEndDate" required="true">
                <s:textfield id="intervalEndDate" name="criteria.intervalEndDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
            </viewer:valueRow>
            <viewer:valueRow labelFor="ctep" labelKey="sosReport.criteria.ctep" required="true">
                <s:checkbox id="ctep" name="criteria.ctep" />
            </viewer:valueRow>
        </tbody>
    </table>
</div>