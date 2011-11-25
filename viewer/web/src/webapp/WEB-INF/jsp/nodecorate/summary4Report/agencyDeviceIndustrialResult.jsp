<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:set name="industrialStr" value="@gov.nih.nci.pa.viewer.util.ViewerConstants@INDUSTRIAL" />
<c:url value="/ctro/ajax/refreshSummary4ReportagencyDeviceIndustrialResult.action" var="agencyDeviceIndustrialSortUrl"/>
<h3><fmt:message key="summary4Report.resultHead.industrial"/></h3>
<table width="100%">
    <tr>               
        <td colspan="2">
            <ajax:displayTag id="summ4SearchResultsagentDeviceListIndustrial" tableClass="data">
                <viewer:summary4ReportResultTable name="${sessionScope.agentDeviceMap[industrialStr]}" requestURI="${agencyDeviceIndustrialSortUrl}" uid="agentDeviceIndustRow"/>
            </ajax:displayTag>
        </td>
    </tr>
</table> 
         
