<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:url value="/ctro/ajax/refreshSummary4ReportagencyDeviceInstitutionalResult.action" var="agencyDeviceInstitutionalSortUrl"/>
<s:set name="institutionalStr" value="@gov.nih.nci.pa.viewer.util.ViewerConstants@INSTITUTIONAL" />    
<h3><fmt:message key="summary4Report.resultHead.institutional"/></h3>
<table width="100%">
    <tr>               
        <td colspan="2">
        <div id="summ4SearchResultsagentDeviceListInstitutional">
            <ajax:displayTag id="summ4SearchResultsagentDeviceListInstitutional" tableClass="data">
                <viewer:summary4ReportResultTable name="${sessionScope.agentDeviceMap[institutionalStr]}" requestURI="${agencyDeviceInstitutionalSortUrl}" uid="agentDeviceInstRow"/>
            </ajax:displayTag>
            </div>
        </td>
    </tr>
</table>             