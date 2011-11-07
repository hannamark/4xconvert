<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<h2><a href="#"><fmt:message key="adHocReport.participatingSites.header" /></a></h2>
<div id="participatingSitesSection">
    <table class="form-table">
        <tbody>
            <viewer:valueRow labelFor="participatingSiteId" labelKey="studyProtocol.participatingSite">
                <s:select name="criteria.participatingSiteId" id="participatingSiteId" list="participatingSiteList" listKey="id"
                          listValue="name" headerKey="" headerValue="All" value="criteria.participatingSiteId" />
            </viewer:valueRow>
        </tbody>
    </table> 
</div>