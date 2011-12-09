<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<h2><a href="#"><fmt:message key="adHocReport.biomarkers.header" /></a></h2>
<div id="biomarkersSection">
    <table class="form-table">
        <tbody>
            <viewer:valueRow labelFor="bioMarkers" labelKey="studyProtocol.biomarker">
                <s:select name="criteria.bioMarkerNames" id="bioMarkers" list="plannedMarkersList" 
                          headerKey="" headerValue="All" value="criteria.bioMarkers" multiple="true" size="5" />
            </viewer:valueRow>
        </tbody>
    </table>    
</div>