<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<h2><a href="#"><fmt:message key="adHocReport.participatingSites.header" /></a></h2>
<div id="participatingSitesSection">
    <table class="form-table">
        <tbody>
            <viewer:valueRow labelFor="participatingSiteIds" labelKey="studyProtocol.participatingSites">
                <s:select name="criteria.participatingSiteIds" id="participatingSiteIds" list="participatingSiteList" listKey="key"
                          listValue="value" headerKey="" headerValue="All" value="criteria.participatingSiteIds" multiple="true"/>
            </viewer:valueRow>
        </tbody>
    </table> 
</div>