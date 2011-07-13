<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:set name="industrialStr" value="@gov.nih.nci.pa.viewer.util.ViewerConstants@INDUSTRIAL" />
<c:url value="/ctro/ajax/refreshAgencyDeviceIndustrialResultsSumm4Rep.action" var="agencyDeviceIndustrialSortUrl"/>
<h3>Industrial</h3>
<table width="100%">
    <tr>               
        <td colspan="2">
            <ajax:displayTag id="summ4SearchResultsagentDeviceListIndustrial" tableClass="data">
                <viewer:summ4RepResultTable name="${sessionScope.agentDeviceMap[industrialStr]}" requestURI="${agencyDeviceIndustrialSortUrl}" uid="agentDeviceIndustRow"/>
            </ajax:displayTag>
        </td>
    </tr>
</table> 
         
